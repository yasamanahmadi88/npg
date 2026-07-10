import { ElementRef } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
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

    describe('ngOnInit', () => {
      it('Should call accountService.identity on Init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(mockAccountService.identity).toHaveBeenCalled();
      });

      it('Should call accountService.isAuthenticated on Init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(mockAccountService.isAuthenticated).toHaveBeenCalled();
      });

      it('should navigate to dashboard on Init if authenticated=true', () => {
        // GIVEN
        mockAccountService.isAuthenticated.mockReturnValue(true);

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/dashboard']);
      });
    });

    describe('ngAfterViewInit', () => {
      it('should set focus to username input after the view has been initialized', () => {
        // GIVEN
        const node = {
          focus: jest.fn(),
        };
        comp.username = new ElementRef(node);

        // WHEN
        comp.ngAfterViewInit();

        // THEN
        expect(node.focus).toHaveBeenCalled();
      });
    });

    describe('login', () => {
      it('should authenticate the user and navigate to dashboard', () => {
        // GIVEN
        comp.loginForm.patchValue({
          username: 'admin',
          password: 'admin',
          rememberMe: true,
          userCaptchaInput: 'ABC123',
        });
        comp.captchaId = 'captcha-id';

        // WHEN
        comp.login();

        // THEN
        expect(comp.authenticationError).toEqual(false);
        expect(mockLoginService.login).toHaveBeenCalledWith({
          username: 'admin',
          password: 'admin',
          rememberMe: true,
          captchaId: 'captcha-id',
          captchaToken: 'ABC123',
        });
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/dashboard']);
        expect(comp.loading).toEqual(false);
      });

      it('should authenticate the user but not navigate to dashboard if authentication process is already routing to cached url', () => {
        // GIVEN
        mockRouter.getCurrentNavigation.mockReturnValue({} as never);
        comp.loginForm.patchValue({ username: 'admin', password: 'admin', rememberMe: true, userCaptchaInput: 'ABC123' });
        comp.captchaId = 'captcha-id';

        // WHEN
        comp.login();

        // THEN
        expect(comp.authenticationError).toEqual(false);
        expect(mockRouter.navigate).not.toHaveBeenCalled();
      });

      it('should stay on login form and show error message on login error', () => {
        // GIVEN
        mockLoginService.login.mockReturnValue(
          throwError(() => ({
            status: 401,
            error: {},
          }))
        );
        comp.loginForm.patchValue({ username: 'admin', password: 'admin', rememberMe: true, userCaptchaInput: 'ABC123' });
        comp.captchaId = 'captcha-id';

        // WHEN
        comp.login();
        httpMock.expectOne('/api/captcha-endpoint').flush({ captchaId: 'new-captcha-id', captchaImageUrl: '/api/captcha.png?cid=new-captcha-id' });

        // THEN
        expect(comp.authenticationError).toEqual(true);
        expect(comp.loading).toEqual(false);
        expect(mockRouter.navigate).not.toHaveBeenCalled();
      });
    });
  });
});