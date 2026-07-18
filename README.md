# NPG Portal

Number Portability Gateway Management Portal — JHipster-origin enterprise SPA + Spring Boot API.

## Stack (validated)

| Layer | Version |
| ----- | ------- |
| Angular | 21.2.18 |
| Node.js | 22.22.x (engines: `>=22.12.0 <23`) |
| TypeScript | 5.9.x |
| Java | 25 |
| Spring Boot | 4.0.7 |
| Hibernate | 7.2.x |
| Database | Oracle (dev/prod), H2 (tests) |
| Package manager | npm |

## Prerequisites

1. **JDK 25** on `PATH` (`JAVA_HOME` set).
2. **Node.js 22.22+** and npm 10.x.
3. Access to an Oracle instance for full local backend runs, **or** override datasource to a disposable DB.
4. Copy `.env.example` and export required variables for production-like runs.

Corporate npm installs may use Artifactory (see `.npmrc.artifactory.bak`). This repository’s default `.npmrc` points at `registry.npmjs.org` for environments without Artifactory.

## Frontend

```bash
npm ci
npm start          # http://localhost:4200 (proxies /api → :8080)
npm run lint
npx jest --config jest.conf.js --watch=false --coverage=false
npx ng build --configuration production
```

### Theme

- Light and dark themes via `html[data-theme]` / CSS tokens (`content/scss/_theme-tokens.scss`).
- Navbar toggle (sun/moon) with accessible label/`aria-pressed`.
- Preference key: `npg-portal-theme` in `localStorage`.
- System `prefers-color-scheme` used when no explicit preference exists.
- Early init: `content/js/theme-init.js` (no page reload required to switch).

### Routing / SPA fallback

- Angular routes are client-side; production JAR serves `index.html` via `ClientForwardController` for non-API paths.
- Deep links and refresh require the backend (or reverse proxy) SPA fallback — not only `ng serve`.

## Backend

```bash
./mvnw -P-webapp                 # run with default Maven profiles
./mvnw -ntp -P-webapp verify     # unit + integration tests (H2)
```

### Environment variables

See `.env.example`. Production **requires**:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET` (generate: `openssl rand -base64 64`)

### Security notes

- Authentication: Bearer JWT + server-side session registry (`SecurityCache`).
- CSRF disabled because the SPA uses Authorization headers (stateless), not cookie form posts.
- CORS origins are allow-listed in `application.yml` (`jhipster.cors`); wildcard + credentials is rejected.

## Docker

Jib builds images (`./mvnw -Pprod jib:dockerBuild`) using `eclipse-temurin:25-jre`. Compose fragments live under `src/main/docker/`. Full compose Oracle service is not bundled — provide an external DB.

## Documentation

| Doc | Purpose |
| --- | ------- |
| `docs/upgrade-audit.md` | Version matrix + issue ledger |
| `docs/root-cause-analysis.md` | Confirmed root causes |
| `docs/security-review.md` | ASVS-oriented security report |
| `docs/test-report.md` | Executed test evidence |
| `docs/change-log.md` | Change summary |
| `docs/remaining-risks.md` | Blockers and residual risk |
| `docs/final-validation-report.md` | Acceptance PASS/FAIL/BLOCKED |

## CI

GitHub Actions workflow: `.github/workflows/ci.yml` (frontend lint/test/build, backend verify, heuristic secret scan).
