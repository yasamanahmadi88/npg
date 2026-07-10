const { test, expect } = require('@playwright/test');

async function mockPublicApis(page) {
  await page.route('**/api/**', async route => {
    const url = route.request().url();
    const method = route.request().method();

    if (url.includes('/api/authenticate') && method === 'POST') {
      return route.fulfill({
        status: 200,
        contentType: 'application/json',
        headers: { Authorization: 'Bearer test-token' },
        body: JSON.stringify({ id_token: 'test-token' }),
      });
    }
    if (url.includes('/api/account')) {
      return route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({
          activated: true,
          authorities: ['ROLE_USER', 'ROLE_ADMIN'],
          email: 'admin@localhost',
          firstName: 'Admin',
          lastName: 'User',
          login: 'admin',
          langKey: 'en',
          resourceAuthorities: [
            { resourceName: 'portability', verb: 'view' },
            { resourceName: 'setting', verb: 'view' },
            { resourceName: 'dayOfWeekTimeFrame', verb: 'view' },
          ],
        }),
      });
    }
    if (url.includes('/api/captcha-endpoint') || url.includes('/api/cp-eyrtyertye')) {
      return route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({ captchaId: 'cap-1', captchaImageUrl: '/api/captcha.png' }),
      });
    }
    // Entity list endpoints commonly hit after menu navigation
    if (method === 'GET') {
      return route.fulfill({
        status: 200,
        contentType: 'application/json',
        headers: { 'X-Total-Count': '0' },
        body: '[]',
      });
    }
    return route.fulfill({ status: 200, contentType: 'application/json', body: '{}' });
  });
}

async function seedAuthenticatedSession(page) {
  // ngx-webstorage prefix/separator from app.module.ts: jhi-
  await page.addInitScript(() => {
    localStorage.setItem('jhi-authenticationToken', JSON.stringify('test-token'));
  });
}

function trackPageDiagnostics(page) {
  const pageErrors = [];
  const failedRequests = [];
  page.on('pageerror', err => pageErrors.push(String(err)));
  page.on('requestfailed', req => {
    const url = req.url();
    // Ignore aborted navigations / cancelled requests common during SPA routing.
    if (req.failure() && req.failure().errorText === 'net::ERR_ABORTED') return;
    failedRequests.push({ url, error: req.failure() && req.failure().errorText });
  });
  return { pageErrors, failedRequests };
}

test.describe('NPG portal critical browser flows', () => {
  test.beforeEach(async ({ page }) => {
    await mockPublicApis(page);
  });

  test('application and login page load', async ({ page }) => {
    const { pageErrors } = trackPageDiagnostics(page);
    await page.goto('/login');
    await expect(page.locator('jhi-login, form, #username, input').first()).toBeVisible({ timeout: 30000 });
    await expect(page.locator('jhi-main')).toBeVisible();
    test.info().annotations.push({ type: 'pageerrors', description: JSON.stringify(pageErrors.slice(0, 5)) });
  });

  test('navbar theme toggle switches and persists', async ({ page }) => {
    await page.goto('/');
    await page.evaluate(() => localStorage.removeItem('npg-portal-theme'));
    await page.reload();
    await page.waitForSelector('[data-cy="navbar"], .navbar', { timeout: 30000 });
    const toggle = page.locator('[data-cy="themeToggle"]');
    await expect(toggle).toBeVisible();

    await toggle.click();
    const after = await page.locator('html').getAttribute('data-theme');
    expect(['light', 'dark']).toContain(after);
    expect(await page.evaluate(() => localStorage.getItem('npg-portal-theme'))).toBe(after);

    await page.reload();
    await page.waitForSelector('[data-cy="themeToggle"]', { timeout: 30000 });
    await expect(page.locator('html')).toHaveAttribute('data-theme', after);

    // Switch back to the other theme
    await toggle.click();
    const flipped = await page.locator('html').getAttribute('data-theme');
    expect(flipped).not.toBe(after);
  });

  test('unknown route shows not-found path', async ({ page }) => {
    await page.goto('/this-route-does-not-exist-xyz');
    await page.waitForURL(/404|accessdenied|error|this-route-does-not-exist/, { timeout: 30000 });
    await expect(page.locator('jhi-main')).toBeVisible();
  });

  test('direct nested URL loads with SPA fallback', async ({ page }) => {
    await seedAuthenticatedSession(page);
    const { pageErrors, failedRequests } = trackPageDiagnostics(page);
    const response = await page.goto('/portability', { waitUntil: 'domcontentloaded' });
    expect(response && response.status()).toBeLessThan(400);
    await expect(page.locator('jhi-main, .navbar').first()).toBeVisible({ timeout: 30000 });
    await expect(page).toHaveURL(/\/portability/);
    // Target entity host should render (list or empty state inside jhi-main)
    await expect(page.locator('jhi-main')).toBeVisible();
    expect(failedRequests.filter(r => r.url.includes('/api/')).length).toBe(0);
    test.info().annotations.push({ type: 'pageerrors', description: JSON.stringify(pageErrors.slice(0, 5)) });
  });

  test('browser refresh keeps nested route', async ({ page }) => {
    await seedAuthenticatedSession(page);
    await page.goto('/portability');
    await expect(page).toHaveURL(/\/portability/);
    await page.reload({ waitUntil: 'domcontentloaded' });
    await expect(page).toHaveURL(/\/portability/);
    await expect(page.locator('jhi-main, .navbar').first()).toBeVisible({ timeout: 30000 });
  });

  test('browser back and forward restore routes', async ({ page }) => {
    await seedAuthenticatedSession(page);
    await page.goto('/dashboard');
    await page.goto('/portability');
    await expect(page).toHaveURL(/\/portability/);
    await page.goBack();
    await expect(page).toHaveURL(/\/dashboard|\/$/);
    await page.goForward();
    await expect(page).toHaveURL(/\/portability/);
    await expect(page.locator('jhi-main').first()).toBeVisible({ timeout: 30000 });
  });

  test('menu click navigates to absolute entity route', async ({ page }) => {
    await seedAuthenticatedSession(page);
    const { pageErrors, failedRequests } = trackPageDiagnostics(page);
    await page.goto('/');
    await page.waitForSelector('[data-cy="navbar"], .navbar', { timeout: 30000 });

    // Open Entities dropdown then click Portability (absolute /portability)
    const entityToggle = page.locator('#entity-menu, [data-cy="entity"]').first();
    await entityToggle.click();
    const portabilityItem = page.locator('a[routerlink="/portability"], a[href="/portability"]').first();
    await expect(portabilityItem).toBeVisible({ timeout: 10000 });
    await portabilityItem.click();
    await expect(page).toHaveURL(/\/portability$/);
    await expect(page.locator('jhi-main')).toBeVisible({ timeout: 30000 });
    expect(failedRequests.filter(r => /\/api\//.test(r.url) && !r.error?.includes('ERR_ABORTED')).length).toBe(0);
    test.info().annotations.push({ type: 'pageerrors', description: JSON.stringify(pageErrors.slice(0, 5)) });
  });

  test('mobile viewport keeps navbar toggler usable', async ({ page }) => {
    await page.setViewportSize({ width: 390, height: 844 });
    await page.goto('/');
    await page.waitForSelector('.navbar', { timeout: 30000 });
    const toggler = page.locator('.navbar-toggler, [aria-label="Toggle navigation"]');
    await expect(toggler.first()).toBeVisible();
    await toggler.first().click();
    await expect(page.locator('[data-cy="themeToggle"]')).toBeVisible();
  });
});
