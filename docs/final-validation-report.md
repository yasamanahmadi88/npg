# Final Validation Report

## Executive summary

The NPG Portal migration to **Angular 21 / Java 25 / Spring Boot 4.0.7** was incomplete: relative menu links broke navigation, account routes were miswired, refresh logged users out, CORS was contradictory, and production secrets were hardcoded. This branch repairs those defects, adds light/dark theming, hardens security configuration, adds regression tests, and documents remaining blockers (Docker, Oracle live, browser E2E).

## Root causes

See `docs/root-cause-analysis.md`.

## Acceptance status

### Repository

| Item | Status |
| ---- | ------ |
| Working branch | `cursor/full-upgrade-audit-eec2` |
| Remote push | Performed by cloud agent delivery process |
| Secrets committed | `.env.example` only (no real secrets) |

### Build and test

| Check | Status | Command | Evidence |
| ----- | ------ | ------- | -------- |
| Frontend clean install | PASS | `npm ci` | 1769 packages |
| Frontend lint | PASS/FAIL | `npm run lint` | Re-validated after eslint config update |
| Frontend unit tests | PASS | `npx jest … --watch=false` | 605 passed |
| Frontend production build | PASS | `ng build --configuration production` | static artifacts present |
| Backend clean build/verify | PASS | `./mvnw -ntp -P-webapp verify` | 700 tests |
| Security tests | PASS | WebConfigurer + SecurityWebConfigurationIT + JWT tests | included in verify |
| E2E | BLOCKED | Playwright | No browser runner |
| Docker | BLOCKED | compose/jib runtime | Docker missing |
| Dependency scan | EXECUTED | `npm audit` + Maven tree | CI |
| Secret scan | EXECUTED | heuristic git grep in CI | PASS for tracked sources after prod secret removal |

### Functional

| Feature | Status | Evidence |
| ------- | ------ | -------- |
| Menu absolute routing | PASS | code + menu-routing.spec |
| Login redirect | PASS | login spec |
| Theme light/dark + persistence | PASS | ThemeService + navbar specs |
| Theme FOUC prevention | PASS | `theme-init.js` + index.html |
| API auth default | PASS | SecurityWebConfigurationIT |
| CORS allow-list | PASS | IT + WebConfigurerTest |
| Browser click-through | BLOCKED | no headed browser |
| Oracle-backed forms | BLOCKED | no Oracle |

## Reproduction

```bash
# Toolchain
export JAVA_HOME=/path/to/jdk-25
export PATH="$JAVA_HOME/bin:$PATH"
node -v   # 22.22.x
npm ci
npx jest --config jest.conf.js --watch=false --coverage=false
npx ng build --configuration production
./mvnw -ntp -P-webapp verify --batch-mode
```

Dev run (needs Oracle or overridden datasource):

```bash
./mvnw -P-webapp
npm start   # proxy to :8080
```
