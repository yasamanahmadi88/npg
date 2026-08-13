import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { setupZoneTestEnv } from 'jest-preset-angular/setup-env/zone';
import { TranslateModule } from '@ngx-translate/core';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

setupZoneTestEnv();

type SpyTarget = Record<string, unknown>;

(globalThis as any).spyOn = (target: SpyTarget, methodName: string) => {
  const spy = jest.spyOn(target as any, methodName as never);

  (spy as any).and = {
    returnValue: (value: unknown) => {
      spy.mockReturnValue(value as never);
      return spy;
    },
    callFake: (fn: (...args: unknown[]) => unknown) => {
      spy.mockImplementation(fn as never);
      return spy;
    },
    throwError: (error: unknown) => {
      spy.mockImplementation(() => {
        throw error;
      });
      return spy;
    },
  };

  return spy;
};

const storageMock = {
  retrieve: jest.fn(),
  store: jest.fn(),
  clear: jest.fn(),
  clearAll: jest.fn(),
  observe: jest.fn(() => of(null)),
  isStorageAvailable: jest.fn(() => true),
};

const activeModalMock = {
  close: jest.fn(),
  dismiss: jest.fn(),
};

beforeEach(() => {
  TestBed.configureTestingModule({
    imports: [TranslateModule.forRoot()],
    providers: [
      { provide: LocalStorageService, useValue: storageMock },
      { provide: SessionStorageService, useValue: storageMock },
      { provide: NgbActiveModal, useValue: activeModalMock },
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  });
});