import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPortability, getPortabilityIdentifier } from '../portability.model';

export type EntityResponseType = HttpResponse<IPortability>;
export type EntityArrayResponseType = HttpResponse<IPortability[]>;

@Injectable({ providedIn: 'root' })
export class PortabilityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/portabilities');
  protected resourceAllUrl = this.applicationConfigService.getEndpointFor('api/portabilities/all');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/portabilities/search');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(portability: IPortability): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(portability);
    return this.http
      .post<IPortability>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(portability: IPortability): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(portability);
    return this.http
      .put<IPortability>(`${this.resourceUrl}/${getPortabilityIdentifier(portability) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(portability: IPortability): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(portability);
    return this.http
      .patch<IPortability>(`${this.resourceUrl}/${getPortabilityIdentifier(portability) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPortability>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  findPorReq(porReqId: string): Observable<EntityResponseType> {
    return this.http
      .get<IPortability>(this.resourceUrl + '?porRequestId.equals=' + porReqId, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPortability[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  /*  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPortability[]>(this.resourceAllUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }*/

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPortability[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPortabilityToCollectionIfMissing(
    portabilityCollection: IPortability[],
    ...portabilitiesToCheck: (IPortability | null | undefined)[]
  ): IPortability[] {
    const portabilities: IPortability[] = portabilitiesToCheck.filter(isPresent);
    if (portabilities.length > 0) {
      const portabilityCollectionIdentifiers = portabilityCollection.map(portabilityItem => getPortabilityIdentifier(portabilityItem)!);
      const portabilitiesToAdd = portabilities.filter(portabilityItem => {
        const portabilityIdentifier = getPortabilityIdentifier(portabilityItem);
        if (portabilityIdentifier == null || portabilityCollectionIdentifiers.includes(portabilityIdentifier)) {
          return false;
        }
        portabilityCollectionIdentifiers.push(portabilityIdentifier);
        return true;
      });
      return [...portabilitiesToAdd, ...portabilityCollection];
    }
    return portabilityCollection;
  }

  protected convertDateFromClient(portability: IPortability): IPortability {
    return Object.assign({}, portability, {
      porPortedDate: portability.porPortedDate?.isValid() ? portability.porPortedDate.toJSON() : undefined,
      portationDate: portability.portationDate?.isValid() ? portability.portationDate.toJSON() : undefined,
      porDeadline: portability.porDeadline?.isValid() ? portability.porDeadline.toJSON() : undefined,
      porCrDate: portability.porCrDate?.isValid() ? portability.porCrDate.toJSON() : undefined,
      porUpdDate: portability.porUpdDate?.isValid() ? portability.porUpdDate.toJSON() : undefined,
      porTechDeadline: portability.porTechDeadline?.isValid() ? portability.porTechDeadline.toJSON() : undefined,
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
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((portability: IPortability) => {
        portability.porPortedDate = portability.porPortedDate ? dayjs(portability.porPortedDate) : undefined;
        portability.portationDate = portability.portationDate ? dayjs(portability.portationDate) : undefined;
        portability.porDeadline = portability.porDeadline ? dayjs(portability.porDeadline) : undefined;
        portability.porCrDate = portability.porCrDate ? dayjs(portability.porCrDate) : undefined;
        portability.porUpdDate = portability.porUpdDate ? dayjs(portability.porUpdDate) : undefined;
        portability.porTechDeadline = portability.porTechDeadline ? dayjs(portability.porTechDeadline) : undefined;
      });
    }
    return res;
  }
}
