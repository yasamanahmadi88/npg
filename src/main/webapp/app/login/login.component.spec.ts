import { ElementRef } from '@angular/core';
import { ComponentFixture, TestBed, fakeAsync, tick, waitForAsync } from '@angular/core/testing';
import { UntypedFormBuilder } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { AlertService } from 'app/core/util/alert.service';
import { LocalStorageService } from 'ngx-webstorage';

import { LoginService } from './login.service';
import { LoginComponent } from './login.component';

describe('Component Tests', () => {
  describe('LoginComponent', () => {
    let comp: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let httpMock: HttpTestingController;

    let mockRouter: {
      navigate: jest.Mock;
      getCurrentNavigation: jest.Mock;
    };

    let mockAccountService: {
      identity: jest.Mock;
      isAuthenticated: jest.Mock;
      getAuthenticationState: jest.Mock;
    };

    let mockLoginService: {
      login: jest.Mock;
    };

    beforeEach(
      waitForAsync(() => {
        mockRouter = {
          navigate: jest.fn(),
          getCurrentNavigation: jest.fn(() => null),
        };

        mockAccountService = {
          identity: jest.fn(() => of(null)),
          isAuthenticated: jest.fn(() => false),
          getAuthenticationState: jest.fn(() => of(null)),
        };

        mockLoginService = {
          login: jest.fn(() => of({})),
        };

        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          declarations: [LoginComponent],
          providers: [
            UntypedFormBuilder,
            { provide: AccountService, useValue: mockAccountService },
            { provide: Router, useValue: mockRouter },
            { provide: LoginService, useValue: mockLoginService },
            { provide: AlertService, useValue: {} },
            {
              provide: LocalStorageService,
              useValue: {
                retrieve: jest.fn(() => ''),
                store: jest.fn(),
                clear: jest.fn(),
                clearAll: jest.fn(),
              },
            },
          ],
        })
          .overrideTemplate(LoginComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(LoginComponent);
      comp = fixture.componentInstance;
      httpMock = TestBed.inject(HttpTestingController);
      mockRouter.navigate.mockClear();
      mockRouter.getCurrentNavigation.mockReturnValue(null);
      mockLoginService.login.mockReturnValue(of({}));
      mockAccountService.identity.mockReturnValue(of(null));
      mockAccountService.isAuthenticated.mockReturnValue(false);
    });

    afterEach(() => {
      httpMock.verify();
    });

    describe('ngOnInit', () => {
      it('Should call accountService.identity on Init', () => {
        comp.ngOnInit();
        expect(mockAccountService.identity).toHaveBeenCalled();
      });

      it('should navigate to dashboard on Init if authenticated=true', () => {
        mockAccountService.isAuthenticated.mockReturnValue(true);
        comp.ngOnInit();
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/dashboard']);
      });
    });

    describe('ngAfterViewInit', () => {
      it('should set focus to username input after the view has been initialized', fakeAsync(() => {
        const node = { focus: jest.fn() };
        comp.username = new ElementRef(node);
        comp.ngAfterViewInit();
        expect(node.focus).toHaveBeenCalled();
        tick();
        httpMock.expectOne('/api/captcha-endpoint').flush({
          captchaId: 'captcha-id',
          captchaImageUrl: '/api/captcha.png?cid=captcha-id',
        });
      }));
    });

    describe('login validation UX', () => {
      it('marks fields touched and does not call loginService when form is invalid', () => {
        comp.login();
        expect(comp.loginForm.touched).toBe(true);
        expect(mockLoginService.login).not.toHaveBeenCalled();
        expect(comp.loginSubmitting).toBe(false);
      });

      it('keeps submit flow available when form is invalid (no early busy lock)', () => {
        expect(comp.loginSubmitting).toBe(false);
        expect(comp.loginForm.invalid).toBe(true);
        comp.login();
        expect(comp.fieldInvalid('username')).toBe(true);
        expect(comp.fieldInvalid('password')).toBe(true);
        expect(comp.fieldInvalid('userCaptchaInput')).toBe(true);
      });
    });

    describe('login', () => {
      it('should authenticate the user and navigate to dashboard', () => {
        comp.loginForm.patchValue({
          username: 'admin',
          password: 'admin',
          rememberMe: true,
          userCaptchaInput: 'ABC123',
        });
        comp.captchaId = 'captcha-id';

        comp.login();

        expect(comp.authenticationError).toEqual(false);
        expect(mockLoginService.login).toHaveBeenCalledWith({
          username: 'admin',
          password: 'admin',
          rememberMe: true,
          captchaId: 'captcha-id',
          captchaToken: 'ABC123',
        });
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/dashboard']);
        expect(comp.loginSubmitting).toEqual(false);
      });

      it('should show captcha error when API returns captcha.validation', () => {
        mockLoginService.login.mockReturnValue(
          throwError(() => ({
            status: 400,
            error: { errorKey: 'captcha.validation', message: 'error.captcha.validation' },
          }))
        );
        comp.loginForm.patchValue({ username: 'admin', password: 'admin', rememberMe: true, userCaptchaInput: 'ABC123' });
        comp.captchaId = 'captcha-id';

        comp.login();
        httpMock.expectOne('/api/captcha-endpoint').flush({
          captchaId: 'new-captcha-id',
          captchaImageUrl: '/api/captcha.png?cid=new-captcha-id',
        });

        expect(comp.wrongCaptcha).toEqual(true);
        expect(comp.authenticationError).toEqual(false);
        expect(comp.loginSubmitting).toEqual(false);
      });

      it('should stay on login form and show error message on login error', () => {
        mockLoginService.login.mockReturnValue(
          throwError(() => ({
            status: 401,
            error: {},
          }))
        );
        comp.loginForm.patchValue({ username: 'admin', password: 'admin', rememberMe: true, userCaptchaInput: 'ABC123' });
        comp.captchaId = 'captcha-id';

        comp.login();
        httpMock.expectOne('/api/captcha-endpoint').flush({
          captchaId: 'new-captcha-id',
          captchaImageUrl: '/api/captcha.png?cid=new-captcha-id',
        });

        expect(comp.authenticationError).toEqual(true);
        expect(comp.loginSubmitting).toEqual(false);
        expect(mockRouter.navigate).not.toHaveBeenCalled();
      });

      it('separates captchaLoading from loginSubmitting', () => {
        comp.loadCaptcha();
        expect(comp.captchaLoading).toBe(true);
        expect(comp.loginSubmitting).toBe(false);
        httpMock.expectOne('/api/captcha-endpoint').flush({
          captchaId: 'cid',
          captchaImageUrl: '/api/captcha.png?cid=cid',
        });
        expect(comp.captchaLoading).toBe(false);
        expect(comp.captchaId).toBe('cid');
      });
    });
  });
});
