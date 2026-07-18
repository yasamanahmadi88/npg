# Why File Report is still empty after local edits

## Most common reason

You edited only frontend files under:

`E:\idea-projects\npg\npg-portal-instance-security\...`

That folder has historically been an **old copy**, not PR branch tip. Changing only
`request-util.ts` is **not enough**.

You must also replace and restart with these **Java** files:

1. `src/main/java/ix/portal/npg/service/FileReportGenerationLogQueryService.java`
2. `src/main/java/ix/portal/npg/web/rest/FileReportGenerationLogResource.java`
3. `src/main/java/ix/portal/npg/domain/FileReportGenerationLogEntity.java` (`@Table(name = "TBL_FILE_REPORT_GENERATION_LOG")`)

Then:

```powershell
# Prefer the PR branch, not a random copy
git fetch origin
git checkout cursor/full-upgrade-audit-eec2
git pull origin cursor/full-upgrade-audit-eec2

# Confirm QueryService has sanitize
Select-String -Path src\main\java\ix\portal\npg\service\FileReportGenerationLogQueryService.java -Pattern 'sanitizeCriteria'

# Rebuild + restart backend (mandatory)
.\mvnw -P-webapp clean compile
# restart Spring Boot from IDEA
```

Frontend-only refresh will not load new Java.

## Network tab checklist (1 minute)

Open DevTools → Network → reload File Report page → click
`/api/file-report-generation-logs?...`

| What you see | Meaning | Action |
|---|---|---|
| Status **429** | Rate limit | Log out/in; pull rate-limit fix; restart backend |
| Status **401/403** | Auth/permission | Login + `fileReportGenerationLog` authority |
| Status **200**, body `[]`, header **`X-Table-Count: 0`** | App DB user/schema has no rows | Query as app user `npg`: `SELECT COUNT(*) FROM TBL_FILE_REPORT_GENERATION_LOG` |
| Status **200**, body `[]`, header **`X-Table-Count: >0`** | Filters still excluding rows | Copy full request URL; blank `reportName.equals=` must be ignored by updated QueryService |
| Status **200**, body has JSON array | Backend OK | If UI still empty → frontend cache; hard refresh |

## Two request-util files

- Edit: `app/core/request/request-util.ts` (real implementation)
- Keep as re-export only: `app/shared/util/request-util.ts`

```ts
export { createRequestOption } from 'app/core/request/request-util';
```

## SQL to run as the **same** JDBC user as the app

```sql
SELECT USER FROM dual;
SELECT COUNT(*) FROM TBL_FILE_REPORT_GENERATION_LOG;
```

If SQL client shows data under another schema but app user count is 0, the UI will stay empty.
