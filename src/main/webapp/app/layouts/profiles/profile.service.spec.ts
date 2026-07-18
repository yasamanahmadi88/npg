import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProfileService } from './profile.service';

describe('ProfileService', () => {
  let service: ProfileService;
  let httpMock: HttpTestingController;
  let applicationConfigService: ApplicationConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting(), ProfileService, ApplicationConfigService],
    });
    service = TestBed.inject(ProfileService);
    httpMock = TestBed.inject(HttpTestingController);
    applicationConfigService = TestBed.inject(ApplicationConfigService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('splits display-ribbon-on-profiles when it is a string', () => {
    let ribbon: string | undefined;
    service.getProfileInfo().subscribe(info => (ribbon = info.ribbonEnv));

    const req = httpMock.expectOne(applicationConfigService.getEndpointFor('management/info'));
    req.flush({ activeProfiles: ['dev'], 'display-ribbon-on-profiles': 'dev,api-docs' });

    expect(ribbon).toBe('dev');
  });

  it('does not call split when display-ribbon-on-profiles is a non-string', () => {
    let ribbon: string | undefined = 'unset';
    let errored = false;
    service.getProfileInfo().subscribe({
      next: info => (ribbon = info.ribbonEnv),
      error: () => (errored = true),
    });

    const req = httpMock.expectOne(applicationConfigService.getEndpointFor('management/info'));
    req.flush({ activeProfiles: ['dev'], 'display-ribbon-on-profiles': ['dev'] });

    expect(errored).toBe(false);
    expect(ribbon).toBeUndefined();
  });

  it('returns empty profile info when management/info request fails', () => {
    let inProduction: boolean | undefined;
    service.getProfileInfo().subscribe(info => (inProduction = info.inProduction));

    const req = httpMock.expectOne(applicationConfigService.getEndpointFor('management/info'));
    req.flush({ message: 'unavailable' }, { status: 503, statusText: 'Service Unavailable' });

    expect(inProduction).toBe(false);
  });
});
