# Change Log

## 2026-07-10 — Full upgrade audit repair branch

### Frontend
- Absolute navbar `routerLink` paths for all menu items
- Login / search absolute navigation fixes
- Restored account `register` and `reset/request` routes
- Removed logout-on-unload behavior
- Added `ThemeService`, theme tokens SCSS, FOUC-safe `theme-init.js`, navbar theme toggle
- Removed duplicate Bootswatch Lumen import
- Routing/theme regression tests
- npm registry fallback to npmjs.org for environments without Artifactory
- ESLint ignore/config adjustments for vendor JS and migration noise

### Backend
- Direct JWT filter registration (removed `JWTConfigurer` / `SecurityConfigurerAdapter`)
- CORS single-source allow-list; reject `*` with credentials
- Prod secrets via environment variables; `.env.example` added
- ExceptionTranslator annotation migration
- Security integration tests for auth + CORS
- WebConfigurer unit test updates

### Tooling / docs
- GitHub Actions CI workflow
- Documentation set under `docs/`
- README prerequisites and theme/security notes updated
- `.gitignore` includes `.env`
