import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPortabilityLog, getPortabilityLogIdentifier } from '../portability-log.model';

export type EntityResponseType = HttpResponse<IPortabilityLog>;
export type EntityArrayResponseType = HttpResponse<IPortabilityLog[]>;

@Injectable({ providedIn: 'root' })
export class PortabilityLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/portability-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(portabilityLog: IPortabilityLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(portabilityLog);
    return this.http
      .post<IPortabilityLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(portabilityLog: IPortabilityLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(portabilityLog);
    return this.http
      .put<IPortabilityLog>(`${this.resourceUrl}/${getPortabilityLogIdentifier(portabilityLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(portabilityLog: IPortabilityLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(portabilityLog);
    return this.http
      .patch<IPortabilityLog>(`${this.resourceUrl}/${getPortabilityLogIdentifier(portabilityLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPortabilityLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPortabilityLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPortabilityLogToCollectionIfMissing(
    portabilityLogCollection: IPortabilityLog[],
    ...portabilityLogsToCheck: (IPortabilityLog | null | undefined)[]
  ): IPortabilityLog[] {
    const portabilityLogs: IPortabilityLog[] = portabilityLogsToCheck.filter(isPresent);
    if (portabilityLogs.length > 0) {
      const portabilityLogCollectionIdentifiers = portabilityLogCollection.map(
        portabilityLogItem => getPortabilityLogIdentifier(portabilityLogItem)!
      );
      const portabilityLogsToAdd = portabilityLogs.filter(portabilityLogItem => {
        const portabilityLogIdentifier = getPortabilityLogIdentifier(portabilityLogItem);
        if (portabilityLogIdentifier == null || portabilityLogCollectionIdentifiers.includes(portabilityLogIdentifier)) {
          return false;
        }
        portabilityLogCollectionIdentifiers.push(portabilityLogIdentifier);
        return true;
      });
      return [...portabilityLogsToAdd, ...portabilityLogCollection];
    }
    return portabilityLogCollection;
  }

  protected convertDateFromClient(portabilityLog: IPortabilityLog): IPortabilityLog {
    return Object.assign({}, portabilityLog, {
      porPortedDate: portabilityLog.porPortedDate?.isValid() ? portabilityLog.porPortedDate.toJSON() : undefined,
      portationDate: portabilityLog.portationDate?.isValid() ? portabilityLog.portationDate.toJSON() : undefined,
      porDeadline: portabilityLog.porDeadline?.isValid() ? portabilityLog.porDeadline.toJSON() : undefined,
      porCrDate: portabilityLog.porCrDate?.isValid() ? portabilityLog.porCrDate.toJSON() : undefined,
      porUpdDate: portabilityLog.porUpdDate?.isValid() ? portabilityLog.porUpdDate.toJSON() : undefined,
      porTechDeadline: portabilityLog.porTechDeadline?.isValid() ? portabilityLog.porTechDeadline.toJSON() : undefined,
      insertTimestamp: portabilityLog.insertTimestamp?.isValid() ? portabilityLog.insertTimestamp.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.porPortedDate = res.body.porPortedDate ? dayjs(res.body.porPortedDate) : undefined;
      res.body.portationDate = res.body.portationDate ? dayjs(res.body.portationDate) : undefined;
      res.body.porDeadline = res.body.porDeadline ? dayjs(res.body.porDeadline) : undefined;
      res.body.porCrDate = res.body.porCrDate ? dayjs(res.body.porCrDate) : undefined;
      res.body.porUpdDate = res.body.porUpdDate ? dayjs(res.body.porUpdDate) : undefined;
      res.body.porTechDeadline = res.body.porTechDeadline ? dayjs(res.body.porTechDeadline) : undefined;
      res.body.insertTimestamp = res.body.insertTimestamp ? dayjs(res.body.insertTimestamp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((portabilityLog: IPortabilityLog) => {
        portabilityLog.porPortedDate = portabilityLog.porPortedDate ? dayjs(portabilityLog.porPortedDate) : undefined;
        portabilityLog.portationDate = portabilityLog.portationDate ? dayjs(portabilityLog.portationDate) : undefined;
        portabilityLog.porDeadline = portabilityLog.porDeadline ? dayjs(portabilityLog.porDeadline) : undefined;
        portabilityLog.porCrDate = portabilityLog.porCrDate ? dayjs(portabilityLog.porCrDate) : undefined;
        portabilityLog.porUpdDate = portabilityLog.porUpdDate ? dayjs(portabilityLog.porUpdDate) : undefined;
        portabilityLog.porTechDeadline = portabilityLog.porTechDeadline ? dayjs(portabilityLog.porTechDeadline) : undefined;
        portabilityLog.insertTimestamp = portabilityLog.insertTimestamp ? dayjs(portabilityLog.insertTimestamp) : undefined;
      });
    }
    return res;
  }
}
