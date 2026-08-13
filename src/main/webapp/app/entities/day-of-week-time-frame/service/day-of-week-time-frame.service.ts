import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDayOfWeekTimeFrame, getDayOfWeekTimeFrameIdentifier } from '../day-of-week-time-frame.model';

export type EntityResponseType = HttpResponse<IDayOfWeekTimeFrame>;
export type EntityArrayResponseType = HttpResponse<IDayOfWeekTimeFrame[]>;

@Injectable({ providedIn: 'root' })
export class DayOfWeekTimeFrameService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/day-of-week-time-frames');
  protected resourceAllUrl = this.applicationConfigService.getEndpointFor('api/day-of-week-time-frames/all');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/day-of-week-time-frames/search');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dayOfWeekTimeFrame: IDayOfWeekTimeFrame): Observable<EntityResponseType> {
    return this.http.post<IDayOfWeekTimeFrame>(this.resourceUrl, dayOfWeekTimeFrame, { observe: 'response' });
  }

  update(dayOfWeekTimeFrame: IDayOfWeekTimeFrame): Observable<EntityResponseType> {
    return this.http.put<IDayOfWeekTimeFrame>(
      `${this.resourceUrl}/${getDayOfWeekTimeFrameIdentifier(dayOfWeekTimeFrame) as number}`,
      dayOfWeekTimeFrame,
      { observe: 'response' }
    );
  }

  partialUpdate(dayOfWeekTimeFrame: IDayOfWeekTimeFrame): Observable<EntityResponseType> {
    return this.http.patch<IDayOfWeekTimeFrame>(
      `${this.resourceUrl}/${getDayOfWeekTimeFrameIdentifier(dayOfWeekTimeFrame) as number}`,
      dayOfWeekTimeFrame,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDayOfWeekTimeFrame>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDayOfWeekTimeFrame[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDayOfWeekTimeFrame[]>(this.resourceAllUrl, { params: options, observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDayOfWeekTimeFrame[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDayOfWeekTimeFrameToCollectionIfMissing(
    dayOfWeekTimeFrameCollection: IDayOfWeekTimeFrame[],
    ...dayOfWeekTimeFramesToCheck: (IDayOfWeekTimeFrame | null | undefined)[]
  ): IDayOfWeekTimeFrame[] {
    const dayOfWeekTimeFrames: IDayOfWeekTimeFrame[] = dayOfWeekTimeFramesToCheck.filter(isPresent);
    if (dayOfWeekTimeFrames.length > 0) {
      const dayOfWeekTimeFrameCollectionIdentifiers = dayOfWeekTimeFrameCollection.map(
        dayOfWeekTimeFrameItem => getDayOfWeekTimeFrameIdentifier(dayOfWeekTimeFrameItem)!
      );
      const dayOfWeekTimeFramesToAdd = dayOfWeekTimeFrames.filter(dayOfWeekTimeFrameItem => {
        const dayOfWeekTimeFrameIdentifier = getDayOfWeekTimeFrameIdentifier(dayOfWeekTimeFrameItem);
        if (dayOfWeekTimeFrameIdentifier == null || dayOfWeekTimeFrameCollectionIdentifiers.includes(dayOfWeekTimeFrameIdentifier)) {
          return false;
        }
        dayOfWeekTimeFrameCollectionIdentifiers.push(dayOfWeekTimeFrameIdentifier);
        return true;
      });
      return [...dayOfWeekTimeFramesToAdd, ...dayOfWeekTimeFrameCollection];
    }
    return dayOfWeekTimeFrameCollection;
  }
}
