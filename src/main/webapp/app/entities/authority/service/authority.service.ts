import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IAuthority } from '../authority.model';
import { createRequestOption } from '../../../core/request/request-util';

type EntityResponseType = HttpResponse<IAuthority>;
type EntityArrayResponseType = HttpResponse<IAuthority[]>;

@Injectable({ providedIn: 'root' })
export class AuthorityService {
  public resourceUrl = SERVER_API_URL + 'api/authorities';
  public resourceUrlAll = SERVER_API_URL + 'api/authorities/all';
  public resourceUrlSearch = SERVER_API_URL + 'api/authorities/search';

  constructor(protected http: HttpClient) {}

  create(authority: IAuthority): Observable<EntityResponseType> {
    return this.http.post<IAuthority>(this.resourceUrl, authority, { observe: 'response' });
  }

  update(authority: IAuthority): Observable<EntityResponseType> {
    return this.http.put<IAuthority>(this.resourceUrl, authority, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthority[]>(this.resourceUrlSearch, { params: options, observe: 'response' });
  }
  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthority[]>(this.resourceUrlAll, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
