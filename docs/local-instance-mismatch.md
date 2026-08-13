# Local instance mismatch — why routing/security still broken

**Date:** 2026-07-11  
**Audience:** Developer running `npg-portal-instance-security` locally

## Verdict

The daily/local folder you inspected is **not** running PR #5 (`cursor/full-upgrade-audit-eec2`).  
It is still the **old broken tree**. That is why menu routing and JWT security still fail.

PR #5 **already contains** the fixes. They are not present in the files you pasted from:

`E:\idea-projects\npg\npg-portal-instance-security`

## Proof from your paste vs PR #5 tip

| Check | Your local instance | PR #5 (`d51dbd3` / application tip `d7f2bde`) |
| ----- | ------------------- | --------------------------------------------- |
| Most entity `routerLink` | Relative: `routerLink="portability"` | Absolute: `routerLink="/portability"` |
| File Report link | Absolute `/file-report-generation-log` **only** | Absolute for **all** menu items |
| File Report permission | **No** `*jhiHasPermission` → always visible | Has `*jhiHasPermission="['fileReportGenerationLog', 'view']"` |
| Search navigate | `./portability` (relative) | `/portability` (absolute) |
| Theme | Missing | `ThemeService` present |
| Security | Still imports/uses `JWTConfigurer` | `JWTConfigurer` **deleted**; `addFilterBefore(new JWTFilter(...))` |
| `JWTConfigurer.java` | Present | Absent (deleted in PR) |

### Why only File Report Generation Logs appears / works

1. **Visibility:** In your local HTML, File Report is the only entity item **without** `*jhiHasPermission`. Every other item is gated on `resourceAuthorities` from `/api/account`. If those permissions are missing/empty, the other items are hidden; File Report still shows.
2. **Navigation:** File Report is the only item with an **absolute** `routerLink="/…"`. Relative links like `routerLink="portability"` nest under the current URL and break nested navigation after Angular upgrade.

### Why security “is not fixed”

Your local `SecurityConfiguration` still uses:

```java
import ix.portal.npg.security.jwt.JWTConfigurer;
// ...
securityConfigurerAdapter() { return new JWTConfigurer(...); }
```

PR #5 removed that adapter (incompatible with the Spring Security 6 / Boot 4 style used in this upgrade) and registers `JWTFilter` directly on the filter chain.

## What to run locally (Windows)

From a clone of `yasamanahmadi88/npg` (or remotes that track that GitHub PR):

```powershell
cd E:\idea-projects\npg   # or your real clone of the GitHub repo — NOT a random copy
git fetch origin
git checkout cursor/full-upgrade-audit-eec2
git pull origin cursor/full-upgrade-audit-eec2
git rev-parse HEAD
# Expect a tip that is d51dbd3 or a later docs-only commit on the same branch
```

### Confirm fixes are on disk before starting the app

```powershell
Select-String -Path src\main\webapp\app\layouts\navbar\navbar.component.html -Pattern 'routerLink="/portability"'
Select-String -Path src\main\java\ix\portal\npg\config\SecurityConfiguration.java -Pattern 'addFilterBefore\(new JWTFilter'
Test-Path src\main\java\ix\portal\npg\security\jwt\JWTConfigurer.java
# Expect: match on absolute portability link; match on JWTFilter; Test-Path = False
```

Then clean rebuild:

```powershell
npm ci
npm run webapp:prod
# or your usual IDEA Spring Boot run after frontend build
.\mvnw -P-webapp clean verify
```

If `npg-portal-instance-security` is a **separate copy** (not the git clone of this PR), either:

* switch that copy to the PR branch, or
* replace it with a fresh checkout of `cursor/full-upgrade-audit-eec2`, or
* merge/cherry-pick PR #5 into that instance **after** review.

Running an old tree and expecting PR #5 behavior will always fail.

## Empty backend data

After you are on the correct branch:

1. Confirm login succeeds and `/api/account` returns `resourceAuthorities` for the menus you need.
2. Confirm list APIs (e.g. `/api/portabilities`) return 200 with data (not 401/403/empty because of auth).
3. Empty `[]` with 200 can still mean a real empty DB / filters — that is data, not the relative-route bug.

## Do not confuse folders

| Location | Role |
| -------- | ---- |
| GitHub PR #5 `cursor/full-upgrade-audit-eec2` | Fixed routing + security |
| `npg-portal-instance-security` (your paste) | Old relative links + JWTConfigurer |

## Code merge / Production decisions (unchanged)

* Code merge: READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS
* Production: NOT READY FOR PRODUCTION (Docker/Oracle/CORS/credentials gates)
