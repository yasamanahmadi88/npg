# Manual UI Review — PR #5

**Decision support for:** READY TO MERGE WITH ACCEPTED BLOCKERS  
**Commit SHA:** `03a753ff3b14771960cc50e9ac6d51dc3d45e99a`  
**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Date:** 2026-07-10

## Environment

| Item | Value |
| ---- | ----- |
| Browser | Chromium (Playwright headless) + screenshot inspection |
| Viewports | 390×844, 768×1024, 1440×900, 1920×1080 |
| Backend mode | **Static production SPA** (`serve -s`) + **mocked `/api/**`** |
| Database mode | **None** (API mocked; empty lists `[]`) — **NOT live Oracle** |
| Languages | English (LTR), Persian `fa` (RTL) |
| Themes | Light, Dark |
| Artifacts | `/tmp/ui-review-artifacts/` (screenshots not committed) |

Dev profile requires Oracle (`172.18.50.50`); Docker unavailable → mock/static mode is the safest available validation path.

## Pages reviewed

| Page | Light | Dark | RTL | Mobile | Result | Notes |
| ---- | ----- | ---- | --- | ------ | ------ | ----- |
| Login | Completed | Completed | Completed* | — | Completed | Form usable; invalid login shows danger alert; captcha mocked |
| Dashboard / Home | Completed | Completed | Completed | Completed | Completed | Charts empty under mock (expected) |
| NPG Menu dropdown | Completed | — | Completed | Completed | Completed | Absolute links; all critical items navigated |
| Portability list | Completed | — | — | — | Completed | Empty state under mock |
| Setting / Off Day / Time Frame / DOW TF | Completed | — | — | — | Completed | Route + `jhi-main` visible |
| Not Found | Completed | — | — | — | Completed | Unknown URL handled |
| Post-logout / settings | Completed | — | — | — | Completed | Logout clears client session path |

\*RTL login/header also observed when `fa` locale active; dedicated RTL dashboard shots: `15b-rtl-forced.png`, `16b-rtl-dark.png`.

## Navigation reviewed

| Menu | Expected route | Actual route | Component visible | Result |
| ---- | -------------- | ------------ | ----------------- | ------ |
| Portability | `/portability` | `/portability` | Yes | Completed |
| Setting | `/setting` | `/setting` | Yes | Completed |
| Off Day | `/off-day` | `/off-day` | Yes | Completed |
| Time Frame | `/time-frame` | `/time-frame` | Yes | Completed |
| Day Of Week Time Frame | `/day-of-week-time-frame` | `/day-of-week-time-frame` | Yes | Completed |
| Browser Back/Forward | — | restored `/portability` | Yes | Completed |
| Direct nested URL + refresh | `/portability` | `/portability` | Yes | Completed |

## Authentication

| Check | Result |
| ----- | ------ |
| Login page renders | Completed |
| Login form usable | Completed |
| Invalid login safe error | Completed |
| Authenticated shell | Completed |
| Refresh does not force logout | Completed |
| Explicit logout | Completed |
| Protected route after logout | Completed (no crash; guard/redirect path) |
| Redirect loop | Not observed |

## Theme

| Check | Result | Notes |
| ----- | ------ | ----- |
| Toggle light↔dark | Completed | |
| Persist after refresh | Completed | `localStorage npg-portal-theme`; brief attribute race noted once |
| FOUC / `theme-init.js` | Completed | External script; CSP unchanged vs main |
| Dark readability (dashboard) | Completed | Text/borders/inputs readable in screenshots |
| Toggle accessible | Completed | `aria-label` / `aria-pressed` |

## RTL (Persian)

| Check | Result |
| ----- | ------ |
| `dir=rtl` / `lang=fa` | Completed (with `jhi-locale=fa`) |
| Navbar alignment | Completed |
| Labels / search / menus | Completed |
| Theme toggle in RTL | Completed |
| Light + Dark in RTL | Completed |

## Responsive

| Viewport | Result | Notes |
| -------- | ------ | ----- |
| Mobile 390×844 | Completed | Hamburger works; **Search button oversized / layout tight** (pre-existing polish issue) |
| Tablet 768×1024 | Completed | |
| Desktop 1440 / 1920 | Completed | |

## Browser diagnostics

| Issue | Severity | Page | Evidence | Status |
| ----- | -------- | ---- | -------- | ------ |
| Minified `pageerror` `"Ct"` / `TypeError: ct.split is not a function` | Medium | Login/bootstrap | Chromium pageerror | Application defect — non-blocking for reviewed flows; follow-up to unminify/source-map |
| Theme attribute briefly mismatched storage on one reload | Low | Dashboard | results.json | Non-blocking |
| 401 on unauthenticated `/api/account` | Low | Login | Network | Expected |
| Empty dashboard charts | Low | Dashboard | Screenshots | Expected blocked integration (mocked API) |

No CORS errors, chunk-load failures, or CSP violations observed under mock static hosting.

## Non-root routing: **BLOCKED**

Evidence:

- `<base href="/" />` in `index.html`
- `server.servlet.context-path` referenced in `application.yml` API-doc patterns → subpath **possible**
- No deploy manifest proving root-only forever

**Required verification if `/npg/` (or similar) is used:**

1. Build with matching non-root `base href` / deploy URL  
2. Serve under the subpath  
3. Test direct navigation, refresh, assets, API prefix, absolute menu links  

## Production variables checklist

| Variable | Required | Current default | Production action |
| -------- | -------- | --------------- | ----------------- |
| `SPRING_DATASOURCE_URL` | Yes (prod) | `jdbc:oracle:thin:@//172.19.49.81:1521/NPGDB` | **Override** to real non-shared host; do not rely on default IP |
| `SPRING_DATASOURCE_USERNAME` | Yes (prod) | none | Set via secret store |
| `SPRING_DATASOURCE_PASSWORD` | Yes (prod) | none | Set via secret store; rotate if historically exposed |
| `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` | Yes (prod) | none | Set ≥256-bit base64 secret; rotate if historically exposed |
| `jhipster.cors.allowed-origins` | Yes for browser clients | Hardcoded list incl. `npg.mci.ir` / `tnpg.mci.ir` / lab IPs | Confirm front-door origins; override via env/config |
| `SPRING_PROFILES_ACTIVE` | Yes | — | `prod` (+ any company profiles) |
| TLS / reverse proxy | Infra | Assumed at proxy | Terminate TLS; forward headers as required |
| Context path | Optional | empty / `/` | If set, complete non-root Angular verification |
| Cookie / CSRF | N/A (Bearer JWT) | CSRF disabled by design | Keep Bearer-only clients |

No weak JWT/password literals remain in prod YAML in this PR.

## Historical secret remediation (ops — do not rewrite git here)

| Credential type | May have existed historically | Still valid? | Action | Owner | Deadline | Verify |
| --------------- | ----------------------------- | ------------ | ------ | ----- | -------- | ------ |
| Prod DB password (`pass#1400` class) | Yes (removed from tree in this PR) | **Assume yes until rotated** | Rotate Oracle/app DB password everywhere | Ops / DBA | Before/at prod deploy of this PR | Login + app health against new secret |
| JWT base64 secret | Yes (removed from prod YAML) | **Assume yes until rotated** | Issue new secret; invalidate old tokens | Ops / Security | Before/at prod deploy | New logins succeed; old tokens fail |
| Internal JDBC host exposure | Yes (still default URL host) | N/A | Treat as inventory; restrict network | Ops | Ongoing | Firewall / private DNS |
| CI tokens / OAuth / private keys | Not found in this PR diff | — | Continue secret scanning | Security | Ongoing | Scanner clean |

## Docker / Oracle blockers

| Blocker | Status | Exact reason | Reviewer command / procedure |
| ------- | ------ | ------------ | ---------------------------- |
| Docker runtime | BLOCKED | `docker: command not found` in agent | On Docker host: `docker compose -f src/main/docker/app.yml config` then build/run per README |
| Compose syntax | Static OK | `app.yml` parsed as YAML | Same as above |
| Live Oracle | BLOCKED | Dev/prod JDBC targets Oracle; no disposable Oracle here | Provision non-prod Oracle; set datasource env; run `mvnw -Pprod verify` / smoke against that instance — **never production** |

## Remaining blockers (acceptance required)

1. Docker runtime verification  
2. Live Oracle verification  
3. Non-root routing verification (if applicable)  
4. Operational rotation of historical DB password + JWT secret  
5. Follow-up on minified `ct.split` pageerror (Medium, non-blocking for merge if accepted)

## Recommendation

**READY TO MERGE WITH ACCEPTED BLOCKERS**

Manual Light/Dark, critical menus, login/logout/refresh, and RTL (with `fa` locale) passed under mock/static mode. Mobile has pre-existing layout polish issues. Do not treat this as Oracle/Docker proof.
