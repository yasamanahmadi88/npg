# Final Validation Report (cleaned)

## Executive summary

The original PR listed **499 files**, almost all CRLF↔LF whole-file rewrites against `origin/main` (which already has Angular 21 / Java 25 / Spring Boot 4.0.7). Cleanup reverted **460** EOL-only files and preserved ~**41** semantic files. Builds and tests were re-executed on the cleaned tree. Playwright browser E2E (5 scenarios) passed against the production static build with API mocks.

## Diff cleanup

| Metric | Value |
| ------ | ----: |
| Initial changed files | 499 |
| EOL-only reverted | 460 |
| Final changed files | ~41 |
| Formatting-only reverted | 0 (none separate from EOL) |
| Real functional/security/theme/test/docs retained | ~41 |

See `docs/diff-scope-review.md`.

## Acceptance matrix

### Build and test

| Check | Status | Command | Evidence |
| ----- | ------ | ------- | -------- |
| Frontend clean install | PASS | `npm ci` | exit 0, 1786 packages |
| Frontend lint | PASS | `npx eslint "src/main/webapp/**/*.ts"` | 0 errors |
| Frontend unit tests | PASS | `npx jest … --watch=false` | 143 suites / 605 tests |
| Frontend production build | PASS | `npx ng build --configuration production` | Build at 13:33:53Z |
| Backend clean verify | PASS | `mvnw -P-webapp clean verify` | 700 tests, 0 failures |
| Security unit/IT | PASS | included in verify + WebConfigurer/SecurityWebConfigurationIT | exit 0 |
| Browser E2E | PASS | `npx playwright test --config=playwright.config.js` | 5 passed |
| Docker runtime | BLOCKED | `docker` missing | — |
| Oracle live | BLOCKED | no Oracle; H2 ITs only | — |
| Dependency scan | EXECUTED | npm audit | High/Critical in **dev** tooling documented |
| Secret scan | PASS | grep on PR files | no prod password/JWT literal retained |

### Functional (evidence type)

| Feature | Status | Evidence |
| ------- | ------ | -------- |
| Absolute menu routing | PASS | code + `menu-routing.spec.ts` |
| Login redirect | PASS | unit + Playwright login load |
| Theme toggle + persistence | PASS | unit + Playwright |
| Unload logout removed | PASS | `main.auth-lifecycle.spec.ts` |
| CORS allow-list | PASS | WebConfigurerTest + SecurityWebConfigurationIT |
| JWT direct filter registration | PASS | SecurityConfiguration + ITs (auth required on `/api/account`) |
| Browser visual screenshots | PASS (on failure artifacts); happy-path screenshots not committed | Playwright |
| Nested SPA URL via static server | PASS | `serve -s` + Playwright |
| Real Oracle-backed forms | BLOCKED | — |

## Repository status

| Item | Value |
| ---- | ----- |
| Branch | `cursor/full-upgrade-audit-eec2` |
| PR | https://github.com/yasamanahmadi88/npg/pull/5 (existing; updated, not replaced) |
| History rewrite | Not used |
| Force-push | Not used |

## Remaining blockers

1. Docker not installed in agent environment.
2. Oracle / Testcontainers Oracle not available.
3. Corporate Artifactory unreachable here — `.npmrc` + lockfile use npmjs for CI; restore Artifactory for on-prem.
4. Local `git status` may show phantom `M` on CRLF blobs under `text=auto`; **do not re-add** those files. Source of truth: `git diff origin/main...HEAD`.
