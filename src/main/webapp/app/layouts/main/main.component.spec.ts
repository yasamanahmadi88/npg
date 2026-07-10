import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, RouterEvent, NavigationEnd, NavigationStart } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { HttpResponse } from '@angular/common/http';
import { Subject, of } from 'rxjs';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { LocalStorageService } from 'ngx-webstorage';

import { AccountService } from 'app/core/auth/account.service';
import { FindLanguageFromKeyPipe } from 'app/shared/language/find-language-from-key.pipe';
import { LoginService } from 'app/login/login.service';
import { PublicService } from 'app/shared/public.service';

import { MainComponent } from './main.component';

describe('Component Tests', () => {
  describe('MainComponent', () => {
    let comp: MainComponent;
    let fixture: ComponentFixture<MainComponent>;
    let titleService: Title;
    let translateService: TranslateService;
    let findLanguageFromKeyPipe: FindLanguageFromKeyPipe;

    let routerEventsSubject: Subject<RouterEvent>;
    let langChangeSubject: Subject<LangChangeEvent>;
    let routerState: any;
    let currentLang: string;

    let mockAccountService: {
      identity: jest.Mock;
      getAuthenticationState: jest.Mock;
    };

    let mockTranslateService: {
      get: jest.Mock;
    };

    let mockLoginService: {
      logout: jest.Mock;
    };

    let mockPublicService: {
      getCaptchaBackUrl: jest.Mock;
    };

    let mockLocalStorageService: {
      store: jest.Mock;
    };

    beforeEach(
      waitForAsync(() => {
        routerEventsSubject = new Subject<RouterEvent>();
        langChangeSubject = new Subject<LangChangeEvent>();
        routerState = { snapshot: { root: { data: {} } } };
        currentLang = 'en';

        mockAccountService = {
          identity: jest.fn(() => of(null)),
          getAuthenticationState: jest.fn(() => of(null)),
        };

        mockTranslateService = {
          get: jest.fn((key: string | string[]) => of(`${key as string} translated`)),
        };

        Object.defineProperty(mockTranslateService, 'currentLang', {
          get: () => currentLang,
          configurable: true,
        });

        Object.defineProperty(mockTranslateService, 'onLangChange', {
          get: () => langChangeSubject.asObservable(),
          configurable: true,
        });

        mockLoginService = {
          logout: jest.fn(),
        };

        mockPublicService = {
          getCaptchaBackUrl: jest.fn(() => of(new HttpResponse({ body: { backUrl: 'http://localhost' } }))),
        };

        mockLocalStorageService = {
          store: jest.fn(),
        };

        TestBed.configureTestingModule({
          declarations: [MainComponent],
          providers: [
            Title,
            FindLanguageFromKeyPipe,
            { provide: AccountService, useValue: mockAccountService },
            {
              provide: Router,
              useValue: {
                events: routerEventsSubject.asObservable(),
                routerState,
              },
            },
            { provide: TranslateService, useValue: mockTranslateService },
            { provide: LoginService, useValue: mockLoginService },
            { provide: PublicService, useValue: mockPublicService },
            { provide: LocalStorageService, useValue: mockLocalStorageService },
          ],
        })
          .overrideTemplate(MainComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(MainComponent);
      comp = fixture.componentInstance;
      titleService = TestBed.inject(Title);
      translateService = TestBed.inject(TranslateService);
      findLanguageFromKeyPipe = TestBed.inject(FindLanguageFromKeyPipe);
    });

    describe('page title', () => {
      const defaultPageTitle = 'global.title';
      const parentRoutePageTitle = 'parentTitle';
      const childRoutePageTitle = 'childTitle';
      const navigationEnd = new NavigationEnd(1, '', '');
      const navigationStart = new NavigationStart(1, '');
      const langChangeEvent: LangChangeEvent = { lang: 'en', translations: {} };

      beforeEach(() => {
        routerState.snapshot.root = { data: {} };
        jest.spyOn(titleService, 'setTitle');
        comp.ngOnInit();
      });

      describe('navigation end', () => {
        it('should set page title to default title if pageTitle is missing on routes', () => {
          routerEventsSubject.next(navigationEnd);

          expect(translateService.get).toHaveBeenCalledWith(defaultPageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(defaultPageTitle + ' translated');
        });

        it('should set page title to root route pageTitle if there is no child routes', () => {
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };

          routerEventsSubject.next(navigationEnd);

          expect(translateService.get).toHaveBeenCalledWith(parentRoutePageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle + ' translated');
        });

        it('should set page title to child route pageTitle if child routes exist and pageTitle is set for child route', () => {
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = { data: { pageTitle: childRoutePageTitle } };

          routerEventsSubject.next(navigationEnd);

          expect(translateService.get).toHaveBeenCalledWith(childRoutePageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(childRoutePageTitle + ' translated');
        });

        it('should set page title to parent route pageTitle if child routes exists but pageTitle is not set for child route data', () => {
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = { data: {} };

          routerEventsSubject.next(navigationEnd);

          expect(translateService.get).toHaveBeenCalledWith(parentRoutePageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle + ' translated');
        });
      });

      describe('navigation start', () => {
        it('should not set page title on navigation start', () => {
          routerEventsSubject.next(navigationStart);

          expect(titleService.setTitle).not.toHaveBeenCalled();
        });
      });

      describe('language change', () => {
        it('should set page title to default title if pageTitle is missing on routes', () => {
          langChangeSubject.next(langChangeEvent);

          expect(translateService.get).toHaveBeenCalledWith(defaultPageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(defaultPageTitle + ' translated');
        });

        it('should set page title to root route pageTitle if there is no child routes', () => {
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };

          langChangeSubject.next(langChangeEvent);

          expect(translateService.get).toHaveBeenCalledWith(parentRoutePageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle + ' translated');
        });

        it('should set page title to child route pageTitle if child routes exist and pageTitle is set for child route', () => {
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = { data: { pageTitle: childRoutePageTitle } };

          langChangeSubject.next(langChangeEvent);

          expect(translateService.get).toHaveBeenCalledWith(childRoutePageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(childRoutePageTitle + ' translated');
        });

        it('should set page title to parent route pageTitle if child routes exists but pageTitle is not set for child route data', () => {
          routerState.snapshot.root.data = { pageTitle: parentRoutePageTitle };
          routerState.snapshot.root.firstChild = { data: {} };

          langChangeSubject.next(langChangeEvent);

          expect(translateService.get).toHaveBeenCalledWith(parentRoutePageTitle);
          expect(titleService.setTitle).toHaveBeenCalledWith(parentRoutePageTitle + ' translated');
        });
      });
    });

    describe('page language attribute', () => {
      it('should change page language attribute on language change', () => {
        comp.ngOnInit();

        findLanguageFromKeyPipe.isRTL = jest.fn(() => false);
        currentLang = 'lang1';
        langChangeSubject.next({ lang: 'lang1', translations: {} });

        expect(document.querySelector('html')?.getAttribute('lang')).toEqual('lang1');
        expect(document.querySelector('html')?.getAttribute('dir')).toEqual('ltr');

        findLanguageFromKeyPipe.isRTL = jest.fn(() => true);
        currentLang = 'lang2';
        langChangeSubject.next({ lang: 'lang2', translations: {} });

        expect(document.querySelector('html')?.getAttribute('lang')).toEqual('lang2');
        expect(document.querySelector('html')?.getAttribute('dir')).toEqual('rtl');
      });
    });
  });
});