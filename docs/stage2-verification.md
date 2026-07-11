# Stage 2 — Full verification results (2026-07-11)

**Branch tip at test time:** `cursor/full-upgrade-audit-eec2` (`f50d610` + docs commits if any)  
**Agent environment:** Cloud agent (no Docker daemon)

## Backend

```text
./mvnw -P-webapp clean verify
```

| Result | Detail |
|--------|--------|
| **PASS** | `BUILD SUCCESS` |
| Tests | **703** run, 0 failures, 0 errors, 0 skipped |
| Checkstyle | 0 violations |
| Jacoco IT | report generated |

> Note: wrapper invoked via LF-normalized `/tmp/mvnw.lf` because checkout may carry CRLF on `mvnw` shebang under `* text=auto`.

## Frontend

```text
npm ci
npm run lint
npm run test
npm run build
```

| Step | Result |
|------|--------|
| `npm ci` | **PASS** |
| `npm run lint` | **PASS** (0 errors; 6 pre-existing unused `eslint-disable` warnings outside PR scope) |
| `npm run test` | **PASS** — 145 suites / **609** tests |
| `npm run build` | **PASS** (Sass deprecation + budget warnings only) |

## Docker

```text
docker compose build
docker compose up
```

| Result | Detail |
|--------|--------|
| **BLOCKED** | `docker: command not found` in this cloud-agent environment. No daemon available; cannot claim Docker PASS. |

Ops must run the compose smoke on a machine with Docker + Oracle connectivity using `docs/docker-verification-runbook.md`.

## npm audit (snapshot)

| Scope | Critical | High | Moderate |
|-------|----------|------|----------|
| Production (`--omit=dev`) | **0** | **0** | **0** |
| All (incl. dev/tooling) | **0** | 7 | 14 |

High findings remain in Yeoman / `generator-jhipster` tooling (not SPA runtime) — accepted as previously documented.

## Gate implication

Stage 2 is **partial PASS**. Backend + frontend **PASS**. Docker remains **BLOCKED** → Production / Merge Stage 4 criteria **not satisfied**.
