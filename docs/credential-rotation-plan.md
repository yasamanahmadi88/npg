# Historical credential rotation plan

**PR:** #5  
**Rule:** Do **not** rewrite Git history automatically.  
**Rule:** Assume exposed credentials are compromised until proven otherwise.  
**Production approval:** Application must **not** be approved for Production while known-valid exposed credentials remain active.

## Context

Prior repository history and older config revisions may have contained database passwords and JWT signing secrets. The current PR moves Production secrets to environment variables (no hardcoded JWT/DB password defaults in `application-prod.yml`). Rotation is still required for any secret that ever appeared in history or shared logs.

## Rotation register

| Credential type | Potential exposure | Current validity known? | Rotation owner | Deadline | Verification |
| --------------- | ------------------ | ----------------------- | -------------- | -------- | ------------ |
| Database password | Historical YAML / logs / tickets | Unknown — assume yes | DBA / Ops | Before Production deploy | Old password rejected; app connects with new |
| JWT signing secret | Historical YAML / env samples | Unknown — assume yes | App security / Ops | Before Production deploy | Old tokens invalid; new login issues valid JWT |
| API tokens | CI logs / old scripts | Unknown | Owning service team | Before Production deploy | Token revoked at provider |
| OAuth client secret | If ever committed (search history) | Unknown | IdP owner | Before Production deploy | Client secret rotated in IdP |
| CI credentials | Workflow logs / old secrets | Unknown | Repo admins | Before Production deploy | Old PAT/deploy keys revoked |
| Private keys / certificates | Keystores in repo or artifacts | Unknown | Ops / TLS owner | Before Production deploy | New cert active; old revoked |

## Required actions (ordered)

1. Assume exposed credentials are compromised until proven otherwise.  
2. Rotate **before or at** Production deployment.  
3. Revoke old values (DB user password change, JWT secret replace, token revoke).  
4. Update secret stores (vault / CI secrets / orchestrator env) — **never commit new secrets**.  
5. Restart affected services safely (rolling restart).  
6. Verify old credentials no longer work.  
7. Verify new credentials work (login, DB health, authenticated API).  
8. Record completion **outside** the repository (ticket / vault audit) without storing secret values.

## Application config expectations after rotation

- `SPRING_DATASOURCE_USERNAME` / `SPRING_DATASOURCE_PASSWORD` set only via environment / secret store  
- `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` set only via environment / secret store  
- `.env` is gitignored; `.env.example` contains placeholders only  
- Missing required secrets must fail startup (unresolved Spring placeholders)

## Status

**Historical credential rotation: BLOCKED / PENDING Ops execution.**  
Code merge may proceed with this as an accepted operational blocker; **Production deployment may not.**
