# Test Report (cleaned branch)

**Branch:** `cursor/full-upgrade-audit-eec2`  
**Evidence date:** 2026-07-10 (post EOL cleanup)

## Frontend

| Check | Command | Exit | Result |
| ----- | ------- | ---: | ------ |
| Clean install | `npm ci --no-fund --no-audit` | 0 | added 1786 packages |
| Lint | `npx eslint "src/main/webapp/**/*.ts"` | 0 | 0 errors, 6 warnings (unused eslint-disable) |
| Unit tests | `npx jest --config jest.conf.js --watch=false --coverage=false` | 0 | **143 suites, 605 tests, 0 failed, 0 skipped** |
| Auth lifecycle unit | `npx jest … main.auth-lifecycle.spec.ts` | 0 | 1 passed |
| Production build | `npx ng build --configuration production` | 0 | `Build at: 2026-07-10T13:33:53.807Z`, artifact `target/classes/static/index.html` |
| E2E (Playwright) | `npx playwright test --config=playwright.config.js` | 0 | **5 passed** (login load, theme persist, 404, nested SPA URL, mobile navbar) |

E2E serves production static assets with `serve -s` and mocks `/api/**` (Oracle not required).

## Backend

| Check | Command | Exit | Result |
| ----- | ------- | ---: | ------ |
| Clean verify | `mvnw -ntp -P-webapp clean verify` (LF-normalized wrapper copy; main `mvnw` is CRLF) | 0 | **Tests run: 700, Failures: 0, Errors: 0, Skipped: 0**, BUILD SUCCESS |

## Scans

| Check | Result |
| ----- | ------ |
| Secret scan on PR files | No `pass#1400` / private keys / AKIA hits in retained files |
| `application-prod.yml` | JWT + DB password require env vars |
| npm audit | Dev-tooling High/Critical remain (`vite`, `ws`/BrowserSync, `yeoman-environment`); not production runtime bundle |
| Docker runtime | **BLOCKED** — `docker` not installed |
| Oracle live / Testcontainers Oracle | **BLOCKED** — no Oracle; H2 used for ITs |

## Type check

No separate `tsc` script; production `ng build` performs Angular compilation/type checking (**PASS** via build).
