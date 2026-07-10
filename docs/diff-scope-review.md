# Diff Scope Review

**Branch:** `cursor/full-upgrade-audit-eec2`  
**Compared to:** `origin/main`  
**Date:** 2026-07-10  
**Committed tip:** `9ebdfe8` (+ follow-up doc commits on same branch)

## 1. Cause of the mass change

| Finding | Evidence |
| ------- | -------- |
| Initial PR file count | **499** |
| Equal add/delete line pairs | **464** |
| True EOL-only (CRLF↔LF) | **460** (byte-identical after stripping `\r`) |
| Semantic retained after cleanup | **44** (`git diff --name-only origin/main...HEAD`) |
| `core.autocrlf` | `false` (local `.git/config`) |
| `core.eol` | `lf` (local `.git/config`) |
| `.gitattributes` | `* text=auto` (**unchanged** vs main; not modified in this PR) |

**Root cause:** Commits on this branch converted nearly the entire tree from CRLF (as stored on `origin/main`) to LF. Git reported every line as deleted and re-added. That was **not** functional migration work.

**Additional note:** `origin/main` already contains Angular 21 / Java 25 / Spring Boot **4.0.7**. `package.json` (aside from the `e2e` script), `pom.xml`, and `angular.json` were EOL-only versus main and were reverted; `pom.xml` / `angular.json` are **not** in the final PR.

## 2. Cleanup actions performed

1. Restored 460 EOL-only paths from `origin/main` (`git checkout origin/main -- <paths>`).
2. Re-applied semantic edits on top of main blobs (preserving per-line endings) so mixed CRLF/LF files no longer whole-file rewrite.
3. Did **not** renormalize the repository and did **not** change `.gitattributes`.
4. Did **not** force-push or create a second PR.

## 3. Classification summary

| Category | File count | Keep/Revert | Reason |
| -------- | ---------: | ----------- | ------ |
| Line-ending-only | 460 | **Reverted** | No semantic change |
| Functional (routing/auth/UI) | 12 | Keep | Absolute links, login/search, account routes, unload logout, styles, index, README |
| Security | 7 | Keep | JWT filter, CORS, secrets, ExceptionTranslator, deleted JWTConfigurer |
| Theme | 5 | Keep | ThemeService, tokens, init script, navbar toggle |
| Tests | 8 | Keep | Unit + Playwright E2E + security ITs |
| Documentation | 9 | Keep | Audit reports (+ menu inventory) |
| Dependency/lock/npmrc | 3 | Keep | npmjs registry for CI; `e2e` script; lock URL rewrite |
| CI / env / eslint / gitignore | 4 | Keep | `.github/workflows/ci.yml`, `.env.example`, `eslint.config.js`, `.gitignore` |

## 4. Counts

| Metric | Value |
| ------ | ----: |
| Initial changed files | 499 |
| EOL-only reverted | 460 |
| Formatting-only reverted (separate from EOL) | 0 |
| Final changed files vs `origin/main` | **44** |
| Final shortstat | 3995 insertions / 2616 deletions (dominated by `package-lock.json` registry rewrite) |
| Equal add/delete pairs (>5 lines) remaining | **0** |

## 5. Working-tree phantom status

On Linux agents, `git status` may list hundreds of `M` files that are **byte-identical** to `HEAD`. Cause: blobs on `main`/branch store CRLF while `* text=auto` would clean to LF on the next add. **Do not re-add those files.** Source of truth for PR scope: `git diff origin/main...HEAD`.

## 6. Spring Boot 4.0.7 vs requested 4.0.6

Already declared on `origin/main`. This PR does not change `pom.xml` Spring Boot version. 4.0.7 remains the intentional patch-line version (Java 25 compatible via the Boot 4.0.x line already on main).
