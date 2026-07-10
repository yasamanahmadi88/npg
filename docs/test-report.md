# Test Report (release candidate)

**Branch:** `cursor/full-upgrade-audit-eec2`  
**HEAD:** `78c2f0d5679f775b8deb7fd430fc3ee4710a53b1`  
**Worktree:** `/tmp/npg-final-verification` (clean checkout, no reused `node_modules`/`target`)

## Frontend

| Command | Exit | Duration | Result |
| ------- | ---: | -------: | ------ |
| `npm ci --no-fund --no-audit` | 0 | ~26s | 1786 packages (pre-Angular bump); re-run after bump OK |
| `npm ls --depth=0` | 0 | ~1s | OK |
| `npm run lint` | 0 | ~5s | 0 errors, 6 warnings |
| `npx jest --config jest.conf.js --watch=false --coverage=false --runInBand` | 0 | ~66s | **144 suites, 606 tests, 0 failed, 0 skipped** |
| `npx ng build --configuration production` | 0 | ~40s | PASS (typecheck via build) |
| `npx playwright test --config=playwright.config.js` | 0 | ~7s | **8 passed** |

### Why 606 (not 605)

New PR specs add 13 tests (theme 8 + auth-lifecycle 1 + menu-routing 2 + navbar.theme 2). Clean HEAD consistently reports **606**. An intermediate “605” figure was from an earlier partial suite set before all new specs were present.

## Backend

| Command | Exit | Duration | Result |
| ------- | ---: | -------: | ------ |
| `sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf && /tmp/mvnw.lf -ntp -P-webapp clean verify --batch-mode` | 0 | ~40s | **Tests run: 703, Failures: 0, Errors: 0, Skipped: 0** |

### 703 vs prior 700

`SecurityWebConfigurationIT` grew from 4 → **7** tests (+3). Those **7 are included in 703**, not an additional separate total.

Security-related subsets inside 703: `JWTFilterTest` (5), `TokenProviderTest` (7), `WebConfigurerTest` (8), `SecurityWebConfigurationIT` (7).

## Scans

| Check | Result |
| ----- | ------ |
| Secret scan on `origin/main...HEAD` | PASS — prod password/JWT literals removed; no new secrets |
| npm audit | Critical **0** after Angular 21.2.18 + concurrently 9.2.3; **7 High** remain in generator-jhipster/Yeoman tooling (not SPA runtime) |
| Docker | BLOCKED |
| Oracle | BLOCKED |
