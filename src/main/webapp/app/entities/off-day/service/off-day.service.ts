import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOffDay, getOffDayIdentifier } from '../off-day.model';

export type EntityResponseType = HttpResponse<IOffDay>;
export type EntityArrayResponseType = HttpResponse<IOffDay[]>;

@Injectable({ providedIn: 'root' })
export class OffDayService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/off-days');
  protected resourceAllUrl = this.applicationConfigService.getEndpointFor('api/off-days/all');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/off-days/search');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(offDay: IOffDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offDay);
    return this.http
      .post<IOffDay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(offDay: IOffDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offDay);
    return this.http
      .put<IOffDay>(`${this.resourceUrl}/${getOffDayIdentifier(offDay) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(offDay: IOffDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offDay);
    return this.http
      .patch<IOffDay>(`${this.resourceUrl}/${getOffDayIdentifier(offDay) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOffDay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOffDay[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOffDay[]>(this.resourceAllUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOffDay[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOffDayToCollectionIfMissing(offDayCollection: IOffDay[], ...offDaysToCheck: (IOffDay | null | undefined)[]): IOffDay[] {
    const offDays: IOffDay[] = offDaysToCheck.filter(isPresent);
    if (offDays.length > 0) {
      const offDayCollectionIdentifiers = offDayCollection.map(offDayItem => getOffDayIdentifier(offDayItem)!);
      const offDaysToAdd = offDays.filter(offDayItem => {
        const offDayIdentifier = getOffDayIdentifier(offDayItem);
        if (offDayIdentifier == null || offDayCollectionIdentifiers.includes(offDayIdentifier)) {
          return false;
        }
        offDayCollectionIdentifiers.push(offDayIdentifier);
        return true;
      });
      return [...offDaysToAdd, ...offDayCollection];
    }
    return offDayCollection;
  }

  protected convertDateFromClient(offDay: IOffDay): IOffDay {
    return Object.assign({}, offDay, {
      offDate: offDay.offDate?.isValid() ? offDay.offDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.offDate = res.body.offDate ? dayjs(res.body.offDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((offDay: IOffDay) => {
        offDay.offDate = offDay.offDate ? dayjs(offDay.offDate) : undefined;
      });
    }
    return res;
  }
}
