# Remaining Risks

| Risk | Severity | Mitigation / next action |
| ---- | -------- | ------------------------ |
| Private npm Artifactory unreachable here | Medium | Restore `.npmrc.artifactory.bak` on corporate networks; keep npmjs mirror for CI sandboxes |
| Oracle-specific SQL / Liquibase not exercised live | Medium | Run Testcontainers Oracle profile in CI with credentials |
| Actuator health MockMvc mapping anomaly under Boot 4 | Low | Verify `/management/health` on a running JVM; confirm actuator auto-config |
| Historical secrets in git history | High (ops) | Rotate JWT + DB passwords; scrub history if required by policy |
| No Docker in agent environment | Medium | Validate Jib image + compose on a Docker host |
| Full live authenticated E2E (real API/Oracle) | Medium | Playwright static+mock E2E already PASS; extend with ephemeral backend |
| `legacy-peer-deps=true` still in `.npmrc` | Low | Resolve peer graph and remove flag |
| Infinispan 2LC disabled but deps remain | Low | Remove unused cache stack or re-enable intentionally |
| In-memory session registry | Medium | Externalize for HA |
| Visual regression baselines not committed | Low | Optional Playwright screenshot baselines |
| npm audit High/Critical in **dev** tooling | Low (runtime) | Tracked in `docs/security-review.md`; not production SPA bundle |
