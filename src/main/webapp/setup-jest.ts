import { setupZoneTestEnv } from 'jest-preset-angular/setup-env/zone';
import { TestBed, TestModuleMetadata } from '@angular/core/testing';
import { TranslateLoader, provideMissingTranslationHandler, provideTranslateService } from '@ngx-translate/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import {
  provideNgxWebstorage,
  withLocalStorage,
  withNgxWebstorageConfig,
  withSessionStorage,
} from 'ngx-webstorage';
import { MissingTranslationHandlerImpl } from 'app/config/translation.config';

setupZoneTestEnv();

(globalThis as any).spyOn = (object: object, method: string) => {
  const spy = jest.spyOn(object as never, method as never);
  return Object.assign(spy, {
    and: {
      returnValue: (value: unknown) => spy.mockReturnValue(value as never),
      callFake: (fn: (...args: unknown[]) => unknown) => spy.mockImplementation(fn as never),
      throwError: (error: unknown) =>
        spy.mockImplementation(() => {
          throw error;
        }),
    },
  });
};

class TranslateFakeLoader implements TranslateLoader {
  getTranslation(_lang: string) {
    return of({});
  }
}

const commonProviders = [
  provideTranslateService({
    loader: { provide: TranslateLoader, useClass: TranslateFakeLoader },
    missingTranslationHandler: provideMissingTranslationHandler(MissingTranslationHandlerImpl),
    fallbackLang: 'fa',
    lang: 'fa',
  }),
  provideNgxWebstorage(
    withNgxWebstorageConfig({
      prefix: 'jhi',
      separator: '-',
      caseSensitive: false,
    }),
    withLocalStorage(),
    withSessionStorage()
  ),
  {
    provide: NgbActiveModal,
    useValue: {
      close: jest.fn(),
      dismiss: jest.fn(),
    },
  },
];

const originalConfigureTestingModule = TestBed.configureTestingModule.bind(TestBed);

TestBed.configureTestingModule = (moduleDef: TestModuleMetadata) => {
  return originalConfigureTestingModule({
    ...moduleDef,
    providers: [...commonProviders, ...(moduleDef.providers ?? [])],
  });
};
