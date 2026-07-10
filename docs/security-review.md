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
