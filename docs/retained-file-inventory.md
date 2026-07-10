# Retained file inventory (PR #5 vs `origin/main`)

Exact remote/local comparison: **45 files** (A21 / M23 / D1).

| File | Category | Reason retained | Semantic change | Test/evidence |
| ---- | -------- | --------------- | --------------- | ------------- |
| `.env.example` | Secrets/configuration | Document required env vars | Placeholders only | Secret scan |
| `.github/workflows/ci.yml` | CI | Quality gates; LF mvnw workaround | Java 25, Node 22, verify | GitHub Actions |
| `.gitignore` | Other required | Ignore env/playwright artifacts | Ignore rules | — |
| `.npmrc` | Dependency | Public registry for CI sandboxes | registry + legacy-peer-deps note | npm ci |
| `README.md` | Documentation | Stack/prereqs for reviewers | Rewrite for current stack | — |
| `docs/change-log.md` | Documentation | Change history | Docs | — |
| `docs/diff-scope-review.md` | Documentation | EOL root cause | Docs | — |
| `docs/final-validation-report.md` | Documentation | Acceptance matrix | Docs | — |
| `docs/menu-route-inventory.md` | Documentation | Menu→route table | Docs | menu-routing.spec |
| `docs/remaining-risks.md` | Documentation | Residual risks | Docs | — |
| `docs/root-cause-analysis.md` | Documentation | RCA | Docs | — |
| `docs/security-review.md` | Documentation | Security review | Docs | — |
| `docs/test-report.md` | Documentation | Test evidence | Docs | — |
| `docs/upgrade-audit.md` | Documentation | Version matrix | Docs | — |
| `docs/release-candidate-verification.md` | Documentation | RC decision + evidence | Docs | this pass |
| `e2e/portal-critical.spec.js` | Tests | Browser E2E | 8 critical flows | Playwright 8/8 |
| `eslint.config.js` | Other required | Lint works on Angular 21 | Config | npm run lint |
| `package-lock.json` | Dependency | npmjs URLs + Angular 21.2.18 + Playwright | Lock updates | npm ci / audit |
| `package.json` | Dependency | e2e script; Playwright; Angular/concurrently patches | Version bumps | jest/build |
| `playwright.config.js` | Tests | E2E runner config | New | Playwright |
| `SecurityConfiguration.java` | Security / JWT | Direct JWT filter registration | Filter wiring | JWTFilterTest + IT |
| `WebConfigurer.java` | CORS | Remove conflicting CORS | Single CorsFilter | WebConfigurerTest |
| `JWTConfigurer.java` | Security | Deleted obsolete adapter | Delete | compile |
| `ExceptionTranslator.java` | Other required | Jakarta/Spring nullability | Annotation fix | ITs |
| `application-dev.yml` | Secrets/configuration | Env-overridable JWT | Config | — |
| `application-prod.yml` | Secrets/configuration | Require DB/JWT secrets | Remove literals | secret scan |
| `application.yml` | Secrets/configuration | CORS/security related | Config | IT |
| `password-reset-init.route.ts` | Routing | Restore reset component | Route fix | menu inventory |
| `register.route.ts` | Routing | Restore register component | Route fix | menu inventory |
| `font-awesome-icons.ts` | Theme / UI | Sun/moon icons | Icons | navbar theme tests |
| `theme.service.ts` | Theme | Central theme service | New | unit + E2E |
| `theme.service.spec.ts` | Tests | Theme unit tests | New | Jest |
| `main.component.ts` | Authentication | Remove unload logout | Behavior fix | auth-lifecycle spec |
| `main.auth-lifecycle.spec.ts` | Tests | Prove no unload logout | New | Jest |
| `menu-routing.spec.ts` | Tests | Absolute menu paths | New | Jest |
| `navbar.component.html` | Routing / Theme / UI | Absolute links + theme toggle | Markup | Jest + E2E |
| `navbar.component.ts` | Routing / Theme | Absolute search + theme API | TS | Jest + E2E |
| `navbar.theme.spec.ts` | Tests | Navbar theme | New | Jest |
| `login.component.ts` | Routing | Absolute `/dashboard` | Navigate fix | Jest |
| `login.component.spec.ts` | Tests | Expect `/dashboard` | Spec | Jest |
| `theme-init.js` | Theme | FOUC-safe init | New | E2E persistence |
| `_theme-tokens.scss` | Theme | Tokens | New | build |
| `global.scss` | UI / Theme | Import tokens; Cosmo-only | SCSS | build |
| `index.html` | Theme | theme-init + theme class | HTML | E2E |
| `WebConfigurerTest.java` | Tests | CORS unit coverage | Assertions | Maven |
| `SecurityWebConfigurationIT.java` | Tests | CORS/JWT/static IT | New (7 tests) | Maven 703 |

## Counts by category

| Category | Count |
| -------- | ----: |
| Documentation | 11 |
| Tests | 9 |
| Routing | 6 |
| Theme | 6 |
| Security/CORS/JWT | 4 |
| Secrets/configuration | 4 |
| Dependency | 3 |
| CI | 1 |
| UI / other | 1+ overlapping |
| Deleted security adapter | 1 |
