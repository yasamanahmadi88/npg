# Post-merge smoke test — after PR #5 lands on `main`

Run on a clean machine or clean worktree after merge. Use **non-production** credentials and databases only. Do **not** use Production secrets.

## 0. Confirm merge landed

```bash
git fetch origin
git checkout main
git pull origin main
git log -1 --oneline
# Confirm merge commit message / parents reference PR #5
```

## 1–6. Clean install and automated suites

```bash
# Toolchain (adjust paths if different)
export PATH="$HOME/node/node-v22.22.2-linux-x64/bin:$PATH"
export JAVA_HOME="${JAVA_HOME:-$HOME/jdk/jdk-25.0.3+9}"
export PATH="$JAVA_HOME/bin:$PATH"

# Clean dependencies
rm -rf node_modules
npm ci --no-fund --no-audit

# 3. Frontend lint
npm run lint

# 4. Frontend Jest
npx jest --config jest.conf.js --watch=false --coverage=false --runInBand

# 5. Frontend production build
npm run webapp:prod

# 6. Maven clean verify (LF-safe mvnw if CRLF shebang)
sed 's/\r$//' mvnw > /tmp/mvnw.lf && chmod +x /tmp/mvnw.lf
/tmp/mvnw.lf -ntp -P-webapp clean verify --batch-mode
```

**Expected (aligned with PR #5 tip):** Jest ~145/609, Maven 703, lint 0 errors, prod build success.

## 7. Application startup (safe test mode)

Prefer **dev** or a dedicated non-prod profile with H2 or non-prod Oracle — never Production.

```bash
# Example: local/dev (uses application-dev.yml defaults — not for Production)
export SPRING_PROFILES_ACTIVE=dev
/tmp/mvnw.lf -P-webapp spring-boot:run
# Or run the packaged jar with non-prod env vars only
```

If exercising **prod profile** against non-prod Oracle:

```bash
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL='jdbc:oracle:thin:@//NONPROD_HOST:1521/NONPROD_SERVICE'
export SPRING_DATASOURCE_USERNAME='...'
export SPRING_DATASOURCE_PASSWORD='...'
export JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET="$(openssl rand -base64 64)"
export JHIPSTER_CORS_ALLOWED_ORIGINS='https://your-nonprod-frontend.example'
```

## 8–17. Browser checks

Base URL example: `http://127.0.0.1:8080` (or static `serve -s` + mocked API only if backend unavailable — note the limitation).

| # | Check | Pass criteria |
| - | ----- | ------------- |
| 8 | Login | Valid non-prod user reaches authenticated shell |
| 9 | Refresh while authenticated | Session retained; not forced to login |
| 10 | Logout | Explicit logout clears session; protected routes blocked |
| 11 | Main menu navigation | Entities/admin items reach expected absolute routes |
| 12 | Nested route direct access | e.g. `/portability` loads |
| 13 | Browser refresh | Nested URL retained (SPA fallback) |
| 14 | Light theme | `data-theme=light` usable |
| 15 | Dark theme | Toggle + persist across refresh |
| 16 | Persian RTL | Language switch / `dir` behavior acceptable |
| 17 | Mobile Search | 320–430px: dashboard Search visible, not clipped |

Optional automated browser suite (mocked API mode):

```bash
npx playwright test --config=playwright.config.js
```

## 18–20. Security smoke

```bash
# 18. CORS preflight (replace hosts)
curl -i -X OPTIONS "http://127.0.0.1:8080/api/account" \
  -H "Origin: http://localhost:4200" \
  -H "Access-Control-Request-Method: GET"

# 19. Protected endpoint without token
curl -i "http://127.0.0.1:8080/api/account"
# Expect 401

# 20. Protected endpoint with valid token
# Obtain token via /api/authenticate with non-prod credentials, then:
curl -i "http://127.0.0.1:8080/api/account" \
  -H "Authorization: Bearer <NONPROD_JWT>"
# Expect 200 with account JSON
```

## Record

Capture command outputs, HTTP status codes, and screenshots for 8–17. File evidence outside the repo if it contains environment details. Do not commit secrets.
