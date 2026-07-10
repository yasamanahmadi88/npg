# PR #5 — Manual Review Checklist

**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Branch:** `cursor/full-upgrade-audit-eec2`  
**Base:** `main`  
**Verified tip SHA (at checklist authoring):** `619d3dc1bac581a570a4cc937116bd0242ac3398`  
**Remote changed files:** **47** (GitHub `changedFiles` + `git diff origin/main...HEAD`)

Re-confirm SHA and CI on the PR page before approving.

## Automated gates (confirm on GitHub)

- [ ] Remote PR shows **~47** changed files (not ~499)
- [ ] CI checks are green (`frontend`, `backend`, `security-scan`)
- [ ] No merge conflict (`MERGEABLE` / clean)
- [ ] Head SHA matches the branch tip you intend to merge

## Security & configuration

- [ ] Security diff reviewed (`SecurityConfiguration.java`, deleted `JWTConfigurer.java`, `WebConfigurer.java`)
- [ ] Production configuration reviewed (`application-prod.yml`, `application-dev.yml`, `application.yml`)
- [ ] No secret introduced (prod password/JWT literals removed; `.env.example` placeholders only)
- [ ] Dependency residuals accepted (7 npm **High** in generator-jhipster/Yeoman tooling; **0 Critical**; Angular **21.2.18** resolved)

## Routing & authentication

- [ ] Routing reviewed (absolute navbar/login links; register + password-reset routes restored)
- [ ] Authentication reviewed (unload logout removed; explicit logout still via `LoginService`)
- [ ] Browser refresh reviewed (session must survive refresh)
- [ ] Explicit logout reviewed (client token cleared; protected routes denied after logout)
- [ ] Non-root routing decision accepted (**BLOCKED** — app ships `<base href="/">`; subpath not verified)

## Theme & UI (manual)

- [ ] Theme reviewed in Light mode
- [ ] Theme reviewed in Dark mode
- [ ] RTL reviewed (Persian `dir` / layout)
- [ ] Login reviewed
- [ ] Main menu reviewed (absolute destinations from nested routes)
- [ ] Contrast acceptable for errors, links, tables, borders, disabled controls

## Environment blockers

- [ ] Docker blocker accepted or resolved (**BLOCKED** in agent/CI — no daemon)
- [ ] Oracle blocker accepted or resolved (**BLOCKED** — H2 ITs only; no live Oracle)

## Approval

- [ ] Reviewer approval received
- [ ] Merge performed by a human (do not auto-merge)

## Quick evidence pointers

| Area | Where to look |
| ---- | ------------- |
| Full RC evidence | `docs/release-candidate-verification.md` |
| File-by-file inventory | `docs/retained-file-inventory.md` / this package below |
| Menu → route map | `docs/menu-route-inventory.md` |
| npm residuals | `docs/security-review.md` |
| Jest 606 / Maven 703 / Playwright 8 | `docs/test-report.md` |
