# Manual UI review — PR #5

**Mode:** Static production SPA + mocked `/api/**` (NOT live Oracle)  
**Browser:** Chromium  
**Updated:** 2026-07-10 (blocker-resolution pass)

## Environment

| Item | Value |
| ---- | ----- |
| Serve | `npx serve -s target/classes/static -l 4173` |
| API | Playwright `page.route('**/api/**')` |
| Info | Must mock `**/management/info` as JSON (see below) |

## Results (prior Chromium pass + blocker follow-up)

| Area | Status | Notes |
| ---- | ------ | ----- |
| Login / invalid login | Completed | |
| Authenticated shell / refresh | Completed | |
| Critical menus / nested URL / refresh | Completed | |
| Back / Forward | Completed | |
| Light / Dark | Completed | |
| Persian RTL | Completed (reviewer acceptance for forced RTL) | |
| Mobile 320–430 | Search button **FIXED** (dashboard CSS) | Playwright coverage added |
| `Ct` / `ct.split` | **MOCK-ONLY** + latent **FIXED** | See investigation |

## `Ct` / `ct.split` investigation

| Field | Value |
| ----- | ----- |
| Exact page | `/login` and authenticated `/` bootstrap |
| Exact user action | Initial load (navbar calls `ProfileService.getProfileInfo()`) |
| Browser | Chromium headless |
| Theme | light (also seen under dark) |
| Language | en (fa also loads i18n) |
| Viewport | 1440×900 (also mobile) |
| Network | `GET /management/info` → **200 `text/html`** (SPA `index.html`) when unmocked |
| Full error | Minified pageerror **`Ct`** = Angular **`HttpErrorResponse`**: `Http failure during parsing for http://127.0.0.1:4173/management/info` |
| Source-mapped related defect | Non-string `display-ribbon-on-profiles` → `profile.service.ts` `.split` (**FIXED**) |
| Consistent? | Yes whenever info returns HTML or non-string ribbon |
| Blocks visible functionality? | No for reviewed flows when APIs return JSON; ribbon/profile flags degrade safely after fix |

**Classification: MOCK-ONLY** for the UI-review observation; repository latent `.split` contract bug **FIXED**.

## Mobile Search

Issue was the portability **dashboard** Search button (`#jh-search-entity`) overflowing at mobile widths (col-5+col-5+col-2 row), not a separate navbar control.

**Fix:** responsive wrap in `portability-dashboard.component.scss`.  
**Test:** Playwright viewports 320 / 375 / 390 / 430.

## Non-root

`<base href="/" />` — **BLOCKED** (deployment topology unknown; no owner-approved root-only sign-off).
