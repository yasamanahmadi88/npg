# Diff Scope Review

**Branch:** `cursor/full-upgrade-audit-eec2`  
**Compared to:** `origin/main`  
**Date:** 2026-07-10

## 1. Cause of the mass change

| Finding | Evidence |
| ------- | -------- |
| Initial PR file count | **499** |
| Equal add/delete line pairs | **464** |
| True EOL-only (CRLF↔LF) | **460** (byte-identical after stripping `\r`) |
| Semantic / added / deleted | **39–41** after cleanup |
| `core.autocrlf` / `core.eol` | unset in environment |
| `.gitattributes` | `* text=auto` (unchanged vs main; not modified in final PR) |

**Root cause:** Commits on this branch converted nearly the entire tree from CRLF (as stored on `origin/main`) to LF. Git reported every line as deleted and re-added. That was **not** functional migration work.

**Additional note:** `origin/main` already contains Angular 21 / Java 25 / Spring Boot **4.0.7**. `package.json`, `pom.xml`, and `angular.json` were EOL-only versus main and were reverted.

## 2. Cleanup actions performed

1. Restored 460 EOL-only paths from `origin/main` (`git checkout origin/main -- <paths>`).
2. Re-applied semantic edits on top of main blobs (preserving per-line endings) so mixed CRLF/LF files no longer whole-file rewrite.
3. Did **not** renormalize the repository and did **not** change `.gitattributes`.

## 3. Classification summary

| Category | File count | Keep/Revert | Reason |
| -------- | ---------: | ----------- | ------ |
| Line-ending-only | 460 | **Reverted** | No semantic change |
| Functional (routing/auth/UI) | ~12 | Keep | Absolute links, login/search, account routes, unload logout, styles, index |
| Security | ~7 | Keep | JWT filter, CORS, secrets, ExceptionTranslator |
| Theme | ~5 | Keep | ThemeService, tokens, init script, navbar toggle |
| Tests | ~6 | Keep | Unit + Playwright E2E |
| Docs | ~9 | Keep | Audit reports including this file |
| Dependency/lock/npmrc | 3 | Keep | npmjs registry for CI; `e2e` script; lock URL rewrite |
| CI / env example | 2 | Keep | `.github/workflows/ci.yml`, `.env.example` |

## 4. Counts

| Metric | Value |
| ------ | ----: |
| Initial changed files | 499 |
| EOL-only reverted | 460 |
| Final changed files vs `origin/main` | ~41 |
| Final shortstat (approx) | ~3.8k insertions / ~2.6k deletions (dominated by `package-lock.json` registry rewrite) |

## 5. Spring Boot 4.0.7 vs requested 4.0.6

Already declared on `origin/main`. This PR does not change `pom.xml` Spring Boot version. 4.0.7 remains the intentional patch line version.
