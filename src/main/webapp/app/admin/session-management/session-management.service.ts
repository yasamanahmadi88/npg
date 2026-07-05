import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { ISessionInfo } from './session-info.model';

@Injectable({
  providedIn: 'root',
})
export class SessionManagementService {
  private resourceSessionUrl = this.applicationConfigService.getEndpointFor('api/admin/sessions');
  private resourceSessionRemoveUrl = this.applicationConfigService.getEndpointFor('api/admin/sessions/remove/');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  fetchSessions(): Observable<HttpResponse<ISessionInfo[]>> {
    return this.http.get<ISessionInfo[]>(this.resourceSessionUrl, { observe: 'response' });
  }

  removeSession(token: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(this.resourceSessionRemoveUrl.concat(token), { observe: 'response' });
  }
}
