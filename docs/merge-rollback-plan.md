# Merge rollback plan — PR #5

Use a **standard merge-revert** strategy. Do **not** reset shared `main` history. Do **not** force-push to shared branches.

## Identify the merge commit

```bash
git fetch origin
git log origin/main --oneline --merges -5
# Or find the commit that merged PR #5:
git log origin/main --oneline --grep='#' -20
git show <MERGE_COMMIT_SHA> --stat
```

Confirm parents: merge commit should have two parents (`main` prior tip + PR head).

## Revert the merge safely

```bash
git checkout main
git pull origin main
# -m 1 = keep first parent (mainline) as the mainline side of the revert
git revert -m 1 <MERGE_COMMIT_SHA>
# Resolve conflicts if any, then:
git commit   # if needed after conflict resolution
git push origin main
```

Open a normal revert PR if branch protection requires review — do not bypass policy.

## Do not

* `git reset --hard` on shared `main`
* Force-push to `main`
* Rewrite or purge Git history to “undo” secrets (rotation handles secrets; history rewrite is a separate emergency process)

## Verify rollback

```bash
git fetch origin
git log origin/main -3 --oneline
# Automated
npm ci && npm run lint
npx jest --config jest.conf.js --watch=false --coverage=false --runInBand
sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf
/tmp/mvnw.lf -ntp -P-webapp clean verify --batch-mode
# Smoke: app starts; login path behaves as pre-merge baseline
```

Confirm critical pre-merge behaviors that this PR changed are restored to the prior baseline (or explicitly accepted as still changed if only a partial revert).

## Database migration considerations

* This PR’s primary risk is **application/config**, not a large new Liquibase expansion unique to the PR tip — still verify Liquibase state on any environment that ran migrations under the merged build.
* **Do not** automatically roll back database schema with destructive scripts.
* If a forward migration already applied in an environment, engage DBA for a controlled forward-fix or compensating migration — never drop production data to “match” a revert.
* H2 test DBs are disposable; non-prod Oracle requires DBA change control.

## Frontend cache invalidation

* Production/static assets are content-hashed; after revert/redeploy, clients should receive new hashes.
* If a service worker / `ngsw` is active, ensure redeploy invalidates or updates the worker so users are not stuck on the reverted-away bundle.
* Hard-refresh / cache-bypass check on `/` and `/login` after rollback deploy.

## Container rollback considerations

* Redeploy the **previous known-good image** tag/digest (not “latest” ambiguity).
* Confirm env vars / secrets still match the rolled-back application expectations.
* Run health checks from `docs/docker-verification-runbook.md` against the rolled-back revision.
* Drain old replicas only after new (rolled-back) replicas are healthy.

## Secret rotation must not be reversed

If DB passwords, JWT secrets, or API tokens were rotated as part of go-live preparation:

* **Keep** the new secrets.
* **Do not** restore historical/exposed secrets to “match” an old build.
* Point the rolled-back application at the **current** secret store values (or rotate again if the rolled-back build cannot accept the new secret format — prefer re-rotate forward).

## Monitoring signals that should trigger rollback

* Sustained 5xx on `/api/**` or login (`/api/authenticate`)
* Health endpoint DOWN / DB connection pool exhaustion
* Spike in authentication failures after release
* CORS failures blocking the Production frontend origin
* SPA white-screen / failed chunk load after deploy
* Liquibase lock / migration failure preventing startup
* Error budget / SLO burn exceeding team threshold within the release window

## Ownership

| Step | Owner |
| ---- | ----- |
| Decide to revert | Reviewer + Deploy lead |
| Execute revert commit/PR | Engineering |
| Redeploy previous image | Ops |
| DB assessment | DBA |
| Secret continuity | Security / Ops |
