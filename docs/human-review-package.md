# PR #5 — Human Review Package

**Generated:** 2026-07-10  
**Decision:** READY TO MERGE AFTER MANUAL UI REVIEW  

| Item | Live value |
| ---- | ---------- |
| PR | https://github.com/yasamanahmadi88/npg/pull/5 |
| Base | `main` |
| Head | `cursor/full-upgrade-audit-eec2` |
| Head SHA | confirm on PR (`c687d54`+) |
| Changed files | **49** (GitHub + `origin/main...HEAD`) |
| Mergeable | `MERGEABLE` / `CLEAN` |
| CI | frontend **SUCCESS**, backend **SUCCESS**, security-scan **SUCCESS** |
| Review decision | (none yet) |
| Angular resolved | **21.2.18** (`package-lock.json` + declared) |
| Java / Boot | **25** / **4.0.7** (Boot unchanged vs main; `pom.xml` not in PR) |
| Jest | 144 suites / **606** tests |
| Playwright | **8/8** |
| Maven verify | **703** tests |
| npm Critical | **0** |
| npm High residual | **7** (dev/codegen tooling) |
| Docker | **BLOCKED** |
| Live Oracle | **BLOCKED** |
| Non-root routing | **BLOCKED** |

Working tree may show ~477 phantom `M` files; all byte-identical to HEAD (CRLF + `text=auto`). **Do not stage.**

---

## 1. Exact 49-file review inventory

| File | Change type | Category | Why required | Main risk | Test/evidence |
| ---- | ----------- | -------- | ------------ | --------- | ------------- |
| `.env.example` | A | Secrets | Document required env vars | Placeholder mistaken for real secret | Secret scan; placeholders only |
| `.github/workflows/ci.yml` | A | CI | FE/BE/security gates; LF `mvnw` workaround | CI drift / secret grep false positives | GitHub Actions SUCCESS |
| `.gitignore` | M | Secrets / CI | Ignore `.env`, playwright reports, bak files | Over-ignore | Diff review |
| `.npmrc` | M | Dependency | Public registry for CI without Artifactory | Corporate installs need Artifactory restore | `npm ci` |
| `README.md` | M | Documentation | Current stack/prereqs | Docs drift | Manual read |
| `docs/change-log.md` | A | Documentation | Change history | Stale counts | Cross-check this package |
| `docs/diff-scope-review.md` | A | Documentation | EOL root cause | — | — |
| `docs/final-validation-report.md` | A | Documentation | Acceptance matrix | — | — |
| `docs/menu-route-inventory.md` | A | Documentation | Menu→route table | Incomplete live click-through | `menu-routing.spec.ts` |
| `docs/pr-review-checklist.md` | A | Documentation | Manual merge checklist | — | This pass |
| `docs/release-candidate-verification.md` | A | Documentation | RC evidence | — | — |
| `docs/remaining-risks.md` | A | Documentation | Residual risks | — | — |
| `docs/retained-file-inventory.md` | A | Documentation | Retained-file reasons | — | — |
| `docs/root-cause-analysis.md` | A | Documentation | RCA | — | — |
| `docs/security-review.md` | A | Documentation | Security + npm table | — | — |
| `docs/test-report.md` | A | Documentation | Command evidence | — | — |
| `docs/upgrade-audit.md` | A | Documentation | Version matrix | — | — |
| `e2e/portal-critical.spec.js` | A | Tests | Browser critical flows | Mocked API ≠ live Oracle | Playwright 8/8 |
| `eslint.config.js` | M | CI / Dependency | Lint on Angular 21 | Rule noise | `npm run lint` |
| `package-lock.json` | M | Dependency | Lock for npmjs + Angular 21.2.18 + Playwright | Large lock churn | `npm ci` |
| `package.json` | M | Dependency | e2e script; Angular/concurrently/Playwright pins | Peer graph / `legacy-peer-deps` | Jest/build |
| `playwright.config.js` | A | Tests | E2E runner | Needs static build artifact | Playwright |
| `SecurityConfiguration.java` | M | JWT / Security configuration | Direct `JWTFilter` registration; remove `/test/**` | Filter order / auth gaps | See §4 |
| `WebConfigurer.java` | M | CORS | Remove conflicting MVC CORS; reject `*`+credentials | Origin allow-list completeness | `WebConfigurerTest`, IT |
| `JWTConfigurer.java` | D | JWT | Obsolete `SecurityConfigurerAdapter` | Missing filter if not rewired | Replaced in `SecurityConfiguration` |
| `ExceptionTranslator.java` | M | Security configuration | Jakarta/Spring nullability | Error shape | Existing ITs |
| `application-dev.yml` | M | Production configuration / Secrets | Env-overridable JWT | Weak dev default if misused in prod | Diff review |
| `application-prod.yml` | M | Production configuration / Secrets | Require DB user/pass + JWT env | Default JDBC URL host still present | Secret scan; startup needs env |
| `application.yml` | M | CORS / Security configuration | CORS/security related settings | Hardcoded origin list (overridable) | CORS ITs |
| `password-reset-init.route.ts` | M | Routing | Restore reset component/path | Wrong public page | Route + inventory |
| `register.route.ts` | M | Routing | Restore register component/path | Wrong public page | Route + inventory |
| `font-awesome-icons.ts` | M | Theme / UI/Navbar | Sun/moon icons | Missing icon | Navbar theme tests |
| `theme.service.ts` | A | Theme | Central theme state | FOUC / persistence bugs | Unit + E2E |
| `theme.service.spec.ts` | A | Tests | Theme unit coverage | — | Jest |
| `main.component.ts` | M | Authentication | Remove unload logout; init ThemeService | Session/security regression | `main.auth-lifecycle.spec.ts` |
| `main.auth-lifecycle.spec.ts` | A | Tests | Prove no unload logout | — | Jest |
| `menu-routing.spec.ts` | A | Tests | Absolute menu paths | Stub router ≠ full app | Jest |
| `navbar.component.html` | M | Routing / UI/Navbar / Theme | Absolute links + theme toggle | Missed relative link | Jest + E2E menu |
| `navbar.component.ts` | M | Routing / Theme | Absolute search nav + theme API | Relative search regress | Jest + E2E |
| `navbar.theme.spec.ts` | A | Tests | Navbar theme wiring | — | Jest |
| `login.component.ts` | M | Routing / Authentication | Absolute `/dashboard` | Wrong post-login URL | `login.component.spec.ts` |
| `login.component.spec.ts` | M | Tests | Expect `/dashboard` | — | Jest |
| `theme-init.js` | A | Theme | Pre-Angular FOUC prevention | CSP / storage errors | External script; E2E persist |
| `_theme-tokens.scss` | A | Theme | Light/dark tokens | Contrast gaps | Build + manual UI |
| `global.scss` | M | Theme / UI/Navbar | Import tokens; Cosmo-only | Style regressions | Build |
| `index.html` | M | Theme | `data-theme` + `theme-init.js`; `<base href="/">` | Subpath deploy | E2E; non-root BLOCKED |
| `WebConfigurerTest.java` | M | Tests | CORS unit assertions | — | Maven |
| `SecurityWebConfigurationIT.java` | A | Tests | CORS/JWT/static IT (7) | — | Maven (in 703) |

**Not in PR (intentionally):** `pom.xml`, `app-routing.module.ts`, `angular.json` — no semantic change vs `main`.

**Unrelated/broad?** None required for merge beyond docs volume (11 doc files). Lockfile is large but required for registry + Angular patch. No unexplained binaries.

---

## 2. Focused semantic review (risky changes)

### JWT / `SecurityConfiguration` + deleted `JWTConfigurer`

| | |
| -- | -- |
| **Previous** | `http.apply(JWTConfigurer)` via deprecated `SecurityConfigurerAdapter`; also `httpBasic`; `/test/**` permitAll |
| **New** | `addFilterBefore(new JWTFilter(...), UsernamePasswordAuthenticationFilter.class)` once; `/test/**` removed; httpBasic removed |
| **Why correct** | Same filter type/order as old configurer; Boot 4–compatible; closes test bypass |
| **Regression risk** | Double registration (not present); static/OPTIONS blocked; anonymous API broken |
| **Tests** | `JWTFilterTest.testJWTFilter`, `testJWTFilterInvalidToken`, `testJWTFilterMissingAuthorization`, `testJWTFilterMissingToken`, `testJWTFilterWrongScheme`; `SecurityWebConfigurationIT.protectedApiRequiresAuthentication`, `captchaEndpointIsPublic`, `optionsRequestsArePermittedWithoutAuthentication`, `invalidBearerTokenDoesNotAuthenticate`, `staticContentPathIsNotUnauthorized` |

### CORS / `WebConfigurer`

| | |
| -- | -- |
| **Previous** | CorsFilter **plus** `WebMvcConfigurer` with `allowedOriginPatterns("*")` and hardcoded hosts |
| **New** | Single CorsFilter; throw if `*` + credentials |
| **Why correct** | Eliminates conflicting CORS; credentials-safe |
| **Regression risk** | Missing origin in `jhipster.cors.allowed-origins` breaks a real front-door host |
| **Tests** | `WebConfigurerTest.shouldCorsFilterOnApiPath`, `shouldRejectWildcardOriginWithCredentials`, `shouldCorsFilterDeactivatedForNullAllowedOrigins`; `SecurityWebConfigurationIT.corsPreflightAllowsConfiguredOrigin`, `corsPreflightRejectsUnknownOrigin` |

### Secrets / `application-prod.yml` + `.env.example`

| | |
| -- | -- |
| **Previous** | Hardcoded DB password `pass#1400` and JWT base64 secret |
| **New** | `${SPRING_DATASOURCE_USERNAME/PASSWORD}`, `${JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET}`; URL default retained |
| **Why correct** | Prod must not ship secrets |
| **Regression risk** | Prod start fails without env (intended); default JDBC host still points at internal IP |
| **Tests** | Secret scan on PR diff; manual prod config review |

### Authentication / `main.component.ts`

| | |
| -- | -- |
| **Previous** | `@HostListener('window:beforeunload')` → `loginService.logout()` (also used as `ngOnDestroy`) |
| **New** | Hooks removed; `ThemeService` constructed eagerly |
| **Why correct** | Refresh/navigation fired unload and destroyed sessions |
| **Regression risk** | Tab close no longer server-notifies logout (explicit logout still posts `api/logout`); multi-tab may keep other tabs’ memory state until next API 401 |
| **Tests** | `main.auth-lifecycle.spec.ts` — `does not expose unload/destroy logout hooks that clear sessions on refresh`; explicit logout path remains `LoginService.logout()`; token invalidation covered by `JWTFilterTest` / `TokenProviderTest` / `SecurityCache` |

### Routing / navbar + login + account routes

| | |
| -- | -- |
| **Previous** | Relative `routerLink` / `./dashboard` / account routes pointed at dashboard |
| **New** | Absolute `/…` links; register + reset/request restored |
| **Why correct** | Nested URLs no longer prefix-corrupt destinations |
| **Regression risk** | Non-root `base href` / context-path undeployed (**BLOCKED**) |
| **Tests** | `menu-routing.spec.ts`; `login.component.spec.ts`; Playwright `menu click…`, `direct nested URL…`, `browser refresh…`, `back and forward…` |

### Theme

| | |
| -- | -- |
| **Previous** | No runtime theme system |
| **New** | `theme-init.js` (external) + `ThemeService` + tokens + navbar toggle |
| **Why correct** | FOUC-safe; storage key `npg-portal-theme`; system preference only if unset |
| **Regression risk** | Dark contrast on forms/tables; RTL interaction untested in Playwright |
| **CSP** | External script under `script-src 'self'` — **no new inline script**; `'unsafe-inline'` already on `main` CSP (unchanged) |
| **Tests** | `theme.service.spec.ts`; `navbar.theme.spec.ts`; Playwright **`navbar theme toggle switches and persists`** |

### Dependencies

| | |
| -- | -- |
| **Previous** | Angular 21.2.14; concurrently 9.2.1; no `@playwright/test` |
| **New** | Angular **21.2.18**; concurrently **9.2.3**; Playwright 1.57.0; npmjs registry |
| **Why correct** | Closes prod Angular High + Critical shell-quote; enables clean E2E |
| **Regression risk** | Artifactory users must restore `.npmrc`; lockfile large |

### CI

LF-stripped `mvnw` only in the workflow (does not renormalize repo). Fixes Linux exit 127 on CRLF shebang.

---

## 3. Non-root routing classification: **BLOCKED**

Evidence:

- `index.html` has `<base href="/" />` (root).
- `application.yml` supports optional `server.servlet.context-path` for API docs patterns → **subpath is possible** at the server layer.
- No nginx/compose deploy-url proving root-only forever → cannot use NOT APPLICABLE.

**Required test before enabling subpath:** build with matching `base href` / `deployUrl`, serve under `/<context>/`, verify menu absolute links, refresh, and deep links.

Absolute `routerLink="/x"` targets application root URLs (correct with `base href="/"`).

---

## 4. Remaining npm High findings (Critical = 0)

| Advisory | Package | Dependency chain | Dev/runtime | In production SPA bundle | Fix available | Decision |
| -------- | ------- | ---------------- | ----------- | ------------------------ | ------------- | -------- |
| GHSA-gh4j-gqv2-49f6 | `fast-xml-parser` | transitive tooling | transitive | No | yes | Accept |
| (generator umbrella) | `generator-jhipster` | direct `devDependency` | development | No | major 8.11.0 | Accept — breaking |
| GHSA-xxjr-mmjv-4gpg | `lodash` | via generator-jhipster / java-parser | transitive | No | via generator major | Accept |
| GHSA-xxjr-mmjv-4gpg | `lodash-es` | via chevrotain (generator) | transitive | No | via generator major | Accept |
| GHSA-x9g3-xrwr-cwfg | `piscina` | via generator-jhipster | transitive | No | via generator major | Accept |
| GHSA-hffm-xvc3-vprc | `simple-git` | via generator-jhipster | transitive | No | via generator major | Accept |
| GHSA-vv9j-gjw2-j8wp | `yeoman-environment` | via generator-jhipster | transitive | No | via generator major | Accept |

**Dependency scan status for merge:** production Angular High findings **fixed** at 21.2.18; remaining High are **accepted development/codegen residuals**, not a production-bundle Critical/High set.

---

## 5. Final risks for the reviewer

| Final risk | Severity | Status | Required reviewer action |
| ---------- | -------- | ------ | ------------------------ |
| Dark/RTL visual contrast & forms | Medium | Needs eyes | Checklist Light/Dark/RTL/Login/Menu |
| Non-root / context-path deploy | Medium | BLOCKED | Accept root-only **or** run subpath test |
| Live Oracle / Liquibase dialect | Medium | BLOCKED | Accept H2-only **or** run Oracle verification |
| Docker/runtime image | Medium | BLOCKED | Accept **or** validate on Docker host |
| CORS allow-list completeness | Medium | Tested for sample origins | Confirm production front-door origins are listed/overridden |
| Prod JDBC URL default host | Low | Present as default only | Ensure env sets `SPRING_DATASOURCE_URL` in real prod |
| Historical secrets in git history | High (ops) | Outside PR diff | Rotate credentials operationally |
| Phantom local `git status` | Low | Understood | Do not commit CRLF phantoms |
| generator-jhipster npm High | Low (runtime) | Accepted | Optional follow-up major upgrade |

## Recommendation

**READY TO MERGE AFTER MANUAL UI REVIEW**

Automated gates are green, the remote PR is **49** files (not 499), security/CORS/JWT/auth changes have focused tests, and Critical npm findings are cleared. Merge only after a human completes `docs/pr-review-checklist.md` (UI + acceptance of Docker/Oracle/non-root blockers).
