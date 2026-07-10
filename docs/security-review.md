# Security Review

## Authentication / authorization

| Control | Status | Notes |
| ------- | ------ | ----- |
| JWT Bearer auth | PASS | `TokenProvider` + `JWTFilter` + `SecurityCache` |
| Password hashing | PASS | BCrypt |
| API default deny for `/api/**` | PASS | Authenticated required except explicit public matchers |
| Admin API | PASS | `/api/admin/**` requires `ROLE_ADMIN` |
| Method security | PASS | `@EnableMethodSecurity` + custom `@Secured` resource manager |
| CSRF | N/A (by design) | Disabled; documented for stateless JWT SPA |
| CORS | PASS | Allow-list origins; rejects `*` with credentials |
| Security headers | PASS | CSP, Referrer-Policy, frame deny, content-type options |
| Actuator | PARTIAL | Sensitive endpoints denyAll/admin; health public by design; MockMvc health mapping residual issue |
| Secrets in repo | FIXED (prod) | Prod JWT/DB password now env-required; dev retains local defaults via env placeholders |
| Error leakage | REVIEWED | Problem JSON; avoid client stack traces in prod profiles |

## ASVS L2 traceability (selected)

| ASVS ID | Requirement | Status | Evidence |
| ------- | ----------- | ------ | -------- |
| V2.1 | Password storage | PASS | BCryptPasswordEncoder |
| V3.1 | Session token | PASS | JWT + server-side SecurityCache invalidation |
| V4.1 | Access control | PASS | SecurityFilterChain + method security |
| V5.1 | Input validation | PASS | Bean Validation on resources |
| V7.1 | Error handling | PASS | ExceptionTranslator Problem responses |
| V8.1 | Sensitive data | PARTIAL | Secrets moved to env for prod; rotate historical JWT sample key |
| V9.1 | Communications | INFRA | TLS termination expected at reverse proxy |
| V13.1 | API | PASS | Authenticated `/api/**` |
| V14.1 | Config | PASS | Profile-specific YAML + `.env.example` |

## Scanner results

| Scan | Status | Notes |
| ---- | ------ | ----- |
| `npm audit --audit-level=high` | EXECUTED | 43 advisories reported (incl. High/Critical in **dev** tooling: `vite` via Angular builders, `ws`/`engine.io` via BrowserSync, `yeoman-environment` via `generator-jhipster`). Production runtime SPA bundle does not ship these generators. `npm audit fix` applied where non-breaking; force upgrades deferred to avoid breaking Angular 21 peer graph. |
| Maven dependency tree | EXECUTED | Captured in CI workflow |
| Heuristic secret scan | EXECUTED | CI job; prod password literal removed |
| OWASP ZAP | BLOCKED | No isolated running stack + Docker unavailable |
| Container scan | BLOCKED | Docker unavailable |

### Unresolved High/Critical (dev-time)

| Package | Reachability | Action |
| ------- | ------------ | ------ |
| `vite` (dev builder) | Dev server only | Track Angular builder updates |
| `ws` / `engine.io` (BrowserSync) | Dev only | Upgrade browser-sync when compatible |
| `yeoman-environment` (generator-jhipster) | Codegen only, not runtime | Upgrade generator-jhipster when peer-safe |

## Residual risks

1. Historical commits may still contain the former JWT sample secret / DB password — rotate in all environments.
2. In-memory `SecurityCache` is not cluster-safe.
3. `/api/cp-eyrtyertye` remains public (captcha-related obfuscated endpoint).
4. Actuator health public by design — ensure no sensitive details when unauthorized (`show-details: when_authorized`).
