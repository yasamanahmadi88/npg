# Empty body from `/api/file-report-generation-logs`

## What the endpoint does

```java
Page<FileReportGenerationLogDTO> page =
    fileReportGenerationLogQueryService.findByCriteria(criteria, pageable);
return ResponseEntity.ok().headers(headers).body(page.getContent());
```

A **200 with `[]`** means JPA ran successfully and matched **zero rows**. This is not a JWT/UI binding bug.

Entity mapping:

- Table: `TBL_FILE_REPORT_GENERATION_LOG` (was `tbl_file_report_generation_log`; Oracle folds both to the same unquoted name)
- Dev JDBC (`application-dev.yml`): `jdbc:oracle:thin:@//172.18.50.50:1521/NPGPDB` user `npg`

## How to diagnose in 30 seconds

After pulling the latest tip, call the API (or open the page) and inspect response headers:

| Header | Meaning |
|--------|---------|
| `X-Total-Count` | Rows matching **this request's filters + page** |
| `X-Table-Count` | `COUNT(*)` on the mapped table with **no filters** |

### Case A — `X-Table-Count: 0`

The schema/user the app uses has an empty mapped table. Your SQL client is almost certainly looking at:

- another user/schema, or
- another table name, or
- another database (`NPGPDB` vs `NPGDB` vs UAT)

Run as the **same DB user as the app**:

```sql
SELECT USER FROM dual;
SELECT COUNT(*) FROM TBL_FILE_REPORT_GENERATION_LOG;
SELECT * FROM TBL_FILE_REPORT_GENERATION_LOG FETCH FIRST 5 ROWS ONLY;
```

Also confirm app log line:

`FileReportGenerationLog mapped table is empty for the connected datasource...`

### Case B — `X-Table-Count > 0` but body `[]`

Filters or page index excluded everything. Check:

- request query string (`reportName.equals`, `reportDate.*`, `porNumber.equals`)
- `page` (must be 0-based; page 5 of a 1-page result is empty)
- backend warn: `table has N row(s) but this request matched 0`

### Case C — backend log shows criteria with unexpected filters

Empty `mat-option value=""` / invalid date values used to be able to leak into query params. Latest tip hardens `createRequestOption` + `buildQuery()` to ignore blanks/invalid dates.

## Not caused by this method returning null

`page.getContent()` returns an empty list when there are no matches; it does not mean serialization failed. If SQL/table were missing you would normally see **500**, not **200 []**.
