# Final security audit — PR #5 (Stage 3)

**Date:** 2026-07-11  
**Scope:** JWT, Spring Security 7 / Boot 4 filter chain, CORS, CSRF, password encoding, secrets, Docker security, OWASP Top 10 mapping  
**Code base:** `cursor/full-upgrade-audit-eec2` vs `origin/main`

## Executive verdict

| Gate | Result |
|------|--------|
| **Code-scope security** | **PASS** (with accepted residual tooling risk) |
| **Production ops security** | **NOT PASS** until Docker topology, Oracle smoke, CORS origin confirm, and credential rotation are completed by Ops |

---

## 1. JWT

| Check | Status | Evidence |
|-------|--------|----------|
| Bearer JWT auth | PASS | `TokenProvider` + `JWTFilter` + in-memory `SecurityCache` session binding |
| Filter registration | PASS | `SecurityConfiguration` uses `addFilterBefore(new JWTFilter(...), UsernamePasswordAuthenticationFilter.class)` once |
| Legacy `JWTConfigurer` | PASS | File deleted; no `WebSecurityConfigurerAdapter` / `securityConfigurerAdapter` remains |
| Stateless sessions | PASS | `SessionCreationPolicy.STATELESS` |
| Prod secret | PASS | `${JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET}` — no default; fail-fast if unset |
| Token validity | OK | 24h / remember-me 30d in prod YAML (Ops may tighten) |

**Residual:** Session cache is JVM-local (multi-instance logout/invalidation not shared) — known architecture limit, not introduced by this PR.

## 2. Spring Security 7 compatibility

| Check | Status | Evidence |
|-------|--------|----------|
| `SecurityFilterChain` bean | PASS | Modern lambda DSL on Boot 4 / Security 7 |
| No deprecated adapter | PASS | Adapter API removed |
| Method security | PASS | `@EnableMethodSecurity(prePostEnabled = true)` |
| Exception handling | PASS | Zalando `SecurityProblemSupport` entry/denied handlers |
| Headers | PASS | CSP from JHipster props, referrer policy, frame deny, nosniff |

## 3. CORS

| Check | Status | Evidence |
|-------|--------|----------|
| Single CorsFilter | PASS | Registered before auth filter |
| Prod allow-list | PASS (code) | `application-prod.yml` defaults `https://npg.mci.ir,https://tnpg.mci.ir` — **no localhost** |
| Override | PASS | `JHIPSTER_CORS_ALLOWED_ORIGINS` |
| Credentials + `*` | PASS | No `allowed-origins: *` with credentials |
| Ops confirm | **PENDING** | Exact Production origins must be confirmed (`docs/production-cors-checklist.md`) |

## 4. CSRF

| Check | Status | Evidence |
|-------|--------|----------|
| Disabled intentionally | PASS / N/A | Documented: Bearer JWT SPA; no cookie-session form auth |
| Risk if cookies used for auth | Mitigated by design | Auth is `Authorization: Bearer`, not cookie session |

## 5. Password encoding

| Check | Status | Evidence |
|-------|--------|----------|
| Algorithm | PASS | `BCryptPasswordEncoder` bean |
| Usage | PASS | `UserService` encodes via injected `PasswordEncoder` |

## 6. Secrets management

| Check | Status | Evidence |
|-------|--------|----------|
| Prod DB user/pass | PASS | Env-required; no YAML defaults |
| Prod JWT secret | PASS | Env-required |
| JDBC URL host default | DOCUMENTED | Non-secret host retained; overridable via `SPRING_DATASOURCE_URL` |
| `.env` | PASS | gitignored; `.env.example` placeholders only |
| Historical rotation | **BLOCKED (Ops)** | Prior committed secrets must be rotated (`docs/credential-rotation-plan.md`) |
| Secret scan vs main | PASS | Literals appear as removals in PR diff |

## 7. Docker security

| Check | Status | Evidence |
|-------|--------|----------|
| Compose smoke in agent | **BLOCKED** | No Docker binary/daemon |
| `app.yml` binds | WARN | `127.0.0.1:8080:8080` — good for local; Production topology unknown |
| Non-root user | **UNKNOWN** | Not verified in this environment |
| Secrets in compose | WARN | Dev compose expects env; must not bake prod passwords into images |
| Prometheus in sample compose | WARN | Sample enables metrics export — Production should keep management endpoints locked (code already `denyAll` / admin for sensitive paths) |

## 8. OWASP Top 10 (mapped)

| # | Category | Assessment |
|---|----------|------------|
| A01 | Broken Access Control | PASS for API defaults (`/api/**` authenticated; admin gated). SPA route guards + `*jhiHasPermission` depend on correct branch deployment. |
| A02 | Cryptographic Failures | PASS for BCrypt + env JWT secret; TLS termination is Ops/infra. |
| A03 | Injection | PASS baseline (JPA/parameterized); no new raw SQL in PR scope. |
| A04 | Insecure Design | Residual: JVM-local session cache; rate limits via bucket4j settings. |
| A05 | Security Misconfiguration | Hardened in code (CORS/prod secrets/management deny). Docker/Ops still open. |
| A06 | Vulnerable Components | Prod npm audit **0** critical/high. Dev tooling 7 high accepted. |
| A07 | Auth Failures | JWT + session cache + captcha endpoints retained; unload logout removed (fix). |
| A08 | Software/Data Integrity | CI present; no unsigned supply-chain changes in scope. |
| A09 | Logging/Monitoring | Failures go through Problem support; Prometheus path denied by default in security config. |
| A10 | SSRF | Dispatcher URLs in config — Ops network trust boundary. |

## 9. CI security-scan

GitHub Actions `security-scan` on PR tip: **SUCCESS** (observed via `gh pr view`).

## 10. Stage 3 conclusion

- **Security PASS for merge-as-code** (application changes are sound relative to stated architecture).
- **Security NOT PASS for Production go-live** while Docker verification, Oracle smoke, CORS Ops confirm, and credential rotation remain open.

Do **not** treat this document as a Production clearance.
