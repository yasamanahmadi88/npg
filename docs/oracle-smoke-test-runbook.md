# Non-production Oracle smoke-test runbook

**Status:** BLOCKED — no safe non-production Oracle endpoint available in the verification agent.  
**Owner:** DBA / Ops  
**PR:** #5  
**Do not connect to production Oracle.**

## Repository-derived requirements

| Item | Value / evidence |
| ---- | ---------------- |
| JDBC driver | `com.oracle.database.jdbc:ojdbc8` (`pom.xml`) |
| JDBC URL format | `jdbc:oracle:thin:@//HOST:PORT/SERVICE` (prod example service `NPGDB`) |
| Prod URL placeholder | `${SPRING_DATASOURCE_URL:jdbc:oracle:thin:@//172.19.49.81:1521/NPGDB}` |
| Username / password | `${SPRING_DATASOURCE_USERNAME}` / `${SPRING_DATASOURCE_PASSWORD}` — **no defaults** |
| Liquibase | `spring.liquibase.contexts: prod` in `application-prod.yml`; changelogs under `src/main/resources/config/liquibase/` |
| Oracle-specific Liquibase props | `now=sysdate`, Oracle types in `master.xml` |
| Timezone | Hibernate JDBC timezone often UTC in test profiles; confirm with DBA for prod |
| Character set | App stores Persian/Unicode text — DB must support Unicode (AL32UTF8 recommended) |
| Sequences / IDs | JHipster/Liquibase entity changelogs — validate sequences exist after migrate |
| Identifier length | Oracle 30-byte legacy limits may apply depending on DB version — run migrate and watch Liquibase errors |
| Tablespaces | Not hard-coded in app config — follow DBA standards |
| Privileges | CONNECT + resource for app schema; DDL for Liquibase migrate user (or pre-migrated schema) |

## What already passed (not Oracle)

| Suite | Result | Notes |
| ----- | ------ | ----- |
| H2 unit/IT (`./mvnw -P-webapp clean verify`) | PASS (703 tests at last full run) | In-memory H2 |
| Repository/IT tests | PASS under H2 | **Oracle compatibility not proven** |

## Required environment variables (non-prod)

```bash
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL='jdbc:oracle:thin:@//NONPROD_HOST:1521/NONPROD_SERVICE'
export SPRING_DATASOURCE_USERNAME='...'
export SPRING_DATASOURCE_PASSWORD='...'
export JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET="$(openssl rand -base64 64)"
export JHIPSTER_CORS_ALLOWED_ORIGINS='https://your-nonprod-frontend.example'
```

## Startup and migration

```bash
sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf
/tmp/mvnw.lf -Pprod,webapp spring-boot:run
# Or run the packaged jar with the same env vars
```

Confirm Liquibase changelog lock clears and all changesets run.

## Smoke checklist (execute in order)

1. Application startup without fatal errors  
2. Liquibase migration validation (no pending failures)  
3. Login-related DB access (`/api/authenticate`, `/api/account`)  
4. Representative list query (e.g. `/api/portabilities`)  
5. Representative create transaction  
6. Representative update transaction  
7. Pagination and sorting (`page`, `size`, `sort`)  
8. Date/time handling (create/read timestamps)  
9. Persian/Unicode persistence (round-trip a Persian string)  
10. Rollback behavior (failed validation does not commit partial rows)  
11. Connection-pool behavior (Hikari pool under modest concurrency)  
12. Clean shutdown (`server.shutdown: graceful`)

## Evidence to capture

- Redacted startup log (Liquibase summary)  
- Health endpoint JSON  
- SQL proof of Unicode row (screenshot or redacted query output)  
- PASS/FAIL per checklist item  

## Explicit status statements

- **H2 tests passed** — yes (CI / local verify).  
- **Repository tests passed** — yes under H2.  
- **Oracle not executed** — yes (this environment).  
- **Oracle compatibility not yet proven** — yes; Production remains blocked on this item.
