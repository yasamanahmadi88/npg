# Remaining risks — PR #5

| Risk | Severity | Status | Mitigation |
| ---- | -------- | ------ | ---------- |
| Docker runtime unverified | High (prod) | BLOCKED | `docs/docker-verification-runbook.md` |
| Oracle compatibility unverified | High (prod) | BLOCKED | `docs/oracle-smoke-test-runbook.md` |
| Non-root / context-path unknown | Medium | BLOCKED | Owner must choose root-only (N/A) or subpath test |
| Production CORS origins | High (prod) | BLOCKED | `docs/production-cors-checklist.md` |
| Historical credentials | High (prod) | BLOCKED | `docs/credential-rotation-plan.md` |
| npm High in Yeoman/generator tooling | Low (runtime) | ACCEPTED | Not shipped in SPA runtime bundle |
| `Ct` HttpErrorResponse under SPA-mock without info JSON | Low | MOCK-ONLY | Mock `/management/info`; real backend returns JSON |
| Ribbon `.split` on non-string | Medium | FIXED | `typeof === 'string'` + tests |
| Mobile dashboard Search overflow | Low | FIXED | Responsive CSS + Playwright |

## Explicit non-claims

- H2 verify ≠ Oracle proven  
- Playwright mocked API ≠ full backend E2E  
- CI green ≠ Production ready  
