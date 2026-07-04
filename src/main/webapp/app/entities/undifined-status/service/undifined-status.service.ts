import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUndifinedStatus, getUndifinedStatusIdentifier } from '../undifined-status.model';

export type EntityResponseType = HttpResponse<IUndifinedStatus>;
export type EntityArrayResponseType = HttpResponse<IUndifinedStatus[]>;

@Injectable({ providedIn: 'root' })
export class UndifinedStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/undifined-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(undifinedStatus: IUndifinedStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(undifinedStatus);
    return this.http
      .post<IUndifinedStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(undifinedStatus: IUndifinedStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(undifinedStatus);
    return this.http
      .put<IUndifinedStatus>(`${this.resourceUrl}/${getUndifinedStatusIdentifier(undifinedStatus) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(undifinedStatus: IUndifinedStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(undifinedStatus);
    return this.http
      .patch<IUndifinedStatus>(`${this.resourceUrl}/${getUndifinedStatusIdentifier(undifinedStatus) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUndifinedStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUndifinedStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUndifinedStatusToCollectionIfMissing(
    undifinedStatusCollection: IUndifinedStatus[],
    ...undifinedStatusesToCheck: (IUndifinedStatus | null | undefined)[]
  ): IUndifinedStatus[] {
    const undifinedStatuses: IUndifinedStatus[] = undifinedStatusesToCheck.filter(isPresent);
    if (undifinedStatuses.length > 0) {
      const undifinedStatusCollectionIdentifiers = undifinedStatusCollection.map(
        undifinedStatusItem => getUndifinedStatusIdentifier(undifinedStatusItem)!
      );
      const undifinedStatusesToAdd = undifinedStatuses.filter(undifinedStatusItem => {
        const undifinedStatusIdentifier = getUndifinedStatusIdentifier(undifinedStatusItem);
        if (undifinedStatusIdentifier == null || undifinedStatusCollectionIdentifiers.includes(undifinedStatusIdentifier)) {
          return false;
        }
        undifinedStatusCollectionIdentifiers.push(undifinedStatusIdentifier);
        return true;
      });
      return [...undifinedStatusesToAdd, ...undifinedStatusCollection];
    }
    return undifinedStatusCollection;
  }

  protected convertDateFromClient(undifinedStatus: IUndifinedStatus): IUndifinedStatus {
    return Object.assign({}, undifinedStatus, {
      insertDate: undifinedStatus.insertDate?.isValid() ? undifinedStatus.insertDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.insertDate = res.body.insertDate ? dayjs(res.body.insertDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((undifinedStatus: IUndifinedStatus) => {
        undifinedStatus.insertDate = undifinedStatus.insertDate ? dayjs(undifinedStatus.insertDate) : undefined;
      });
    }
    return res;
  }
}
