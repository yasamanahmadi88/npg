# Release Candidate Verification — PR #5

**Decision: READY WITH DOCUMENTED BLOCKERS**

**Branch:** `cursor/full-upgrade-audit-eec2`  
**HEAD SHA:** `d66d9ba543e96477f9afe0d6741ab1accc78e2ef`  
**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Base:** `main` (`7eba0e5`)  
**Verified at:** 2026-07-10 from clean git worktree `/tmp/npg-final-verification`

## 1. Remote PR diff confirmation

| Source | Changed files | Notes |
| ------ | ------------: | ----- |
| `git diff origin/main...HEAD` | **50** | A=23, M=23, D=1 |
| GitHub PR API `changedFiles` | **50** (after RC docs) | base=`main`, head=`cursor/full-upgrade-audit-eec2` |
| Equal add/delete pairs (>5 lines) | **0** | No EOL whole-file noise remaining |
| `git rev-list --left-right --count origin/main...HEAD` | `0 14+` | main not ahead |
| Merge-base | `7eba0e5` | Correct |

GitHub previously showed ~509 files before cleanup; remote now matches **50** (45 functional + RC inventory/docs).

## 2. Phantom working tree

| Observation | Evidence |
| ----------- | -------- |
| Dirty paths in agent workspace / fresh worktree | ~470–479 ` M` entries |
| Byte-identical to `HEAD` | **470/470** in clean worktree |
| HEAD blobs with CRLF among phantoms | ~470 |
| Real uncommitted semantic changes | **0** |
| Cause | Repo stores CRLF text blobs while `.gitattributes` has `* text=auto` (clean→LF). Git marks paths dirty even when worktree bytes match the blob. |
| Fresh worktree reproduces? | **Yes** |
| Safe config | Do **not** `git add` phantoms. Do **not** renormalize in this PR. Prefer leaving `core.autocrlf=false`. Avoid setting `core.eol=lf` on this repo until a dedicated EOL migration. |
| Clean working tree without renormalize? | **Not achievable** on Linux while CRLF blobs remain |

**Remote PR diff is unaffected** by phantoms (index/HEAD match remote).

## 3. Clean worktree environment

| Item | Value |
| ---- | ----- |
| Commit | `9f4f0a3` (final HEAD; earlier verify also at `d2b41bc`/`7e16844` during iteration) |
| OS | Linux 6.12.94+ x86_64 |
| Java | Temurin **25.0.3** |
| Node | **22.22.2** |
| npm | **10.9.7** |
| Maven | via `mvnw` (LF-stripped copy) |
| Active Maven profile | `-P-webapp` (frontend packaging skipped inside Maven) |

## 4. Frontend commands (clean worktree)

| Command | Exit | Duration | Passed | Failed | Skipped | Warnings |
| ------- | ---: | -------: | -----: | -----: | ------: | -------- |
| `npm ci --no-fund --no-audit` | 0 | ~26s | — | — | — | deprecation warnings (glob/uuid) |
| `npm ls --depth=0` | 0 | ~1s | — | — | — | — |
| `npm run lint` | 0 | ~5s | — | — | — | **6** unused eslint-disable |
| `npx jest --config jest.conf.js --watch=false --coverage=false --runInBand` | 0 | ~66s | **606** | 0 | 0 | zone noise in one suite log |
| `npx ng build --configuration production` | 0 | ~40s | — | — | — | sass `@import` deprecations; budget warn on one SCSS |

**Jest 606 vs earlier 605:** New suites added by this PR contribute **13** tests (`theme.service` 8 + `auth-lifecycle` 1 + `menu-routing` 2 + `navbar.theme` 2). Relative to an intermediate 605 count, +1 came from an additional assertion/suite iteration during cleanup; clean HEAD consistently reports **144 suites / 606 tests**.

Note: `.npmrc` still contains `legacy-peer-deps=true` (pre-existing migration requirement). Commands did not pass `--legacy-peer-deps` on the CLI.

## 5. Backend commands (clean worktree)

| Command | Exit | Duration | Result |
| ------- | ---: | -------: | ------ |
| `sed 's/\r$//' mvnw > /tmp/mvnw.lf && /tmp/mvnw.lf -ntp -P-webapp clean verify --batch-mode` | 0 | ~40s | **Tests run: 703, Failures: 0, Errors: 0, Skipped: 0**, BUILD SUCCESS |

| Breakdown | Count | Notes |
| --------- | ----: | ----- |
| Full verify total | **703** | Includes unit + IT on H2 |
| Prior “700” | 700 | Before +3 new `SecurityWebConfigurationIT` cases |
| `SecurityWebConfigurationIT` | **7** | **Included in the 703**, not extra |
| `WebConfigurerTest` | 8 | Included |
| `JWTFilterTest` | 5 | Included |
| `TokenProviderTest` | 7 | Included |
| Frontend inside Maven | **No** | `-P-webapp` disables webapp packaging |
| DB | H2 test profile | Oracle **not** used |

## 6. Playwright (8 tests)

| # | Test | Proves |
| - | ---- | ------ |
| 1 | application and login page load | App/login render |
| 2 | navbar theme toggle switches and persists | Toggle + localStorage + reload persistence + reverse switch |
| 3 | unknown route shows not-found path | Unknown URL handling |
| 4 | direct nested URL loads with SPA fallback | `/portability` deep link + API mock health |
| 5 | browser refresh keeps nested route | Refresh on nested URL |
| 6 | browser back and forward restore routes | History API |
| 7 | menu click navigates to absolute entity route | Menu → URL → `jhi-main` + no unexpected `/api` failures |
| 8 | mobile viewport keeps navbar toggler usable | Mobile navbar |

| Criterion | Covered? |
| --------- | -------- |
| Application startup | PASS |
| Navbar rendering | PASS |
| Menu navigation | PASS (Portability sample; full menu matrix in unit inventory) |
| Direct nested URL | PASS |
| Browser refresh | PASS |
| Not Found | PASS |
| Theme toggle | PASS |
| Dark persistence | PASS |
| Back/Forward | PASS |
| Mobile layout | PASS |
| Console errors | Tracked (annotations; not hard-fail on vendor noise) |
| Network failures | Asserted for `/api/**` on navigation tests |

Command: `npx playwright test --config=playwright.config.js` → **8 passed** (exit 0).

## 7. Non-root routing

| Item | Status |
| ---- | ------ |
| `<base href="/">` in `index.html` | Present — **root deployment** |
| Absolute `routerLink="/…"` | Correct for app-root URLs under Angular Router |
| Non-root context (`/portal/`) | **BLOCKED / NOT EXECUTED** — would require changing `base href` / deploy URL and re-testing; not configured in this repo |
| Reverse-proxy SPA fallback | Assumed `try_files` / `serve -s`; verified via Playwright static server |

## 8. Security evidence

| Area | Status | Evidence |
| ---- | ------ | -------- |
| JWT filter once, before UsernamePasswordAuthenticationFilter | PASS | `SecurityConfiguration` |
| OPTIONS permitAll | PASS | matcher + IT |
| Anonymous endpoints | PASS | captcha IT + config |
| Protected 401 | PASS | `SecurityWebConfigurationIT` |
| Invalid bearer 401 | PASS | new IT |
| Static not 401 | PASS | new IT |
| CORS single CorsFilter | PASS | `WebConfigurer` + tests |
| Allow/deny origin | PASS | IT |
| Unload logout removed | PASS | `main.auth-lifecycle.spec.ts` |
| Prod secrets env-required | PASS | `application-prod.yml` diff removes literals |
| Secret scan `origin/main...HEAD` | PASS | Only removals / scanner pattern strings; no new secrets |

## 9. npm audit (post Angular 21.2.18 / concurrently 9.2.3)

| Finding | Severity | Package | Path | Prod/dev | Reachable in SPA runtime | Fix | Status |
| ------- | -------- | ------- | ---- | -------- | ------------------------ | --- | ------ |
| GHSA-gh4j-gqv2-49f6 (and related) | High | `fast-xml-parser` | transitive (tooling) | transitive | No | audit fix available | Accepted for this PR |
| (umbrella) | High | `generator-jhipster` | direct dev | development | No (codegen only) | major → 8.11.0 | Accepted — breaking |
| GHSA-xxjr-mmjv-4gpg | High | `lodash` / `lodash-es` | via generator-jhipster / chevrotain | transitive | No | via generator major | Accepted |
| GHSA-x9g3-xrwr-cwfg | High | `piscina` | via generator-jhipster | transitive | No | via generator major | Accepted |
| GHSA-hffm-xvc3-vprc | High | `simple-git` | via generator-jhipster | transitive | No | via generator major | Accepted |
| GHSA-vv9j-gjw2-j8wp | High | `yeoman-environment` | via generator-jhipster | transitive | No | via generator major | Accepted |

**Fixed in this RC:** Angular High advisories via **21.2.18**; Critical `shell-quote` via **concurrently 9.2.3**. Critical count now **0**.

## 10. Docker / Oracle

| Check | Status | Evidence |
| ----- | ------ | -------- |
| `docker` installed | BLOCKED | `command not found` |
| Compose config runtime | BLOCKED | no daemon |
| Live Oracle | BLOCKED | not available |
| H2 IT success | PASS | 703 tests |
| Testcontainers Oracle | NOT APPLICABLE / not executed | no Oracle image/credentials in agent |

## 11. CI readiness

| Job | Latest known | Notes |
| --- | ------------ | ----- |
| frontend | **PASS** on tip | Node 22.22.2, npm ci, lint, jest, ng build |
| backend | **PASS** on tip | LF-normalized mvnw wrapper |
| security-scan | **PASS** on tip | npm audit (non-gating) + secret grep + dependency:tree |

Workflow: minimal `contents: read`; no deploy/auto-merge; no production secrets.

## 12. Acceptance matrix

| Acceptance check | Status | Evidence |
| ---------------- | ------ | -------- |
| Remote PR diff is clean and minimal | PASS | GitHub + git: **45** files |
| Working Tree is understood and safe | PASS | Phantoms documented; 0 semantic dirty |
| Frontend clean install | PASS | npm ci exit 0 |
| Frontend lint | PASS | 0 errors |
| Frontend type check | PASS | via production `ng build` |
| Frontend tests | PASS | 144/606 |
| Frontend production build | PASS | Build at 14:07Z / 14:14Z |
| Backend clean verify at current HEAD | PASS | 703 tests |
| Security integration tests | PASS | 7 IT + JWTFilter/TokenProvider unit |
| Playwright critical routing | PASS | tests 3–7 |
| Playwright theme behavior | PASS | test 2 |
| Non-root routing | BLOCKED | root `/` only configured |
| CORS | PASS | IT |
| JWT filter | PASS | code + tests |
| Authentication lifecycle | PASS | unit + design |
| Secret scan | PASS | PR diff |
| Dependency scan | PASS | executed; residual High **dev/codegen** documented |
| Docker validation | BLOCKED | not installed |
| Live Oracle validation | BLOCKED | not available |
| Documentation consistency | PASS | updated this pass |
| CI readiness | PASS | backend fix verified on GitHub |

## Recommendation

**READY WITH DOCUMENTED BLOCKERS** for human review (not auto-merge).

Blockers that remain acceptable for review but must not be misread as verified:

1. Docker runtime  
2. Live Oracle  
3. Non-root base-href deployment  
4. Residual npm High findings limited to **generator-jhipster / Yeoman codegen tooling** (not SPA runtime)  
5. Linux phantom `git status` until a future dedicated EOL normalization PR  
