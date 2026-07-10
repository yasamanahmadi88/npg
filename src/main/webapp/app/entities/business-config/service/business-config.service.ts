import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBusinessConfig, getBusinessConfigIdentifier } from '../business-config.model';

export type EntityResponseType = HttpResponse<IBusinessConfig>;
export type EntityArrayResponseType = HttpResponse<IBusinessConfig[]>;

@Injectable({ providedIn: 'root' })
export class BusinessConfigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/business-configs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(businessConfig: IBusinessConfig): Observable<EntityResponseType> {
    return this.http.post<IBusinessConfig>(this.resourceUrl, businessConfig, { observe: 'response' });
  }

  update(businessConfig: IBusinessConfig): Observable<EntityResponseType> {
    return this.http.put<IBusinessConfig>(`${this.resourceUrl}/${getBusinessConfigIdentifier(businessConfig) as string}`, businessConfig, {
      observe: 'response',
    });
  }

  partialUpdate(businessConfig: IBusinessConfig): Observable<EntityResponseType> {
    return this.http.patch<IBusinessConfig>(
      `${this.resourceUrl}/${getBusinessConfigIdentifier(businessConfig) as string}`,
      businessConfig,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IBusinessConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBusinessConfigToCollectionIfMissing(
    businessConfigCollection: IBusinessConfig[],
    ...businessConfigsToCheck: (IBusinessConfig | null | undefined)[]
  ): IBusinessConfig[] {
    const businessConfigs: IBusinessConfig[] = businessConfigsToCheck.filter(isPresent);
    if (businessConfigs.length > 0) {
      const businessConfigCollectionIdentifiers = businessConfigCollection.map(
        businessConfigItem => getBusinessConfigIdentifier(businessConfigItem)!
      );
      const businessConfigsToAdd = businessConfigs.filter(businessConfigItem => {
        const businessConfigIdentifier = getBusinessConfigIdentifier(businessConfigItem);
        if (businessConfigIdentifier == null || businessConfigCollectionIdentifiers.includes(businessConfigIdentifier)) {
          return false;
        }
        businessConfigCollectionIdentifiers.push(businessConfigIdentifier);
        return true;
      });
      return [...businessConfigsToAdd, ...businessConfigCollection];
    }
    return businessConfigCollection;
  }
}
