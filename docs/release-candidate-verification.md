# Release candidate verification — PR #5

**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Branch:** `cursor/full-upgrade-audit-eec2`  
**Prior tip verified:** `f5d2ad0` (CI green, 50 files)  
**Blocker-pass adds:** profile service fix, dashboard CSS, prod CORS, runbooks, Playwright coverage  

## Commands (re-run after code changes)

```bash
export PATH="$HOME/node/node-v22.22.2-linux-x64/bin:$PATH"
npm run lint:fix   # or project lint script
npm test -- --coverage=false
npx playwright test
npm run webapp:prod
sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf
JAVA_HOME=$HOME/jdk/jdk-25.0.3+9 /tmp/mvnw.lf -P-webapp clean verify
```

Record exact counts in `docs/test-report.md`.

## Environment blockers (not executed here)

| Item | Doc |
| ---- | --- |
| Docker | `docs/docker-verification-runbook.md` |
| Oracle | `docs/oracle-smoke-test-runbook.md` |
| CORS Prod origins | `docs/production-cors-checklist.md` |
| Credential rotation | `docs/credential-rotation-plan.md` |

## Non-root / context-path

| Evidence | Value |
| -------- | ----- |
| `index.html` | `<base href="/" />` |
| `server.servlet.context-path` | Optional in API-doc patterns only |
| Approved topology | **Unknown** — no owner sign-off for root-only or `/npg/` |

**Classification: BLOCKED — deployment topology unknown.**  
Do not change production routing architecture until Ops/App owner decides.

## Dual decision

| Gate | Decision |
| ---- | -------- |
| Code merge | READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS |
| Production | NOT READY FOR PRODUCTION |
