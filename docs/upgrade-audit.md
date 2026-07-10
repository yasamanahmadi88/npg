# Upgrade Audit — NPG Portal

**Branch:** `cursor/full-upgrade-audit-eec2`  
**Date:** 2026-07-10  
**Stack (validated):** Angular 21.2.14 · Node 22.22.2 · TypeScript 5.9.3 · Java 25.0.3 · Spring Boot 4.0.7 · Hibernate 7.2.12 · JHipster Framework 9.1.0

## Version matrix

| Component | Requested | Declared | Resolved/Runtime | Docker/CI | Notes |
| --------- | --------- | -------- | ---------------- | --------- | ----- |
| Angular | 21 | 21.2.14 | 21.2.14 | CI Node 22.22.2 | PASS |
| Node | Angular 21 support | `>=22.12 <23` | 22.22.2 | 22.22.2 | PASS |
| TypeScript | Angular 21 | ~5.9.3 | 5.9.3 | — | PASS |
| Java | 25 | 25 | Temurin 25.0.3 | Temurin 25 | Local agent initially had only JDK 21; JDK 25 installed for builds |
| Spring Boot | 4.0.6 | **4.0.7** | 4.0.7 | — | Declared 4.0.7 (newer than prompt 4.0.6) |
| Hibernate | Boot-managed | 7.2.12.Final | 7.2.12.Final | — | PASS |
| npm registry | corporate Artifactory | was `art.behsacorp.com` | `registry.npmjs.org` | — | Private registry ENOTFOUND in this environment |

## Issue ledger

| ID | Symptom | Root cause | Fix | Status |
| -- | ------- | ---------- | --- | ------ |
| R1 | Navbar/menu navigation broken from nested routes | Relative `routerLink` values | Absolute `/…` links | PASS |
| R2 | Login redirect to `/login/dashboard` | `navigate(['./dashboard'])` | Absolute `/dashboard` | PASS |
| R3 | Register / password-reset-init wrong components | Routes pointed at dashboard with `path: ''` | Restored JHipster paths + components | PASS |
| R4 | Search navigates relative to current URL | `./portability` | `/portability` | PASS |
| R5 | Session destroyed on browser refresh | `beforeunload` → logout | Removed destructive unload logout | PASS |
| R6 | Duplicate conflicting CORS layers | CorsFilter + WebMvcConfigurer with `*` patterns | Single CorsFilter; reject `*`+credentials | PASS |
| R7 | Hardcoded prod DB password / JWT secret | Secrets in YAML | Env vars; prod requires secrets | PASS |
| R8 | `javax.annotation` in ExceptionTranslator | Incomplete Jakarta migration | Spring `NonNull`/`Nullable` | PASS |
| R9 | Deprecated `SecurityConfigurerAdapter` JWT wiring | Legacy JHipster pattern | Direct `JWTFilter` registration | PASS |
| R10 | No light/dark theme | Missing ThemeService/tokens/toggle | Implemented | PASS |
| R11 | Duplicate Bootstrap Cosmo+Lumen imports | SCSS conflict | Cosmo only | PASS |
| R12 | npm install failed | Private Artifactory unreachable | Public registry + lock URL rewrite | PASS (env-specific) |
| R13 | Actuator `/management/health` via MockMvc | Boot 4 resource fallback in some IT contexts | Documented; auth tests cover API protection | BLOCKED (runtime health needs live app) |
| R14 | Docker Compose runtime | Docker unavailable in agent | Static compose validation only | BLOCKED (Docker) |
| R14b | Browser E2E | Initially blocked | Playwright + `serve -s` + API mocks | PASS (5 tests); live API still BLOCKED |
| R15 | Oracle DB live verification | No local Oracle | H2 ITs used | BLOCKED for Oracle-specific SQL |

## Architecture summary

- **Frontend:** NgModule JHipster Angular app under `src/main/webapp`, custom webpack, Jest.
- **Backend:** Spring Boot monolith `ix.portal.npg.NpgPortalApp`, JWT + `SecurityCache`, Liquibase, Oracle (prod/dev) / H2 (test).
- **Navigation:** Top navbar only (named outlet); entity routes at root; admin under `/admin`.
- **Auth:** Bearer JWT; CSRF disabled by design (documented).

## Baseline commands executed

See `docs/test-report.md` and `docs/final-validation-report.md`.
