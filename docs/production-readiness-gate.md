# Production readiness gate — PR #5 / NPG Portal

**Current Production status:**

```text
NOT READY FOR PRODUCTION
```

The application must remain **NOT READY FOR PRODUCTION** until every mandatory gate below is completed with recorded evidence. Code merge may proceed earlier with accepted code-scope blockers; Production must not.

**Reviewed code SHA:** `d7f2bde5d4eaf05b4221874c9bce9508b1d87649`  
**PR:** https://github.com/yasamanahmadi88/npg/pull/5  

---

## Docker gate

**Required owner:** Ops  
**Required before Production:** Yes  
**Required before merge:** No (if formally accepted as code-scope residual)

### Required evidence

* [ ] `docker compose config` succeeds
* [ ] Images build successfully
* [ ] Containers start
* [ ] Health checks pass
* [ ] Frontend reaches backend
* [ ] SPA refresh works
* [ ] No secret is baked into images
* [ ] Containers use safe privileges
* [ ] Shutdown and cleanup succeed

**Reference:** `docs/docker-verification-runbook.md`  
**Agent status:** BLOCKED (`docker` unavailable in verification environment)

---

## Oracle gate

**Required owner:** DBA/Ops  
**Required before Production:** Yes  
**Required before merge:** No (if formally accepted)

### Required evidence

* [ ] Safe **non-production** Oracle is used (never production)
* [ ] Application starts
* [ ] Liquibase migrations succeed
* [ ] Representative read succeeds
* [ ] Representative create/update succeeds
* [ ] Pagination and sorting succeed
* [ ] Date/time behavior succeeds
* [ ] Persian/Unicode persistence succeeds
* [ ] Transactions and rollback succeed
* [ ] Connection pool is healthy

**Reference:** `docs/oracle-smoke-test-runbook.md`  
**Agent status:** BLOCKED (no safe non-prod Oracle available)  
**Note:** H2 Maven verify (703) does **not** prove Oracle compatibility.

---

## Deployment-path gate

**Required owner:** Deployment/Architecture  
**Required before Production:** Yes  
**Required before merge:** No (if formally accepted)

### Choose and document exactly one

#### Option A — Root-only deployment `/`

* [ ] Written approval recorded (owner + date)
* [ ] Non-root marked **NOT APPLICABLE**
* [ ] Reverse proxy serves the portal at `/`
* [ ] Confirmed `<base href="/">` matches deployment

#### Option B — Subpath deployment (e.g. `/npg/`)

* [ ] Build with correct base href (e.g. `ng build --configuration production --base-href /npg/`)
* [ ] Serve under the real subpath
* [ ] Verify assets, fonts, icons
* [ ] Verify login
* [ ] Verify menus
* [ ] Verify nested routes
* [ ] Verify browser refresh / back / forward
* [ ] Verify API paths
* [ ] Verify theme initialization
* [ ] Verify Not Found behavior

**Repository evidence today:** `<base href="/">` only; optional `server.servlet.context-path` referenced in API-doc patterns.  
**Current classification:** **BLOCKED — deployment topology unknown**

---

## CORS gate

**Required owner:** Ops/Security  
**Required before Production:** Yes  
**Required before merge:** No (if formally accepted)

### Required evidence

* [ ] Exact Production frontend origin known
* [ ] HTTPS origin configured
* [ ] No wildcard with credentials
* [ ] No accidental localhost origin in Production
* [ ] Preflight succeeds for allowed origin
* [ ] Allowed origin succeeds
* [ ] Disallowed origin fails

**Reference:** `docs/production-cors-checklist.md`  
**Repo default (prod profile):** `JHIPSTER_CORS_ALLOWED_ORIGINS` defaulting to `https://npg.mci.ir,https://tnpg.mci.ir` — **Ops must confirm** exact Production origin(s).

---

## Credential-rotation gate

**Required owner:** Security/Ops/DBA  
**Required before Production:** Yes  
**Required before merge:** No only if policy explicitly allows merge with rotation pending; **never** deploy to Production with known-valid exposed credentials

### Required evidence

* [ ] Historical DB password rotated
* [ ] Historical JWT secret rotated
* [ ] Historical API or OAuth secrets reviewed
* [ ] Old credentials revoked
* [ ] New secrets stored in approved secret storage
* [ ] Services restarted safely
* [ ] Old credentials confirmed invalid
* [ ] New credentials confirmed functional

**Reference:** `docs/credential-rotation-plan.md`  
**Rule:** Assume exposed historical credentials are compromised until proven otherwise. Record completion **outside** the repository without storing secret values.

---

## Gate completion rule

| Condition | Production status |
| --------- | ----------------- |
| Any mandatory gate incomplete | **NOT READY FOR PRODUCTION** |
| All mandatory gates complete with evidence | Re-evaluate for Production readiness |

Until all mandatory gates are complete, Production status remains:

```text
NOT READY FOR PRODUCTION
```
