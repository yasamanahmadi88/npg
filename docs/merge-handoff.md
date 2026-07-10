# Merge handoff — PR #5

**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Base:** `main`  
**Head:** `cursor/full-upgrade-audit-eec2`  
**Reviewed SHA:** `d7f2bde5d4eaf05b4221874c9bce9508b1d87649`  
**Remote changed files:** **58** (A31 / M26 / D1)  
**Mergeable:** MERGEABLE / CLEAN  
**Required reviews:** none configured (human approval still required)  
**Conflicts:** none  

Do **not** merge automatically. Do **not** force-push. Do **not** rewrite history.

## Purpose

Complete the Angular 21 / Java 25 / Spring Boot 4 upgrade audit cleanup: restore broken navigation and account routes, harden JWT/CORS/secret configuration, add Light/Dark theme with persistence, fix mobile Search layout, add CI and browser/security tests, and document operational Production gates that remain outside repository verification.

## Main fixes

* Menu and route navigation (absolute `routerLink` / login navigation)
* Account routes (register + password-reset restored)
* Authentication persistence on refresh (unload logout removed)
* Explicit logout behavior retained
* JWT filter registration (direct registration; `JWTConfigurer` deleted)
* CORS consolidation (single filter; reject `*` + credentials)
* Production secret externalization (DB user/password + JWT via env; no weak prod defaults)
* Light/Dark theme + theme persistence (`ThemeService`, `theme-init.js`, tokens)
* RTL support retained/verified in manual UI review
* Mobile Search layout (dashboard + navbar responsive CSS)
* Profile info contract guard (`display-ribbon-on-profiles` string-only split; info failure degrade)
* Angular dependency security patch (21.2.18) + concurrently Critical fix
* Browser (Playwright) and security (IT) tests + GitHub Actions CI

## Verified evidence

| Check            | Result                 |
| ---------------- | ---------------------- |
| Frontend Jest    | 145 suites / 609 tests |
| Playwright       | 9/9                    |
| Maven verify     | 703 tests              |
| Lint             | PASS (0 errors)        |
| CI frontend      | PASS                   |
| CI backend       | PASS                   |
| CI security scan | PASS                   |
| npm Critical     | 0                      |
| npm High         | 7 tooling-only         |

## Repository scope

| Item | Value |
| ---- | ----- |
| Final SHA | `d7f2bde5d4eaf05b4221874c9bce9508b1d87649` |
| Final changed-file count | **58** |
| Added | 31 |
| Modified | 26 |
| Deleted | 1 (`JWTConfigurer.java`) |
| PR base / head | `main` ← `cursor/full-upgrade-audit-eec2` |
| Mergeable status | MERGEABLE / CLEAN |
| EOL normalization | **Not included** (CRLF-only noise previously reverted; no renormalization) |

### Scope classification (all 58 files)

| Category | Examples |
| -------- | -------- |
| Routing / UI / auth UX | navbar, login, register/password-reset routes, main.component |
| Security / CORS / JWT | `SecurityConfiguration`, `WebConfigurer`, ExceptionTranslator, JWTConfigurer delete |
| Production / config | `application*.yml`, `.env.example`, `.gitignore` |
| Theme / mobile layout | ThemeService, theme-init, tokens, dashboard/navbar SCSS |
| Tests / CI | Jest specs, Playwright, SecurityWebConfigurationIT, `.github/workflows/ci.yml` |
| Docs | `docs/**` |
| Dependency security patch | `package.json` / `package-lock.json` (Angular 21.2.18, concurrently) |
| Tooling support | `.npmrc`, `eslint.config.js`, `README.md` |

**Unexplained files:** none.

## Known accepted code-scope residuals

* Seven High npm findings in generator-jhipster / Yeoman **development tooling** (not SPA runtime) — ACCEPTED
* Root `<base href="/">` remains unchanged — non-root topology **BLOCKED** until Architecture decision
* Docker and Oracle **not** executed in repository verification — Production gates remain open
* Production CORS exact origin confirmation pending Ops (`docs/production-cors-checklist.md`)
* Historical DB/JWT credential rotation required before Production (`docs/credential-rotation-plan.md`)

## Decisions (unchanged)

| Gate | Decision |
| ---- | -------- |
| Code merge | **READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS** |
| Production | **NOT READY FOR PRODUCTION** |

## Related handoff docs

* `docs/final-merge-checklist.md`
* `docs/production-readiness-gate.md`
* `docs/post-merge-smoke-test.md`
* `docs/merge-rollback-plan.md`
* `docs/docker-verification-runbook.md`
* `docs/oracle-smoke-test-runbook.md`
* `docs/production-cors-checklist.md`
* `docs/credential-rotation-plan.md`

## Remaining owners

| Action | Owner | Required before merge | Required before Production | Evidence |
| ------ | ----- | --------------------- | -------------------------- | -------- |
| Human code review | Reviewer | Yes | Yes | Approval |
| Docker validation | Ops | No, if accepted | Yes | Runbook results |
| Oracle smoke test | DBA/Ops | No, if accepted | Yes | Smoke-test report |
| Deployment-path decision | Architecture/Deploy | No, if accepted | Yes | Written decision |
| Production CORS origin | Ops/Security | No, if accepted | Yes | CORS checklist |
| Credential rotation | Security/Ops/DBA | No, if policy allows | Yes | Rotation confirmation |
| npm tooling residual acceptance | Reviewer/Security | Yes | Yes | Risk acceptance |

Named individuals are intentionally not assigned in-repo; teams fill owners in their tracker.
