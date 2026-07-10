# Test Report (blocker-resolution pass)

**Branch:** `cursor/full-upgrade-audit-eec2`  
**HEAD at test time:** post-`bcbdfd1` working tree (includes mobile Search CSS follow-up)  
**Date:** 2026-07-10

## Frontend (re-run after code changes)

| Command | Exit | Result |
| ------- | ---: | ------ |
| `npm run lint` | 0 | 0 errors, 6 warnings (pre-existing unused eslint-disable) |
| `npx jest --config jest.conf.js --watch=false --coverage=false --runInBand` | 0 | **145 suites / 609 tests** (was 144/606; +1 suite / +3 from `profile.service.spec.ts`) |
| Focused: `profile.service.spec.ts` + ribbon + navbar.theme | 0 | 6 passed |
| `npm run webapp:prod` | 0 | PASS (typecheck via production build) |
| `npx playwright test --config=playwright.config.js` | 0 | **9/9 passed** (was 8; + mobile Search viewport) |

## Backend (re-run after `application-prod.yml` change)

| Command | Exit | Result |
| ------- | ---: | ------ |
| `sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf && /tmp/mvnw.lf -ntp -P-webapp clean verify --batch-mode` | 0 | **Tests run: 703, Failures: 0, Errors: 0, Skipped: 0** |

## Scans / environment

| Check | Result |
| ----- | ------ |
| npm Critical | 0 |
| npm High | 7 (generator-jhipster/Yeoman tooling — ACCEPTED) |
| Docker | BLOCKED (`docker` not installed) — runbook added |
| Oracle | BLOCKED — runbook added |
| Source maps | Used **locally only** for `Ct` investigation; not enabled permanently in `angular.json` |

## `Ct` / `ct.split` verification commands

```bash
# Local source-map build (do not commit sourceMap:true for public prod)
# Reproduce: serve -s static + mock /api only → Ct = HttpErrorResponse parse failure on /management/info HTML
# With JSON /management/info → no Ct
# With non-string ribbon → profile.service.ts split (now guarded)
```
