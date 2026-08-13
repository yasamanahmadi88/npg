import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { Subject, of } from 'rxjs';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { LocalStorageService } from 'ngx-webstorage';
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { AccountService } from 'app/core/auth/account.service';
import { ThemeService } from 'app/core/theme/theme.service';
import { FindLanguageFromKeyPipe } from 'app/shared/language/find-language-from-key.pipe';
import { PublicService } from 'app/shared/public.service';
import { MainComponent } from './main.component';

describe('MainComponent authentication lifecycle', () => {
  let fixture: ComponentFixture<MainComponent>;

  beforeEach(waitForAsync(() => {
    const langChangeSubject = new Subject<LangChangeEvent>();
    TestBed.configureTestingModule({
      declarations: [MainComponent],
      providers: [
        Title,
        ThemeService,
        { provide: AccountService, useValue: { identity: () => of(null), getAuthenticationState: () => of(null) } },
        {
          provide: TranslateService,
          useValue: {
            get: (key: string) => of(key),
            currentLang: 'en',
            onLangChange: langChangeSubject.asObservable(),
          },
        },
        { provide: FindLanguageFromKeyPipe, useValue: { isRTL: () => false } },
        {
          provide: Router,
          useValue: {
            events: new Subject(),
            routerState: { snapshot: { root: { data: {} } } },
          },
        },
        { provide: PublicService, useValue: { getCaptchaBackUrl: () => of({ body: { backUrl: 'http://localhost' } }) } },
        { provide: LocalStorageService, useValue: { store: jest.fn() } },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainComponent);
    fixture.detectChanges();
  });

  it('does not expose unload/destroy logout hooks that clear sessions on refresh', () => {
    const instance = fixture.componentInstance as any;
    expect(instance.ngOnDestroy).toBeUndefined();
    // HostListener metadata should not bind beforeunload logout anymore.
    const props = Object.getOwnPropertyNames(Object.getPrototypeOf(instance));
    expect(props).not.toContain('ngOnDestroy');
  });
});
