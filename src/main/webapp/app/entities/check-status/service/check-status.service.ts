import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckStatus, getCheckStatusIdentifier } from '../check-status.model';

export type EntityResponseType = HttpResponse<ICheckStatus>;
export type EntityArrayResponseType = HttpResponse<ICheckStatus[]>;

@Injectable({ providedIn: 'root' })
export class CheckStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/check-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(checkStatus: ICheckStatus): Observable<EntityResponseType> {
    return this.http.post<ICheckStatus>(this.resourceUrl, checkStatus, { observe: 'response' });
  }

  update(checkStatus: ICheckStatus): Observable<EntityResponseType> {
    return this.http.put<ICheckStatus>(`${this.resourceUrl}/${getCheckStatusIdentifier(checkStatus) as number}`, checkStatus, {
      observe: 'response',
    });
  }

  partialUpdate(checkStatus: ICheckStatus): Observable<EntityResponseType> {
    return this.http.patch<ICheckStatus>(`${this.resourceUrl}/${getCheckStatusIdentifier(checkStatus) as number}`, checkStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCheckStatusToCollectionIfMissing(
    checkStatusCollection: ICheckStatus[],
    ...checkStatusesToCheck: (ICheckStatus | null | undefined)[]
  ): ICheckStatus[] {
    const checkStatuses: ICheckStatus[] = checkStatusesToCheck.filter(isPresent);
    if (checkStatuses.length > 0) {
      const checkStatusCollectionIdentifiers = checkStatusCollection.map(checkStatusItem => getCheckStatusIdentifier(checkStatusItem)!);
      const checkStatusesToAdd = checkStatuses.filter(checkStatusItem => {
        const checkStatusIdentifier = getCheckStatusIdentifier(checkStatusItem);
        if (checkStatusIdentifier == null || checkStatusCollectionIdentifiers.includes(checkStatusIdentifier)) {
          return false;
        }
        checkStatusCollectionIdentifiers.push(checkStatusIdentifier);
        return true;
      });
      return [...checkStatusesToAdd, ...checkStatusCollection];
    }
    return checkStatusCollection;
  }
}
