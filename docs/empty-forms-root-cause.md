# Empty forms — deep root-cause analysis (2026-07-12)

## Confirmed root causes

### 1) Rate limit emptied every list (primary)

Historical defaults:

```yaml
bucket4j.get.token-per-minute: 6
bucket4j.get.bucket-size: 15
```

Each SPA navigation costs multiple GETs (`/api/account` + entity list + …). After a few clicks, `JWTFilter` returns **HTTP 429** `error.too.many.requests`. List components treat that as failure → empty tables — while the DB still has rows.

**Fix:** raise defaults (dev 300/min, base/prod 120/min), enforce a **minimum floor** in `SecurityCache` even if `SETTING` table still stores `6`, and **exempt** `GET /api/account` from the GET bucket.

### 2) List UI state hid data / looked empty

- `settings` started `undefined` → neither table nor “not found”
- Event Log / File Report set data but left `isDataLoaded=false` on initial load → table stayed `[hidden]`
- Portability set `dataSource = undefined` during search → broken table shell
- Card-level `*jhiHasPermission` could hide whole pages; route guards already protect access

### 3) Broken appearance

- `mat-select class="form-control"` fights Angular Material MDC
- Forced `min-height: 450px` on search forms
- Theme token / indigo-pink / Bootswatch stacking

**Fix:** `npg-mat-select` + `.entity-search-form` global rules; remove 450px min-height; default light theme (earlier).

## What you must do after pull

1. `git fetch && git reset --hard origin/cursor/full-upgrade-audit-eec2`
2. Restart **backend** (rate-limit + JWT changes are server-side)
3. Rebuild frontend / hard refresh
4. **Log out and log in** (creates a new bucket with the new limits)
5. Network tab on `/setting`:
   - `GET /api/settings?...` must be **200** with a JSON array
   - If still **429** → old JVM still running, or SETTING keys ignored incorrectly (should no longer happen with floor)
   - If **200 []** while SQL shows rows → app JDBC URL points at a different schema than the one you queried
