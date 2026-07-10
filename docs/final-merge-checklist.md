# Final merge checklist — PR #5

**Reviewed SHA:** `d7f2bde5d4eaf05b4221874c9bce9508b1d87649`  
**Expected changed-file count:** **58**  
**PR:** https://github.com/yasamanahmadi88/npg/pull/5  

Human reviewer: check each box only with live evidence. Do not mark operational or approval items complete without proof.

## Remote / CI

* [ ] PR remote SHA matches the reviewed SHA (`d7f2bde5d4eaf05b4221874c9bce9508b1d87649`)
* [ ] GitHub changed-file count matches the handoff (**58**)
* [ ] CI frontend is green
* [ ] CI backend is green
* [ ] CI security scan is green
* [ ] No merge conflict

## Security / configuration

* [ ] `SecurityConfiguration` reviewed
* [ ] JWT filter registration reviewed (`JWTConfigurer` deleted; filter registered directly)
* [ ] CORS configuration reviewed (including prod override; no `*` + credentials)
* [ ] Production configuration reviewed (`application-prod.yml` secrets via env)
* [ ] `.env.example` contains placeholders only
* [ ] No new secret introduced

## Functional / UX

* [ ] Routing changes reviewed
* [ ] Login/logout behavior reviewed
* [ ] Light theme reviewed
* [ ] Dark theme reviewed
* [ ] RTL reviewed
* [ ] Mobile layout reviewed

## Accepted residuals / blockers acknowledged

* [ ] Residual npm findings accepted (7 High tooling-only)
* [ ] Non-root deployment decision acknowledged (BLOCKED / topology unknown)
* [ ] Docker blocker acknowledged
* [ ] Oracle blocker acknowledged
* [ ] Historical credential rotation owner assigned
* [ ] Human reviewer approval received

## Merge decision after checklist

Only after the required-before-merge items above are complete:

* Code merge recommendation remains: **READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS**
* Production remains: **NOT READY FOR PRODUCTION** (see `docs/production-readiness-gate.md`)
