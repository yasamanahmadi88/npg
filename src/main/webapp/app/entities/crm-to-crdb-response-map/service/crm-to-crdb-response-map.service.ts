import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICrmToCrdbResponseMap, getCrmToCrdbResponseMapIdentifier } from '../crm-to-crdb-response-map.model';

export type EntityResponseType = HttpResponse<ICrmToCrdbResponseMap>;
export type EntityArrayResponseType = HttpResponse<ICrmToCrdbResponseMap[]>;

@Injectable({ providedIn: 'root' })
export class CrmToCrdbResponseMapService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crm-to-crdb-response-maps');
  protected resourceAllUrl = this.applicationConfigService.getEndpointFor('api/crm-to-crdb-response-maps/all');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/crm-to-crdb-response-maps/search');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(crmToCrdbResponseMap: ICrmToCrdbResponseMap): Observable<EntityResponseType> {
    return this.http.post<ICrmToCrdbResponseMap>(this.resourceUrl, crmToCrdbResponseMap, { observe: 'response' });
  }

  update(crmToCrdbResponseMap: ICrmToCrdbResponseMap): Observable<EntityResponseType> {
    return this.http.put<ICrmToCrdbResponseMap>(
      `${this.resourceUrl}/${getCrmToCrdbResponseMapIdentifier(crmToCrdbResponseMap) as number}`,
      crmToCrdbResponseMap,
      { observe: 'response' }
    );
  }

  partialUpdate(crmToCrdbResponseMap: ICrmToCrdbResponseMap): Observable<EntityResponseType> {
    return this.http.patch<ICrmToCrdbResponseMap>(
      `${this.resourceUrl}/${getCrmToCrdbResponseMapIdentifier(crmToCrdbResponseMap) as number}`,
      crmToCrdbResponseMap,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICrmToCrdbResponseMap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICrmToCrdbResponseMap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICrmToCrdbResponseMap[]>(this.resourceAllUrl, { params: options, observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICrmToCrdbResponseMap[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCrmToCrdbResponseMapToCollectionIfMissing(
    crmToCrdbResponseMapCollection: ICrmToCrdbResponseMap[],
    ...crmToCrdbResponseMapsToCheck: (ICrmToCrdbResponseMap | null | undefined)[]
  ): ICrmToCrdbResponseMap[] {
    const crmToCrdbResponseMaps: ICrmToCrdbResponseMap[] = crmToCrdbResponseMapsToCheck.filter(isPresent);
    if (crmToCrdbResponseMaps.length > 0) {
      const crmToCrdbResponseMapCollectionIdentifiers = crmToCrdbResponseMapCollection.map(
        crmToCrdbResponseMapItem => getCrmToCrdbResponseMapIdentifier(crmToCrdbResponseMapItem)!
      );
      const crmToCrdbResponseMapsToAdd = crmToCrdbResponseMaps.filter(crmToCrdbResponseMapItem => {
        const crmToCrdbResponseMapIdentifier = getCrmToCrdbResponseMapIdentifier(crmToCrdbResponseMapItem);
        if (crmToCrdbResponseMapIdentifier == null || crmToCrdbResponseMapCollectionIdentifiers.includes(crmToCrdbResponseMapIdentifier)) {
          return false;
        }
        crmToCrdbResponseMapCollectionIdentifiers.push(crmToCrdbResponseMapIdentifier);
        return true;
      });
      return [...crmToCrdbResponseMapsToAdd, ...crmToCrdbResponseMapCollection];
    }
    return crmToCrdbResponseMapCollection;
  }
}
