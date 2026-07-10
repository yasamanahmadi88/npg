# Change Log

## 2026-07-10 — Human review package

- Added `docs/pr-review-checklist.md` and `docs/human-review-package.md`.
- Confirmed live GitHub PR #5: **49** files after review-package docs, CI green, mergeable.
- Decision: **READY TO MERGE AFTER MANUAL UI REVIEW**.

## 2026-07-10 — Release candidate verification

- Confirmed GitHub PR #5 remote diff reached **47** before review-package docs (now **49**) (not 499).
- Fixed CI backend failure: run Maven via LF-stripped `mvnw` (CRLF shebang).
- Expanded Playwright to **8** critical browser tests.
- Expanded `SecurityWebConfigurationIT` (+3).
- Added `@playwright/test` for clean-worktree E2E.
- Patched **Angular 21.2.18** and **concurrently 9.2.3** for npm High/Critical findings.
- Documented phantom CRLF working-tree limitation; no force-push; no second PR.

## 2026-07-10 — PR cleanup pass

- Reverted **460** CRLF-only whole-file rewrites vs `origin/main`.
- Preserved routing, security, theme, tests, docs, npm registry/CI changes.
- Added Playwright E2E and auth-lifecycle coverage.

## 2026-07-10 — Initial repair commits (semantic subset retained)

Routing, security, theme, and documentation fixes retained; mass EOL noise removed in cleanup commits.
