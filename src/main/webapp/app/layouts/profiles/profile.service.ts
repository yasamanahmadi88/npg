import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, shareReplay } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProfileInfo, InfoResponse } from './profile-info.model';

@Injectable({ providedIn: 'root' })
export class ProfileService {
  private infoUrl = this.applicationConfigService.getEndpointFor('management/info');
  private profileInfo$?: Observable<ProfileInfo>;

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getProfileInfo(): Observable<ProfileInfo> {
    if (this.profileInfo$) {
      return this.profileInfo$;
    }

    this.profileInfo$ = this.http.get<InfoResponse>(this.infoUrl).pipe(
      map((response: InfoResponse) => {
        const profileInfo: ProfileInfo = {
          activeProfiles: response.activeProfiles,
          inProduction: response.activeProfiles?.includes('prod'),
          openAPIEnabled: response.activeProfiles?.includes('api-docs'),
        };
        // Contract: display-ribbon-on-profiles is a comma-separated string (see application.yml info.*).
        const ribbonOnProfiles = response['display-ribbon-on-profiles'];
        if (response.activeProfiles && typeof ribbonOnProfiles === 'string' && ribbonOnProfiles.length > 0) {
          const displayRibbonOnProfiles = ribbonOnProfiles.split(',');
          const ribbonProfiles = displayRibbonOnProfiles.filter(profile => response.activeProfiles?.includes(profile));
          if (ribbonProfiles.length > 0) {
            profileInfo.ribbonEnv = ribbonProfiles[0];
          }
        }
        return profileInfo;
      }),
      catchError(() =>
        of({
          activeProfiles: [],
          inProduction: false,
          openAPIEnabled: false,
        } as ProfileInfo)
      ),
      shareReplay()
    );
    return this.profileInfo$;
  }
}
