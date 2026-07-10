# Security Review

## Authentication / authorization

| Control | Status | Notes |
| ------- | ------ | ----- |
| JWT Bearer auth | PASS | `TokenProvider` + `JWTFilter` + `SecurityCache` |
| JWT filter registration | PASS | Direct `addFilterBefore` once; `JWTConfigurer` deleted |
| Password hashing | PASS | BCrypt |
| API default deny for `/api/**` | PASS | Authenticated except public matchers |
| Admin API | PASS | `/api/admin/**` requires `ROLE_ADMIN` |
| CSRF | NOT APPLICABLE | Disabled for Bearer JWT SPA (documented) |
| CORS | PASS | Single CorsFilter; reject unknown origins; no `*`+credentials |
| Unload logout | PASS (removed) | Refresh no longer clears session; explicit logout remains |
| Secrets in prod YAML | PASS | Env-required; literals removed |
| OPTIONS | PASS | `permitAll` + IT |

## npm audit (post Angular 21.2.18; HEAD includes RC docs)

| Finding | Severity | Package | Dependency path | Production/dev | Reachable in SPA | Fix available | Status |
| ------- | -------- | ------- | --------------- | -------------- | ---------------- | ------------- | ------ |
| Angular XSS/DoS advisories | High | `@angular/*` 21.2.14 | direct | production | Yes | 21.2.18 | **Fixed** in this PR |
| shell-quote via concurrently | Critical | `shell-quote` | `concurrently` | development | Dev scripts only | concurrently 9.2.3 | **Fixed** |
| GHSA-gh4j-gqv2-49f6 | High | `fast-xml-parser` | transitive tooling | transitive | No | yes | Accepted |
| Yeoman/codegen cluster | High | `generator-jhipster`, `yeoman-environment`, `lodash`, `lodash-es`, `piscina`, `simple-git` | generator-jhipster tree | development | No | major 8.11.0 | Accepted — breaking |

Critical remaining: **0**.

## Secret scan

Executed against `origin/main...HEAD`. Production password `pass#1400` and hardcoded JWT secret appear only as **removals**. `.env.example` placeholders only. `.env` gitignored.

## Blocker-pass security updates

| Control | Status | Notes |
| ------- | ------ | ----- |
| Prod CORS allow-list | HARDENED / Ops PENDING | `application-prod.yml` overrides shared yml; no localhost in prod default; Ops must verify exact origins (`docs/production-cors-checklist.md`) |
| Prod DB username/password | PASS fail-fast | `${SPRING_DATASOURCE_USERNAME}` / `${SPRING_DATASOURCE_PASSWORD}` — no defaults |
| Prod JWT secret | PASS fail-fast | `${JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET}` — no default |
| Prod JDBC URL host default | DOCUMENTED | Non-secret host `172.19.49.81` default retained intentionally; override via `SPRING_DATASOURCE_URL` |
| Historical credential rotation | BLOCKED | `docs/credential-rotation-plan.md` — required before Production |
| `.env` ignored / `.env.example` placeholders | PASS | Confirmed |
