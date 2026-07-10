import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFileReportGenerationLog, getFileReportGenerationLogIdentifier } from '../file-report-generation-log.model';

export type EntityResponseType = HttpResponse<IFileReportGenerationLog>;
export type EntityArrayResponseType = HttpResponse<IFileReportGenerationLog[]>;

@Injectable({ providedIn: 'root' })
export class FileReportGenerationLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/file-report-generation-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fileReportGenerationLog: IFileReportGenerationLog): Observable<EntityResponseType> {
    return this.http.post<IFileReportGenerationLog>(this.resourceUrl, fileReportGenerationLog, { observe: 'response' });
  }

  update(fileReportGenerationLog: IFileReportGenerationLog): Observable<EntityResponseType> {
    return this.http.put<IFileReportGenerationLog>(
      `${this.resourceUrl}/${getFileReportGenerationLogIdentifier(fileReportGenerationLog) as number}`,
      fileReportGenerationLog,
      { observe: 'response' }
    );
  }

  partialUpdate(fileReportGenerationLog: IFileReportGenerationLog): Observable<EntityResponseType> {
    return this.http.patch<IFileReportGenerationLog>(
      `${this.resourceUrl}/${getFileReportGenerationLogIdentifier(fileReportGenerationLog) as number}`,
      fileReportGenerationLog,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFileReportGenerationLog>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileReportGenerationLog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFileReportGenerationLogToCollectionIfMissing(
    fileReportGenerationLogCollection: IFileReportGenerationLog[],
    ...fileReportGenerationLogsToCheck: (IFileReportGenerationLog | null | undefined)[]
  ): IFileReportGenerationLog[] {
    const fileReportGenerationLogs: IFileReportGenerationLog[] = fileReportGenerationLogsToCheck.filter(isPresent);
    if (fileReportGenerationLogs.length > 0) {
      const fileReportGenerationLogCollectionIdentifiers = fileReportGenerationLogCollection.map(
        fileReportGenerationLogItem => getFileReportGenerationLogIdentifier(fileReportGenerationLogItem)!
      );
      const fileReportGenerationLogsToAdd = fileReportGenerationLogs.filter(fileReportGenerationLogItem => {
        const fileReportGenerationLogIdentifier = getFileReportGenerationLogIdentifier(fileReportGenerationLogItem);
        if (
          fileReportGenerationLogIdentifier == null ||
          fileReportGenerationLogCollectionIdentifiers.includes(fileReportGenerationLogIdentifier)
        ) {
          return false;
        }
        fileReportGenerationLogCollectionIdentifiers.push(fileReportGenerationLogIdentifier);
        return true;
      });
      return [...fileReportGenerationLogsToAdd, ...fileReportGenerationLogCollection];
    }
    return fileReportGenerationLogCollection;
  }
}
