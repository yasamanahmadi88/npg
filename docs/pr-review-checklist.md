# PR #5 review checklist

**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Branch:** `cursor/full-upgrade-audit-eec2`  
**Base:** `main`

## Live confirmation (re-check before merge)

| Check | Expected |
| ----- | -------- |
| `git fetch origin && git rev-parse HEAD` | Matches PR head |
| `git diff --name-status origin/main...HEAD` | Minimal semantic + docs |
| GitHub CI | frontend / backend / security-scan SUCCESS |
| Mergeable | MERGEABLE / CLEAN |
| Review | Human approval required |

## Functional / quality

| Item | Status |
| ---- | ------ |
| Jest | PASS 145/609 |
| Playwright | PASS 9/9 |
| Maven verify | PASS 703 |
| Console / `Ct` | MOCK-ONLY explained; latent FIXED |
| Mobile Search | FIXED |
| Light/Dark | PASS (manual) |
| Persian RTL | PASS with notes |
| Docker | BLOCKED — runbook |
| Oracle | BLOCKED — runbook |
| Non-root | BLOCKED — topology unknown |
| Prod CORS | BLOCKED — Ops verify checklist |
| Secrets fail-fast | PASS (no password/JWT defaults) |
| Credential rotation | BLOCKED — plan doc |

## Merge vs Production

| Decision | Value |
| -------- | ----- |
| Code merge | READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS |
| Production | NOT READY FOR PRODUCTION |

Do **not** merge automatically. Do **not** force-push.
