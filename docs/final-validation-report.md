# Final validation report — PR #5

**Updated:** 2026-07-10T15:21Z  
**Branch:** `cursor/full-upgrade-audit-eec2`  
**Tip (pre-blocker-pass commit):** see latest push SHA  
**Base:** `main`  
**Remote changed files:** 50+ (docs/code from this blocker pass will increase)  
**GitHub CI:** PASS (frontend / backend / security-scan) on tip `f5d2ad0`  
**Mergeable:** CLEAN  

## Stack

| Component | Version |
| --------- | ------- |
| Angular | 21.2.18 |
| Java | 25 |
| Spring Boot | 4.0.7 |
| Node (verify) | 22.22.2 |

## Test evidence (re-run after blocker-pass code changes — see test-report.md)

| Suite | Status |
| ----- | ------ |
| Frontend Jest | **145 suites / 609 tests** PASS |
| Playwright | **9/9** PASS |
| Maven verify | **703** PASS |
| npm Critical | 0 |
| npm High | 7 (generator-jhipster/Yeoman tooling — ACCEPTED for SPA runtime) |

## Blocker resolution summary

| Item | Status | Classification / notes |
| ---- | ------ | ---------------------- |
| `ct.split` / `Ct` pageerror | MOCK-ONLY (observed UI review) + FIXED (latent) | See below |
| Mobile Search layout | FIXED | Dashboard responsive CSS + Playwright viewports |
| Docker runtime | BLOCKED | `docs/docker-verification-runbook.md` |
| Oracle smoke | BLOCKED | `docs/oracle-smoke-test-runbook.md` |
| Non-root context-path | BLOCKED | Topology unknown; `<base href="/">`; no owner-approved root-only decision |
| Production CORS origins | BLOCKED (Ops) | Prod profile defaults to HTTPS MCI hosts; Ops must verify |
| Prod secrets fail-fast | PASS (config) | Username/password/JWT have no defaults; JDBC host default documented |
| Historical credential rotation | BLOCKED | `docs/credential-rotation-plan.md` |

### `ct.split` / `Ct` investigation (source-mapped)

**Reproduction environment:** static production SPA (`serve -s`) + Playwright-mocked `/api/**` (same mode as manual UI review).

| Scenario | `/management/info` response | Result |
| -------- | --------------------------- | ------ |
| A — UI-review mock (no info mock) | SPA `index.html` 200 `text/html` | pageerror **`Ct`** = minified **`HttpErrorResponse`**: `Http failure during parsing for .../management/info` |
| B — `{}` JSON | `{}` | No error |
| C — ribbon object | non-string ribbon | `TypeError: ...display-ribbon-on-profiles.split is not a function` → **source map:** `profile.service.ts:29` |
| F — valid string ribbon | `display-ribbon-on-profiles: "dev"` | No error |

**Classification:**

- Observed UI-review `Ct` / parse failure: **MOCK-ONLY** (SPA fallback HTML for actuator info). Does not occur when info returns JSON (real backend or proper mock).
- Latent `.split` on non-string ribbon: **FIXED** with `typeof === 'string'` contract check + `catchError` empty profile + unit tests.
- Playwright now mocks `/management/info` with valid JSON.

**Does not block visible login/menu flows** under proper API JSON. **NOT** an unexplained reproducible application error against a real backend contract.

## Decisions

### Code merge decision

**READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS**

Accepted code-scope / external blockers that do not require further application code in this PR:

- Docker runtime not executed here  
- Oracle smoke not executed here  
- Non-root topology unknown (root `<base href="/">` remains)  
- Ops must verify Production CORS origins and complete credential rotation  

### Production deployment decision

**NOT READY FOR PRODUCTION**

Blocked on Docker verification, Oracle smoke, deployment topology confirmation, Production CORS origin verification, and historical credential rotation.
