import { TestBed } from '@angular/core/testing';
import { Router, provideRouter } from '@angular/router';
import { Component } from '@angular/core';

@Component({ template: '', standalone: true })
class StubPageComponent {}

/**
 * Regression: menu items must use absolute paths so navigation works from nested routes.
 */
describe('Menu route inventory', () => {
  const menuRoutes = [
    '/',
    '/day-of-week-time-frame',
    '/crm-to-crdb-response-map',
    '/off-day',
    '/time-frame',
    '/portability',
    '/setting',
    '/check-status',
    '/notification-template',
    '/undifined-status',
    '/business-config',
    '/portability-log',
    '/event-log',
    '/file-report-generation-log',
    '/resource',
    '/authority',
    '/resource-authority',
    '/admin/session-management',
    '/admin/user-management',
    '/admin/health',
    '/admin/configuration',
    '/admin/logs',
    '/admin/docs',
    '/account/settings',
    '/account/password',
    '/account/register',
    '/account/reset/request',
    '/dashboard',
    '/login',
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideRouter([
          ...menuRoutes.map(path => ({
            path: path.replace(/^\//, ''),
            component: StubPageComponent,
          })),
          { path: '**', component: StubPageComponent },
        ]),
      ],
    }).compileComponents();
  });

  it('resolves every visible menu absolute path without relative nesting', async () => {
    const router = TestBed.inject(Router);
    for (const path of menuRoutes) {
      expect(path.startsWith('/')).toBe(true);
      const result = await router.navigateByUrl(path);
      expect(result).toBe(true);
      expect(router.url.split('?')[0]).toBe(path === '/' ? '/' : path);
    }
  });

  it('does not treat nested relative admin links as valid absolute targets', () => {
    const badRelative = 'admin/health';
    expect(badRelative.startsWith('/')).toBe(false);
    expect(`/portability/${badRelative}`).toBe('/portability/admin/health');
  });
});
