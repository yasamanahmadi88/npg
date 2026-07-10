# Final Validation Report (cleaned)

## Executive summary

The original PR listed **499 files**, almost all CRLF↔LF whole-file rewrites against `origin/main` (which already has Angular 21 / Java 25 / Spring Boot 4.0.7). Cleanup reverted **460** EOL-only files and retained **44** semantic files. Builds and tests were re-executed on the cleaned tree. Playwright browser E2E (**5** scenarios) passed against the production static build with API mocks.

## Diff cleanup

| Metric | Value |
| ------ | ----: |
| Initial changed files | 499 |
| EOL-only reverted | 460 |
| Formatting-only reverted | 0 (none separate from EOL) |
| Final changed files | **44** |
| Real functional/security/theme/test/docs/tooling retained | **44** |

See `docs/diff-scope-review.md` and `docs/menu-route-inventory.md`.

## Acceptance matrix

Statuses use only: **PASS** / **FAIL** / **BLOCKED** / **NOT APPLICABLE**.

### Build and test

| Check | Status | Command | Evidence |
| ----- | ------ | ------- | -------- |
| Frontend clean install | PASS | `npm ci` | exit 0, 1786 packages |
| Frontend lint | PASS | `npx eslint "src/main/webapp/**/*.ts"` | 0 errors, 6 warnings |
| Frontend unit tests | PASS | `npx jest --config jest.conf.js --watch=false --coverage=false` | Re-verify: 144 suites / 606 tests / 0 failed / 0 skipped |
| Frontend production build | PASS | `npx ng build --configuration production` | Build at 2026-07-10T13:33:53.807Z |
| Backend clean verify | PASS | `mvnw -P-webapp clean verify` (LF wrapper copy) | 700 tests, 0 failures, 0 errors, 0 skipped |
| Security unit/IT | PASS | included in verify + `WebConfigurerTest` / `SecurityWebConfigurationIT` | exit 0 |
| Browser E2E | PASS | `npx playwright test --config=playwright.config.js` | 5 passed (mocked API + `serve -s`) |
| Docker runtime | BLOCKED | `docker` not installed | — |
| Oracle live | BLOCKED | no Oracle; H2 ITs only | — |
| npm High/Critical | PASS (documented) | `npm audit` | Remaining findings are **dev** tooling; see `docs/security-review.md` |
| Secret scan | PASS | grep on PR files | no prod password/JWT literal retained |

### Functional

| Feature | Status | Evidence |
| ------- | ------ | -------- |
| Absolute menu routing | PASS | code + `menu-routing.spec.ts` + inventory |
| Login redirect | PASS | unit + Playwright login load |
| Theme toggle + persistence | PASS | unit + Playwright |
| Unload logout removed | PASS | `main.auth-lifecycle.spec.ts` |
| Explicit logout still works | PASS | `LoginService.logout()` path unchanged; unit coverage |
| CORS allow-list | PASS | `WebConfigurerTest` + `SecurityWebConfigurationIT` |
| JWT direct filter registration | PASS | `SecurityConfiguration` + ITs |
| Nested SPA URL / refresh via static server | PASS | `serve -s` + Playwright |
| Full live UI (Oracle-backed forms) | BLOCKED | — |
| Happy-path screenshot baselines committed | NOT APPLICABLE | Playwright failure artifacts only; not committed as baselines |

## Repository status

| Item | Value |
| ---- | ----- |
| Branch | `cursor/full-upgrade-audit-eec2` |
| PR | https://github.com/yasamanahmadi88/npg/pull/5 (existing; updated, not replaced) |
| Local vs remote | In sync after push of corrective commits |
| History rewrite | Not used |
| Force-push | Not used |
| Working tree | May show phantom `M` on CRLF+`text=auto` files; ignore — do not commit |

## Remaining blockers

1. Docker not installed in agent environment → Docker runtime **BLOCKED**.
2. Oracle / Testcontainers Oracle not available → live Oracle **BLOCKED**.
3. Corporate Artifactory unreachable here — `.npmrc` + lockfile use npmjs for CI; restore Artifactory for on-prem.
4. Full authenticated menu click-through against a live API remains **BLOCKED** without Oracle/backend stack.
