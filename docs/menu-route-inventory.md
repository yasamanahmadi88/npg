# Menu → Route Inventory

**Source:** `navbar.component.html` + Angular route modules  
**Automated coverage:** `menu-routing.spec.ts` (absolute-path resolution)  
**Base href note:** Links use root-absolute paths (`/…`). Angular Router resolves them from the application root; with a non-root `<base href>`, `navigateByUrl('/x')` still targets the app root path `x` (not the filesystem). Do not convert these to relative links — that reintroduces nested-route breakage.

| Menu label (i18n / default) | RouterLink | Configured route | Guard | Required authority / permission | Expected component | Test status |
| --------------------------- | ---------- | ---------------- | ----- | ------------------------------- | ------------------ | ----------- |
| Home | `/` | `''` → home | — | Authenticated (navbar switch) | `HomeComponent` | PASS (unit inventory) |
| Day Of Week Time Frame | `/day-of-week-time-frame` | entity lazy route `''` | `AuthActivateService` | `dayOfWeekTimeFrame` / `view` | list component | PASS (unit inventory) |
| Crm To Crdb Response Map | `/crm-to-crdb-response-map` | entity lazy route | `AuthActivateService` | `crmToCrdbResponseMap` / `view` | list component | PASS (unit inventory) |
| Off Day | `/off-day` | entity lazy route | `AuthActivateService` | `offDay` / `view` | list component | PASS (unit inventory) |
| Time Frame | `/time-frame` | entity lazy route | `AuthActivateService` | `timeFrame` / `view` | list component | PASS (unit inventory) |
| Portability | `/portability` | entity lazy route | `AuthActivateService` | `portability` / `view` | list component | PASS (unit + Playwright nested URL) |
| Setting | `/setting` | entity lazy route | `AuthActivateService` | `setting` / `view` | list component | PASS (unit inventory) |
| Check Status | `/check-status` | entity lazy route | `AuthActivateService` | `checkStatus` / `view` | list component | PASS (unit inventory) |
| Notification Template | `/notification-template` | entity lazy route | `AuthActivateService` | `notificationTemplate` / `view` | list component | PASS (unit inventory) |
| Undifined Status | `/undifined-status` | entity lazy route | `AuthActivateService` | `undifinedStatus` / `view` | list component | PASS (unit inventory) |
| Business Config | `/business-config` | entity lazy route | `AuthActivateService` | `businessConfig` / `view` | list component | PASS (unit inventory) |
| Portability Log | `/portability-log` | entity lazy route | `AuthActivateService` | `portabilityLog` / `view` | list component | PASS (unit inventory) |
| Event Log | `/event-log` | entity lazy route | `AuthActivateService` | `eventLog` / `view` | list component | PASS (unit inventory) |
| File Report Generation Log | `/file-report-generation-log` | entity lazy route | `AuthActivateService` | `fileReportGenerationLog` / `view` | list component | PASS (unit inventory) |
| Resource | `/resource` | entity lazy route | `AuthActivateService` | Menu: `ROLE_ADMIN`; route: permission | list component | PASS (unit inventory) |
| Authority | `/authority` | entity route | `AuthActivateService` | Menu: `ROLE_ADMIN` | list component | PASS (unit inventory) |
| Resource Authority | `/resource-authority` | entity lazy route | `AuthActivateService` | permission directive | list component | PASS (unit inventory) |
| Session management | `/admin/session-management` | `admin` child | `UserRouteAccessService` | `ROLE_ADMIN` | session-management | PASS (unit inventory) |
| User management | `/admin/user-management` | `admin` child | `UserRouteAccessService` | `ROLE_ADMIN` | user-management | PASS (unit inventory) |
| Health | `/admin/health` | `admin` child | `UserRouteAccessService` | `ROLE_ADMIN` | health | PASS (unit inventory) |
| Configuration | `/admin/configuration` | `admin` child | `UserRouteAccessService` | `ROLE_ADMIN` | configuration | PASS (unit inventory) |
| Logs | `/admin/logs` | `admin` child | `UserRouteAccessService` | `ROLE_ADMIN` | logs | PASS (unit inventory) |
| API | `/admin/docs` | `admin` child | `UserRouteAccessService` | `ROLE_ADMIN` | docs | PASS (unit inventory) |
| Settings | `/account/settings` | account child | `UserRouteAccessService` | Authenticated | settings | PASS (unit inventory) |
| Password | `/account/password` | account child | `UserRouteAccessService` | Authenticated | password | PASS (unit inventory) |
| Register | `/account/register` | account child | — (public) | Anonymous | register | PASS (unit inventory) |
| Sign in | `/login` (via `login()`) | `login` | — | Anonymous | login | PASS (unit + Playwright) |
| Sign out | click → `logout()` | n/a | — | Authenticated | clears client auth | PASS (`main.auth-lifecycle.spec.ts`) |
| Password reset request | `/account/reset/request` | account child | — | Anonymous | password-reset-init | PASS (route restore + inventory) |
| Dashboard (post-login) | `/dashboard` | common route | auth | Authenticated | dashboard | PASS (login unit) |

## Absolute vs relative

| Check | Status |
| ----- | ------ |
| All navbar `routerLink` values are root-absolute (`/…`) | PASS |
| Relative nested breakage regression covered | PASS (`menu-routing.spec.ts`) |
| Live click-through of every menu item against real backend | BLOCKED (Oracle / full stack) |
