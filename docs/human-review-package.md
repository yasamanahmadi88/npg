# Human review package — PR #5

**PR:** https://github.com/yasamanahmadi88/npg/pull/5  
**Branch:** `cursor/full-upgrade-audit-eec2`

## What changed (semantic)

- Absolute navbar/login routing; register + password-reset routes restored  
- Unload-time logout removed  
- JWT filter registration / CORS hardening / prod secrets via env  
- ThemeService + `theme-init.js` + navbar toggle  
- Tests, Playwright E2E, CI (LF-stripped `mvnw` for Linux)  
- Docs under `docs/`  
- **Blocker pass:** `ProfileService` ribbon contract + error degrade; dashboard mobile Search CSS; prod CORS override; Ops runbooks  

## What did not change

- No broad refactor  
- No line-ending renormalization of the repo  
- `pom.xml` Spring Boot / Java versions already on `main`  

## Reviewer focus

1. Routing absolute links + account routes  
2. Security filter / CORS / secret env wiring  
3. Theme init flash prevention  
4. `ProfileService` typeof guard (not optional-chaining on unknown contracts)  
5. Accept external blockers: Docker, Oracle, non-root, CORS Ops confirm, credential rotation  

## Decisions for human

| Decision | Recommendation |
| -------- | -------------- |
| Code merge | READY TO MERGE WITH ACCEPTED CODE-SCOPE BLOCKERS |
| Production deploy | NOT READY FOR PRODUCTION |

## Related docs

- `docs/final-validation-report.md`  
- `docs/manual-ui-review.md`  
- `docs/docker-verification-runbook.md`  
- `docs/oracle-smoke-test-runbook.md`  
- `docs/production-cors-checklist.md`  
- `docs/credential-rotation-plan.md`  
- `docs/remaining-risks.md`  
- `docs/security-review.md`  
- `docs/test-report.md`  
- `docs/release-candidate-verification.md`
