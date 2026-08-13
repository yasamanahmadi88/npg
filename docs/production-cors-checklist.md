# Production CORS allow-list checklist

**PR:** #5  
**Owner:** Ops + App security  
**Status:** BLOCKED pending Ops confirmation of exact Production frontend origins

## Configuration sources (repository)

| Source | Applies to | Content |
| ------ | ---------- | ------- |
| `src/main/resources/config/application.yml` → `jhipster.cors` | All profiles unless overridden | Shared allow-list including localhost, private IPs, `http://` and `https://` MCI hosts |
| `src/main/resources/config/application-prod.yml` → `jhipster.cors` | `prod` profile | Overrides to `${JHIPSTER_CORS_ALLOWED_ORIGINS:https://npg.mci.ir,https://tnpg.mci.ir}` — **no localhost** |
| `WebConfigurer.corsFilter()` | Runtime | Registers CORS for `/api/**`, `/management/**`, docs; **rejects** `*` with `allow-credentials=true` |
| `portal.captcha.backUrl` (prod) | Captcha | `https://npg.mci.ir` (origin hint only) |

## Verified engineering controls

| Check | Status |
| ----- | ------ |
| Wildcard origin with credentials | Rejected in `WebConfigurer` (fail-fast `IllegalStateException`) |
| Localhost in **prod profile default** | Removed via `application-prod.yml` override |
| HTTPS origins documented for prod default | `https://npg.mci.ir`, `https://tnpg.mci.ir` |
| Preflight IT coverage | `SecurityWebConfigurationIT` (allow configured origin / reject unknown) |

## Allow-list table (fill Verified column during Ops review)

| Environment | Allowed origin | Credentials | Owner | Verified |
| ----------- | -------------- | ----------- | ----- | -------- |
| Production (default in repo) | `https://npg.mci.ir` | true | Ops | PENDING |
| Non-prod / UAT (default in repo) | `https://tnpg.mci.ir` | true | Ops | PENDING |
| Production (override) | _set `JHIPSTER_CORS_ALLOWED_ORIGINS`_ | true | Ops | PENDING |
| Dev (base `application.yml`) | `http://localhost:9000` | true | Dev | N/A for prod |
| Dev (base `application.yml`) | `http://localhost:4200` | true | Dev | N/A for prod |
| Shared base yml (legacy) | `http://172.20.200.17` | true | Ops | Do not use in prod profile |
| Shared base yml (legacy) | `http://172.20.200.16` | true | Ops | Do not use in prod profile |
| Shared base yml (legacy) | `http://npg.mci.ir` / `http://tnpg.mci.ir` | true | Ops | Prefer HTTPS only in prod |

**Do not invent additional Production domains.** If the real Production SPA origin differs, set `JHIPSTER_CORS_ALLOWED_ORIGINS` explicitly and record it above.

## Reverse proxy notes

- If TLS terminates at a reverse proxy, the browser `Origin` is the **public** frontend origin (scheme + host + port).  
- Ensure proxy does not strip/rewrite `Origin` incorrectly.  
- SPA and API on the **same origin** may not need CORS for browser calls; CORS still matters for separated frontends.

## Preflight verification commands

```bash
curl -i -X OPTIONS "https://API_HOST/api/account" \
  -H "Origin: https://npg.mci.ir" \
  -H "Access-Control-Request-Method: GET"
# Expect: Access-Control-Allow-Origin: https://npg.mci.ir

curl -i -X OPTIONS "https://API_HOST/api/account" \
  -H "Origin: https://evil.example" \
  -H "Access-Control-Request-Method: GET"
# Expect: no Access-Control-Allow-Origin
```

## Decision

Exact Production origins require Ops input. Until the table rows for Production are **Verified**, treat Production CORS as a **deployment blocker**.
