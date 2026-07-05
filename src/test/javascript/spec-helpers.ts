import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import {
  LocalStorageService,
  provideNgxWebstorage,
  SessionStorageService,
  withLocalStorage,
  withNgxWebstorageConfig,
  withSessionStorage,
} from 'ngx-webstorage';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

export const specTranslateImports = () => [TranslateModule.forRoot()];

export const specStorageProviders = () => [
  provideNgxWebstorage(
    withNgxWebstorageConfig({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    withLocalStorage(),
    withSessionStorage()
  ),
];

export const specCommonImports = () => [...specTranslateImports()];

export const mockNgbActiveModalProvider = () => ({
  provide: NgbActiveModal,
  useValue: {
    dismiss: jest.fn(),
    close: jest.fn(),
  },
});

export const mockApplicationConfigServiceProvider = () => ({
  provide: ApplicationConfigService,
  useValue: {
    getEndpointFor: (path: string) => path,
  },
});

export const mockSessionStorageProvider = () => ({
  provide: SessionStorageService,
  useValue: {
    retrieve: jest.fn(),
    store: jest.fn(),
    clear: jest.fn(),
  },
});

export const mockLocalStorageProvider = () => ({
  provide: LocalStorageService,
  useValue: {
    retrieve: jest.fn(),
    store: jest.fn(),
    clear: jest.fn(),
  },
});
