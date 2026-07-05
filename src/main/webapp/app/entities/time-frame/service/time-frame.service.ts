import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITimeFrame, getTimeFrameIdentifier } from '../time-frame.model';

export type EntityResponseType = HttpResponse<ITimeFrame>;
export type EntityArrayResponseType = HttpResponse<ITimeFrame[]>;

@Injectable({ providedIn: 'root' })
export class TimeFrameService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/time-frames');
  protected resourceAllUrl = this.applicationConfigService.getEndpointFor('api/time-frames/all');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/time-frames/search');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(timeFrame: ITimeFrame): Observable<EntityResponseType> {
    return this.http.post<ITimeFrame>(this.resourceUrl, timeFrame, { observe: 'response' });
  }

  update(timeFrame: ITimeFrame): Observable<EntityResponseType> {
    return this.http.put<ITimeFrame>(`${this.resourceUrl}/${getTimeFrameIdentifier(timeFrame) as number}`, timeFrame, {
      observe: 'response',
    });
  }

  partialUpdate(timeFrame: ITimeFrame): Observable<EntityResponseType> {
    return this.http.patch<ITimeFrame>(`${this.resourceUrl}/${getTimeFrameIdentifier(timeFrame) as number}`, timeFrame, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITimeFrame>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITimeFrame[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITimeFrame[]>(this.resourceAllUrl, { params: options, observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITimeFrame[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTimeFrameToCollectionIfMissing(
    timeFrameCollection: ITimeFrame[],
    ...timeFramesToCheck: (ITimeFrame | null | undefined)[]
  ): ITimeFrame[] {
    const timeFrames: ITimeFrame[] = timeFramesToCheck.filter(isPresent);
    if (timeFrames.length > 0) {
      const timeFrameCollectionIdentifiers = timeFrameCollection.map(timeFrameItem => getTimeFrameIdentifier(timeFrameItem)!);
      const timeFramesToAdd = timeFrames.filter(timeFrameItem => {
        const timeFrameIdentifier = getTimeFrameIdentifier(timeFrameItem);
        if (timeFrameIdentifier == null || timeFrameCollectionIdentifiers.includes(timeFrameIdentifier)) {
          return false;
        }
        timeFrameCollectionIdentifiers.push(timeFrameIdentifier);
        return true;
      });
      return [...timeFramesToAdd, ...timeFrameCollection];
    }
    return timeFrameCollection;
  }
}
