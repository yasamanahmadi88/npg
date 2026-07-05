import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class PublicService {
  protected resourceCaptchaBackEndUrl = this.applicationConfigService.getEndpointFor('api/public/backUrl');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getCaptchaBackUrl(): Observable<HttpResponse<any>> {
    return this.http.get<any>(this.resourceCaptchaBackEndUrl, { observe: 'response' });
  }
}
