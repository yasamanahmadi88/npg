import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';

import { AccountService } from './account.service';
import { StateStorageService } from './state-storage.service';
import { UserRouteAccessService } from './user-route-access.service';
import { Authority } from 'app/config/authority.constants';

describe('UserRouteAccessService', () => {
  let service: UserRouteAccessService;
  let accountService: jest.Mocked<AccountService>;
  let router: jest.Mocked<Router>;
  let stateStorageService: jest.Mocked<StateStorageService>;

  beforeEach(() => {
    accountService = {
      identity: jest.fn(),
      hasAnyAuthority: jest.fn(),
    } as unknown as jest.Mocked<AccountService>;
    router = { navigate: jest.fn() } as unknown as jest.Mocked<Router>;
    stateStorageService = { storeUrl: jest.fn() } as unknown as jest.Mocked<StateStorageService>;

    TestBed.configureTestingModule({
      providers: [
        UserRouteAccessService,
        { provide: AccountService, useValue: accountService },
        { provide: Router, useValue: router },
        { provide: StateStorageService, useValue: stateStorageService },
      ],
    });

    service = TestBed.inject(UserRouteAccessService);
  });

  it('should allow authenticated users when no authorities are required', done => {
    accountService.identity.mockReturnValue(of({ login: 'user' } as any));

    service.canActivate({ data: {} } as any, { url: '/entities/portability' } as any).subscribe(result => {
      expect(result).toBe(true);
      done();
    });
  });

  it('should deny users without required authorities', done => {
    accountService.identity.mockReturnValue(of({ login: 'user' } as any));
    accountService.hasAnyAuthority.mockReturnValue(false);

    service.canActivate({ data: { authorities: [Authority.ADMIN] } } as any, { url: '/admin' } as any).subscribe(result => {
      expect(result).toBe(false);
      expect(router.navigate).toHaveBeenCalledWith(['accessdenied']);
      done();
    });
  });

  it('should redirect unauthenticated users to login', done => {
    accountService.identity.mockReturnValue(of(null));

    service.canActivate({ data: {} } as any, { url: '/entities/portability' } as any).subscribe(result => {
      expect(result).toBe(false);
      expect(stateStorageService.storeUrl).toHaveBeenCalledWith('/entities/portability');
      expect(router.navigate).toHaveBeenCalledWith(['/login']);
      done();
    });
  });
});
