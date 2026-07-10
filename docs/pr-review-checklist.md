# PR #5 — Manual Review Checklist

**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Branch:** `cursor/full-upgrade-audit-eec2`  
**Base:** `main`  
**Tip SHA:** confirm on PR / `git rev-parse origin/cursor/full-upgrade-audit-eec2`  
**Remote changed files:** **50**  
**UI evidence:** `docs/manual-ui-review.md`  
**Artifacts (not in git):** `/tmp/ui-review-artifacts/screenshots/`

Status legend: **Completed** · **Failed** · **Blocked** · **Requires reviewer acceptance**

## Automated gates

| Item | Status |
| ---- | ------ |
| Remote PR shows ~50 files (not ~509) | Completed |
| CI checks green (frontend / backend / security-scan) | Completed (re-confirm on tip) |
| No merge conflict | Completed (`MERGEABLE` / `CLEAN` at review time) |
| Head SHA matches intended tip | Requires reviewer acceptance |

## Security & configuration

| Item | Status |
| ---- | ------ |
| Security diff reviewed | Requires reviewer acceptance (evidence in `docs/human-review-package.md`) |
| Production configuration reviewed | Completed (documented in `docs/manual-ui-review.md`) |
| No secret introduced in PR tip | Completed |
| Dependency residuals accepted (7 High codegen / 0 Critical) | Requires reviewer acceptance |
| CORS origins reviewed | Completed (list documented; confirm prod front-door) |
| Production variables reviewed | Completed |
| Historical secret rotation acknowledged | Requires reviewer acceptance (ops prerequisite) |

## Routing & authentication

| Item | Status |
| ---- | ------ |
| Routing reviewed | Completed |
| Authentication reviewed | Completed |
| Login reviewed | Completed |
| Logout reviewed | Completed |
| Browser refresh reviewed | Completed |
| Main menu reviewed | Completed |
| Nested routes reviewed | Completed |
| Non-root routing decision | **Blocked** — accept root-only **or** run subpath procedure |

## Theme & UI

| Item | Status |
| ---- | ------ |
| Light mode reviewed | Completed |
| Dark mode reviewed | Completed |
| RTL reviewed | Completed |
| Mobile reviewed | Completed (polish: oversized Search on narrow viewport) |
| Form / empty table surfaces reviewed | Completed (mocked empty data) |
| Validation reviewed | Completed (invalid login alert) |
| Console reviewed | Completed (Medium `ct.split` pageerror — accept or fix follow-up) |
| Network reviewed | Completed under mock |

## Environment blockers

| Item | Status |
| ---- | ------ |
| Docker blocker accepted or resolved | **Blocked** — Requires reviewer acceptance |
| Oracle blocker accepted or resolved | **Blocked** — Requires reviewer acceptance |
| Non-root routing accepted or resolved | **Blocked** — Requires reviewer acceptance |

## Approval

| Item | Status |
| ---- | ------ |
| Reviewer approval received | Requires reviewer acceptance |
| Merge performed by a human | Requires reviewer acceptance |

## Human sign-off

| Role | Name | Date | Decision |
| ---- | ---- | ---- | -------- |
| Reviewer | | | Accept blockers / Request changes |
| Ops (secret rotation) | | | Scheduled / Done |
