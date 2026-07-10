import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class CommonService {
  public portabilityPorTypeRepoUrl = SERVER_API_URL + 'api/portabilities/report-por-type';
  public portabilityPorDateTrendRepoUrl = SERVER_API_URL + 'api/portabilities/report-por-date-trend';

  constructor(protected http: HttpClient) {}

  fetchPortabilityPortType(startDate: string, endDate: string): Observable<HttpResponse<any[]>> {
    return this.http
      .get<any[]>(this.portabilityPorTypeRepoUrl.concat('?startDate=').concat(startDate).concat('&endDate=').concat(endDate), {
        observe: 'response',
      })
      .pipe();
  }

  fetchPortabilityPortDate(startDate: string, endDate: string): Observable<HttpResponse<any[]>> {
    return this.http
      .get<any[]>(this.portabilityPorDateTrendRepoUrl.concat('?startDate=').concat(startDate).concat('&endDate=').concat(endDate), {
        observe: 'response',
      })
      .pipe();
  }
}
