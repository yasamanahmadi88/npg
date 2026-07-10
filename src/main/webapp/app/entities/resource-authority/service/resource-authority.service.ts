import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResourceAuthority, getResourceAuthorityIdentifier } from '../resource-authority.model';

export type EntityResponseType = HttpResponse<IResourceAuthority>;
export type EntityArrayResponseType = HttpResponse<IResourceAuthority[]>;

@Injectable({ providedIn: 'root' })
export class ResourceAuthorityService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/resource-authorities');
  public resourceAllUrl = this.applicationConfigService.getEndpointFor('api/resource-authorities/all');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/resource-authorities/search');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(resourceAuthority: IResourceAuthority): Observable<EntityResponseType> {
    return this.http.post<IResourceAuthority>(this.resourceUrl, resourceAuthority, { observe: 'response' });
  }

  update(resourceAuthority: IResourceAuthority): Observable<EntityResponseType> {
    return this.http.put<IResourceAuthority>(
      `${this.resourceUrl}/${getResourceAuthorityIdentifier(resourceAuthority) as number}`,
      resourceAuthority,
      { observe: 'response' }
    );
  }

  partialUpdate(resourceAuthority: IResourceAuthority): Observable<EntityResponseType> {
    return this.http.patch<IResourceAuthority>(
      `${this.resourceUrl}/${getResourceAuthorityIdentifier(resourceAuthority) as number}`,
      resourceAuthority,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResourceAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResourceAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResourceAuthority[]>(this.resourceAllUrl, { params: options, observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResourceAuthority[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResourceAuthorityToCollectionIfMissing(
    resourceAuthorityCollection: IResourceAuthority[],
    ...resourceAuthoritiesToCheck: (IResourceAuthority | null | undefined)[]
  ): IResourceAuthority[] {
    const resourceAuthorities: IResourceAuthority[] = resourceAuthoritiesToCheck.filter(isPresent);
    if (resourceAuthorities.length > 0) {
      const resourceAuthorityCollectionIdentifiers = resourceAuthorityCollection.map(
        resourceAuthorityItem => getResourceAuthorityIdentifier(resourceAuthorityItem)!
      );
      const resourceAuthoritiesToAdd = resourceAuthorities.filter(resourceAuthorityItem => {
        const resourceAuthorityIdentifier = getResourceAuthorityIdentifier(resourceAuthorityItem);
        if (resourceAuthorityIdentifier == null || resourceAuthorityCollectionIdentifiers.includes(resourceAuthorityIdentifier)) {
          return false;
        }
        resourceAuthorityCollectionIdentifiers.push(resourceAuthorityIdentifier);
        return true;
      });
      return [...resourceAuthoritiesToAdd, ...resourceAuthorityCollection];
    }
    return resourceAuthorityCollection;
  }
}
