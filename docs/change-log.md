# Change Log

## 2026-07-10 — PR cleanup pass

- Reverted **460** CRLF-only whole-file rewrites vs `origin/main`.
- Retained **45** semantic files vs `origin/main` (routing, security, theme, tests, docs, npm registry/CI).
- Re-applied edits on main blobs to avoid mixed-EOL full-file diffs.
- Added Playwright E2E (5 critical flows) with static SPA serve + API mocks.
- Added `main.auth-lifecycle.spec.ts` proving unload logout was removed.
- Added `docs/diff-scope-review.md`, `docs/menu-route-inventory.md`, and refreshed validation evidence.
- Did **not** force-push or create a new PR.

## 2026-07-10 — Initial repair commits (semantic subset retained)

Routing, security, theme, and documentation fixes retained; mass EOL noise removed in cleanup commits.
