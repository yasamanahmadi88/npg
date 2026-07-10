# Root Cause Analysis

## Confirmed root causes

| ID | Layer | Root cause | Evidence | Fix |
| -- | ----- | ---------- | -------- | --- |
| R1 | Angular Router | Relative `routerLink` resolved against current URL | `navbar.component.html` used `routerLink="portability"` etc. | Absolute paths |
| R2 | Login | Relative navigate from `/login` | `login.component.ts` `./dashboard` | `/dashboard` |
| R3 | Account routes | Mis-migrated routes to dashboard | `register.route.ts`, `password-reset-init.route.ts` | Correct components/paths |
| R4 | Navbar search | Relative navigate | `navbar.component.ts` | Absolute |
| R5 | Main shell | Logout on every unload/refresh | `@HostListener('window:beforeunload')` calling logout | Removed |
| R6 | CORS | Dual CORS config with credentials conflict | `WebConfigurer` CorsFilter + MVC mapping with `allowedOriginPatterns("*")` | Single allow-list CorsFilter |
| R7 | Secrets | Hardcoded JWT + Oracle password in prod YAML | `application-prod.yml` | `${ENV}` placeholders |
| R8 | Jakarta | Residual `javax.annotation` | `ExceptionTranslator` | Spring lang annotations |
| R9 | Security | Deprecated JWT configurer adapter | `JWTConfigurer` + `http.apply` | Direct filter bean wiring |
| R10 | UI theme | No runtime theme system | SCSS tokens unused; no toggle | ThemeService + tokens + navbar toggle |
| R11 | SCSS | Cosmo + Lumen double import | `global.scss` | Cosmo only |
| R12 | Git / PR scope | Mass CRLF→LF rewrite inflated PR to ~499 files | Equal add/delete on 464 paths; 460 byte-identical after `\r` strip | Reverted EOL-only; retained 49 files (incl. human-review package docs) |

## Auth unload logout (R5 detail)

| Question | Answer |
| -------- | ------ |
| Why did unload logout exist? | Legacy attempt to clear client session when the tab closed |
| Why remove it? | `beforeunload` also fires on refresh/navigation, destroying valid sessions |
| Explicit logout? | Still via navbar → `LoginService.logout()` / token + cache clear |
| Security regression? | No — refresh must keep JWT; expiry still enforced by `JWTFilter` + `TokenProvider`; server `SecurityCache` invalidates on logout |
