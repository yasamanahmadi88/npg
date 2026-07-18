# Empty body from `/api/file-report-generation-logs`

## Root cause (confirmed when debugger shows empty page)

```java
fileReportGenerationLogRepository.findAll(specification, page)
    .map(fileReportGenerationLogMapper::toDto);
```

returning an empty `Page` means JPA matched **zero rows**. This is not a mapper/serialization bug.

The most common regression after the upgrade: Spring binds empty query params such as
`reportName.equals=` into a non-null `StringFilter` with `equals=""`. JHipster
`QueryService` then emits `WHERE report_name = ''`, which matches nothing even when
`TBL_FILE_REPORT_GENERATION_LOG` has data.

## Fix shipped on this branch

`FileReportGenerationLogQueryService` now:

1. **Sanitizes** blank string/range filters (`equals=""`, whitespace `contains`, empty ranges)
2. When **no active filter** remains → uses `repository.findAll(page)` (unfiltered), same as pre-filter behavior
3. Only applies **active** filters inside `createSpecification`

Frontend already skips blank values in `createRequestOption` / `buildQuery()`.

Entity table mapping: `tbl_file_report_generation_log` (Oracle folds to `TBL_FILE_REPORT_GENERATION_LOG`).

## How to diagnose in 30 seconds

After pulling the latest tip, call the API (or open the page) and inspect response headers:

| Header | Meaning |
|--------|---------|
| `X-Total-Count` | Rows matching **this request's filters + page** |
| `X-Table-Count` | `COUNT(*)` on the mapped table with **no filters** |

### Case A — `X-Table-Count: 0`

The schema/user the app uses has an empty mapped table. Your SQL client is almost certainly looking at another user/schema/DB.

Run as the **same DB user as the app**:

```sql
SELECT USER FROM dual;
SELECT COUNT(*) FROM TBL_FILE_REPORT_GENERATION_LOG;
SELECT * FROM TBL_FILE_REPORT_GENERATION_LOG FETCH FIRST 5 ROWS ONLY;
```

### Case B — `X-Table-Count > 0` but body `[]`

Filters or page index excluded everything. In the debugger, inspect `criteria` **before**
`findAll(specification, page)`:

- `reportName.equals` / `porNumber.equals` must not be `""`
- `reportDate.*` must not be Invalid date / empty
- `page` must be 0-based

Backend warn: `table has N row(s) but this request matched 0`

With the sanitize fix, blank filters should no longer zero out the list; you should see log:

`FileReportGenerationLog: no active filters — using findAll(pageable)`

### Case C — still empty after sanitize + `X-Table-Count > 0`

Share the request URL query string and the logged `criteria=` object. A real non-blank filter may be excluding all rows.

## Not caused by this method returning null

`page.getContent()` returns an empty list when there are no matches; it does not mean serialization failed. If SQL/table were missing you would normally see **500**, not **200 []**.
