# NPG consolidated branch — security + login

**Branch:** `cursor/npg-secure-login-complete-eec2`  
**Base:** `main`  
**Purpose:** Single delivery branch that includes the Angular/Spring upgrade, security hardening, empty-list fixes, and CAPTCHA/login UX.

## Included

### Security
- `JWTConfigurer` removed; `JWTFilter` via `addFilterBefore`
- Absolute navbar / entity routes
- Rate-limit floors (legacy SETTING=6 can no longer empty SPA lists with 429)
- CORS hardening; prod secrets fail-fast via env
- Permission checks tolerate `resource.name` / `resourceName`

### Login / CAPTCHA
- CAPTCHA enforced in default + interactive `localrun`
- Invalid CAPTCHA → HTTP 400 with `errorKey=captcha.validation`
- Bean validation on `captchaId` / `captchaToken`
- `LoginVM.toString()` never logs password
- Login submit stays clickable for field validation feedback
- Separate captcha vs login loading states

### Data / forms
- File Report blank criteria sanitized
- Entity table `TBL_FILE_REPORT_GENERATION_LOG`
- `createRequestOption` skips blank/invalid query values

## Local checkout

```powershell
git fetch origin
git checkout cursor/npg-secure-login-complete-eec2
git pull origin cursor/npg-secure-login-complete-eec2
```

## Ops still required for production
Docker/Oracle smoke, credential rotation, production CORS confirmation.
