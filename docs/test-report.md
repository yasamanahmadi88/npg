# Test Report

## Commands and results (2026-07-10)

| Check | Command | Status | Evidence |
| ----- | ------- | ------ | -------- |
| Frontend install | `npm ci` (registry.npmjs.org) | PASS | 1769 packages |
| Frontend unit tests | `npx jest --config jest.conf.js --watch=false --coverage=false` | PASS | 143 suites / 605 tests |
| Frontend production build | `npx ng build --configuration production` | PASS | `target/classes/static/index.html` |
| Backend compile | `./mvnw -ntp -P-webapp compile` | PASS | BUILD SUCCESS |
| Backend unit + IT | `./mvnw -ntp -P-webapp verify` | PASS | 700 tests, 0 failures |
| Frontend lint | `npx eslint src/main/webapp/**/*.{js,ts}` | PENDING re-check after eslint config soften | Pre-existing empty-object / project-service issues reduced |
| Docker Compose | `docker compose up` | BLOCKED | Docker not installed |
| Browser E2E | Playwright | BLOCKED | No headed browser automation in this agent environment |
| Oracle live | — | BLOCKED | No Oracle instance |
| npm audit | `npm audit --audit-level=high` | EXECUTED | See below |

## New / updated tests

- `theme.service.spec.ts` — theme init, system preference, persistence, toggle
- `navbar.theme.spec.ts` — accessible navbar toggle
- `menu-routing.spec.ts` — absolute menu path inventory
- `login.component.spec.ts` — expects `/dashboard`
- `WebConfigurerTest` — rejects wildcard+credentials
- `SecurityWebConfigurationIT` — API auth + CORS allow/deny

## npm audit

Run at delivery time; treat High/Critical as must-triage. CI records audit output without failing the whole pipeline until advisories are classified (`|| true` with follow-up in remaining-risks).
