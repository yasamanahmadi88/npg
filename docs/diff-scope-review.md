# Diff Scope Review

**Branch:** `cursor/full-upgrade-audit-eec2`  
**Compared to:** `origin/main`  
**Date:** 2026-07-10

## 1. Cause of the mass change

| Finding | Evidence |
| ------- | -------- |
| Initial PR file count | **499** (`git diff --name-only origin/main...HEAD \| wc -l`) |
| Equal add/delete line pairs | **464** files (`git diff --numstat` where `$1==$2`) |
| True EOL-only (CRLF↔LF) | **460** files (byte-identical after stripping `\r`) |
| Semantic / added / deleted | **39** files |
| `core.autocrlf` | unset |
| `core.eol` | unset |
| `.gitattributes` | `* text=auto` plus per-extension `text` rules (unchanged semantically; EOL-only rewrite on branch) |

**Root cause:** Working-tree / commit content converted CRLF → LF (and related wrapper script normalization) across nearly the entire tree. Git therefore reported every line as deleted and re-added. This is **not** a functional migration delta for those 460 files.

**Important:** `package.json`, `pom.xml`, and `angular.json` are **EOL-only** versus `origin/main`. The Angular 21 / Java 25 / Spring Boot 4.0.7 stack was already present on `main`. This branch’s real work is a smaller set of routing, security, theme, test, docs, and registry/CI fixes.

## 2. Classification summary

| Category | File count | Keep/Revert | Reason |
| -------- | ---------: | ----------- | ------ |
| Line-ending-only (CRLF↔LF) | 460 | **Revert** | No semantic change after `\r` strip |
| Real functional (routing/auth/UI) | 12 | **Keep** | Absolute links, login/search, account routes, unload logout removal, global.scss, index.html |
| Security change | 7 | **Keep** | SecurityConfiguration, WebConfigurer, JWTConfigurer delete, ExceptionTranslator, application-*.yml |
| Theme change | 5 | **Keep** | ThemeService, tokens, theme-init.js, font-awesome icons, navbar toggle |
| Test change | 5 | **Keep** | Theme/menu/login specs, WebConfigurerTest, SecurityWebConfigurationIT |
| Documentation change | 9 | **Keep** | `docs/*`, README |
| Dependency / lockfile / npmrc | 2 | **Keep** | `package-lock.json` + `.npmrc` (Artifactory → npmjs for reachable CI) |
| Tooling / ignore / CI | 4 | **Keep** | `.gitignore`, `eslint.config.js`, `.env.example`, `.github/workflows/ci.yml` |
| Mode-only noise on wrappers | (in EOL set) | **Revert** | `npmw` +x accompanied EOL rewrite; do not keep whole-file EOL churn |
| Formatting-only / encoding-only / generated | 0 | — | Not separately detected beyond EOL |
| Unrelated | 0 after cleanup | — | EOL noise treated as unrelated |

**Target after cleanup:** ~39 files (exact count may shift slightly if docs are updated in follow-up commits).

## 3. Cleanup plan

1. Restore all 460 EOL-only paths from `origin/main` via `git checkout origin/main -- <paths>` (no `reset --hard`).
2. Leave the 39 semantic paths untouched.
3. Commit as `chore: revert CRLF-only noise from migration PR`.
4. Re-run frontend/backend verification on the cleaned tree.
5. Continue browser/security/theme validation without expanding feature scope.
6. Push corrective commit(s) to the existing PR branch (no force-push, no new PR).

## 4. Suspicious large files

| File | Verdict |
| ---- | ------- |
| `pom.xml` | EOL-only → **revert** (Boot 4.0.7 already on main) |
| `package.json` | EOL-only → **revert** |
| `angular.json` | EOL-only → **revert** |
| `package-lock.json` | Semantic (registry URL rewrite + audit fix) → **keep** |
| Liquibase XML / i18n JSON / docker YAML | EOL-only → **revert** |
| `mvnw` / `mvnw.cmd` / `npmw*` | EOL-only → **revert** |
| Vendor `fontAwesome.js` / CSS | EOL-only → **revert** |

## 5. Spring Boot 4.0.7 vs requested 4.0.6

Already declared on `origin/main` as `4.0.7`. This cleanup PR does **not** change that property. 4.0.7 is a patch release in the 4.0.x line and remains the intentional declared version.
