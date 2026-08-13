import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';

import { NavbarComponent } from './navbar.component';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { ThemeService } from 'app/core/theme/theme.service';

describe('NavbarComponent theme toggle', () => {
  let fixture: ComponentFixture<NavbarComponent>;
  let themeService: ThemeService;

  beforeEach(async () => {
    localStorage.clear();
    await TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot(), HttpClientTestingModule],
      declarations: [NavbarComponent],
      providers: [
        ThemeService,
        { provide: LoginService, useValue: { logout: jest.fn() } },
        { provide: AccountService, useValue: { getAuthenticationState: () => of(null), isAuthenticated: () => false } },
        { provide: ProfileService, useValue: { getProfileInfo: () => of({ inProduction: false, openAPIEnabled: false }) } },
        { provide: Router, useValue: { navigate: jest.fn() } },
        { provide: SessionStorageService, useValue: { store: jest.fn(), retrieve: jest.fn() } },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    themeService = TestBed.inject(ThemeService);
    fixture = TestBed.createComponent(NavbarComponent);
    fixture.detectChanges();
  });

  it('renders an accessible theme toggle', () => {
    const button: HTMLButtonElement = fixture.nativeElement.querySelector('[data-cy="themeToggle"]');
    expect(button).toBeTruthy();
    expect(button.getAttribute('aria-label')).toContain('theme');
    expect(button.getAttribute('aria-pressed')).toBe('false');
  });

  it('toggles theme when the navbar button is clicked', () => {
    const button: HTMLButtonElement = fixture.nativeElement.querySelector('[data-cy="themeToggle"]');
    button.click();
    fixture.detectChanges();
    expect(themeService.currentTheme).toBe('dark');
    expect(button.getAttribute('aria-pressed')).toBe('true');
    expect(localStorage.getItem('npg-portal-theme')).toBe('dark');
  });
});
