# Docker verification runbook (Ops)

**Status:** BLOCKED in agent environment (`docker: command not found`).  
**Owner:** Ops / Platform  
**PR:** #5 (`cursor/full-upgrade-audit-eec2`)  
**Compose file:** `src/main/docker/app.yml`  
**Entrypoint:** `src/main/docker/jib/entrypoint.sh`

This runbook is for a **safe non-production** verification only. Do not attach containers to production Oracle or production networks.

## Prerequisites

```bash
docker version
docker compose version
docker info
```

All three must succeed. Install Docker Engine + Compose plugin if missing.

## Required environment variables

Set **before** build/start (never bake secrets into the image):

| Variable | Required | Example (non-prod) |
| -------- | -------- | ------------------ |
| `SPRING_DATASOURCE_URL` | Yes | `jdbc:oracle:thin:@//oracle-host:1521/NPGUAT` |
| `SPRING_DATASOURCE_USERNAME` | Yes | non-prod DB user |
| `SPRING_DATASOURCE_PASSWORD` | Yes | from secret store |
| `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` | Yes | `openssl rand -base64 64` |
| `JHIPSTER_CORS_ALLOWED_ORIGINS` | Recommended | exact frontend origin(s) |
| `SPRING_PROFILES_ACTIVE` | Yes | `prod` (or `prod,api-docs` for docs) |
| `JHIPSTER_SLEEP` | Optional | `30` (wait for DB) |

## Safe non-production configuration

1. Use a **non-production** Oracle SID/service only.
2. Bind published ports to `127.0.0.1` (already in `app.yml`).
3. Do not mount production keystores or `.env` files with live prod secrets into the verification host beyond a disposable secret store.
4. Confirm `src/main/docker/app.yml` does not embed passwords (it should not).

## Build commands

```bash
# From repository root — build the application image (Jib / preferred project method)
./mvnw -Pprod,webapp clean package -DskipTests
# Or project-standard image build, e.g.:
./mvnw -Pprod verify jib:dockerBuild
# Image name expected by compose:
docker images | grep npgportal
```

If `mvnw` has a CRLF shebang on Linux:

```bash
sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf
/tmp/mvnw.lf -Pprod,webapp clean package -DskipTests
```

## Compose startup

```bash
cd src/main/docker
docker compose -f app.yml config   # validate
# Export required env vars in this shell, then:
docker compose -f app.yml up -d
docker compose -f app.yml ps
```

## Health verification

```bash
curl -fsS http://127.0.0.1:8080/management/health
curl -fsS -o /dev/null -w "%{http_code}\n" http://127.0.0.1:8080/
```

**PASS:** health is UP (or documented degraded components only); SPA index returns 200.

## Log inspection

```bash
docker compose -f app.yml logs --tail=200 npgportal-app
```

Confirm:

- No stack traces on startup
- Liquibase completed (if DB attached)
- No secret values printed
- JWT/datasource placeholders resolved

## Frontend URL / Backend health URL

| Check | URL |
| ----- | --- |
| Frontend SPA | `http://127.0.0.1:8080/` |
| Backend health | `http://127.0.0.1:8080/management/health` |
| Login | `http://127.0.0.1:8080/login` |

## Route-refresh verification

1. Log in (non-prod credentials).
2. Open a nested route (e.g. `/portability`).
3. Browser refresh — page must reload the same route (SPA fallback).
4. Theme toggle Light/Dark — attribute `data-theme` persists across refresh.

## Theme verification

1. Toggle theme via navbar control (`data-cy="themeToggle"`).
2. Confirm `html[data-theme]` and `localStorage['npg-portal-theme']`.
3. Hard refresh — theme persists.

## Secret / privilege checks

```bash
# No secrets in image history / env defaults baked in
docker history npgportal
docker inspect npgportal | grep -iE 'password|secret|jwt' || true
# Prefer non-root user in runtime (document if image still runs as root — follow-up)
docker compose -f app.yml exec npgportal-app id
```

## Shutdown and cleanup

```bash
cd src/main/docker
docker compose -f app.yml down --remove-orphans
# Only remove the verification image/network created for this test
docker image rm npgportal || true
```

## PASS/FAIL evidence to capture

| Evidence | PASS criteria |
| -------- | ------------- |
| `docker compose config` output | Valid |
| `docker compose ps` | Healthy/running |
| `/management/health` body | UP |
| Screenshot of `/login` | Renders |
| Nested route + refresh | Same URL |
| Theme persistence | OK |
| Logs redacted | No secrets |
| Cleanup complete | Containers stopped |

**Until this runbook is executed successfully, Docker runtime remains BLOCKED for Production readiness.**
