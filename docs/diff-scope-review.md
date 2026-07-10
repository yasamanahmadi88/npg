# Diff Scope Review

**Branch:** `cursor/full-upgrade-audit-eec2`  
**Compared to:** `origin/main`  
**Confirmed by GitHub PR #5 `changedFiles`:** matches `git diff origin/main...HEAD`

## Cause of the original ~499-file PR

| Finding | Evidence |
| ------- | -------- |
| Initial PR file count | **499** |
| True EOL-only (CRLF↔LF) | **460** |
| `core.autocrlf` | `false` |
| `core.eol` | often `lf` in agent config (worsens phantoms) |
| `.gitattributes` | `* text=auto` (**unchanged**) |

Root cause: branch commits converted CRLF blobs (as on `main`) to LF → every line appeared rewritten.

## Final counts

| Metric | Value |
| ------ | ----: |
| EOL-only reverted | 460 |
| Final files vs `origin/main` | **45** before RC doc adds; see GitHub after push |
| Equal add/delete pairs (>5) | **0** |
| `pom.xml` / `angular.json` in PR? | **No** (EOL-only; reverted) |

## Phantom local `git status`

Fresh worktrees still show ~470 `M` files that are **byte-identical** to HEAD because text blobs remain CRLF under `text=auto`. **Do not stage them.** Fix requires a dedicated renormalization PR, not this migration PR.

## Spring Boot 4.0.7

Already on `origin/main`. This PR does not change Boot version. Requested 4.0.6 is superseded by patch 4.0.7 already present upstream.
