# Remaining Risks

| Risk | Severity | Mitigation / next action |
| ---- | -------- | ------------------------ |
| Private npm Artifactory unreachable here | Medium | Restore `.npmrc.artifactory.bak` on corporate networks |
| Oracle-specific SQL / Liquibase not exercised live | Medium | Run Testcontainers Oracle profile in CI with credentials |
| Actuator health MockMvc mapping anomaly under Boot 4 | Low | Verify `/management/health` on a running JVM |
| Historical secrets in git history | High (ops) | Rotate JWT + DB passwords |
| No Docker in agent environment | Medium | Validate Jib image + compose on a Docker host |
| Full live authenticated E2E (real API/Oracle) | Medium | Playwright static+mock E2E PASS (8); extend with ephemeral backend |
| Non-root `base href` / context path undeployed | Medium | Dedicated deploy-url verification before non-root release |
| `legacy-peer-deps=true` still in `.npmrc` | Low | Resolve peer graph and remove flag |
| Infinispan 2LC disabled but deps remain | Low | Remove unused cache stack or re-enable intentionally |
| In-memory session registry | Medium | Externalize for HA |
| Visual regression baselines not committed | Low | Optional Playwright screenshot baselines |
| npm High in generator-jhipster/Yeoman tree | Low (runtime) | Accept until generator major upgrade; not in SPA bundle |
| Linux phantom `git status` on CRLF blobs | Low | Dedicated EOL renormalization PR later — do not mix into #5 |
