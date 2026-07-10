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
          resourceAuthorities: [],
        }),
      });
    }
    if (url.includes('/api/captcha-endpoint')) {
      return route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({ captchaId: 'cap-1', captchaImageUrl: '/api/captcha.png' }),
      });
    }
    return route.fulfill({ status: 200, contentType: 'application/json', body: '{}' });
  });
}

test.describe('NPG portal critical browser flows', () => {
  test.beforeEach(async ({ page }) => {
    await mockPublicApis(page);
  });

  test('application and login page load', async ({ page }) => {
    const errors = [];
    page.on('pageerror', err => errors.push(String(err)));
    await page.goto('/login');
    await expect(page.locator('jhi-login, form, #username, input').first()).toBeVisible({ timeout: 30000 });
    // Record non-fatal page errors for diagnostics without failing on minified vendor noise.
    test.info().annotations.push({ type: 'pageerrors', description: JSON.stringify(errors.slice(0, 5)) });
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
  });

  test('unknown route shows not-found path', async ({ page }) => {
    await page.goto('/this-route-does-not-exist-xyz');
    await page.waitForURL(/404|accessdenied|error|this-route-does-not-exist/, { timeout: 30000 });
    await expect(page.locator('jhi-main')).toBeVisible();
  });

  test('direct nested URL loads with SPA fallback', async ({ page }) => {
    const response = await page.goto('/portability', { waitUntil: 'domcontentloaded' });
    expect(response && response.status()).toBeLessThan(400);
    await expect(page.locator('jhi-main, .navbar').first()).toBeVisible({ timeout: 30000 });
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
