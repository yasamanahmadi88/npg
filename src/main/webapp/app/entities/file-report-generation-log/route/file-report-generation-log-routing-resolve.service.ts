import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFileReportGenerationLog, FileReportGenerationLog } from '../file-report-generation-log.model';
import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';

@Injectable({ providedIn: 'root' })
export class FileReportGenerationLogRoutingResolveService {
  constructor(protected service: FileReportGenerationLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFileReportGenerationLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fileReportGenerationLog: HttpResponse<FileReportGenerationLog>) => {
          if (fileReportGenerationLog.body) {
            return of(fileReportGenerationLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FileReportGenerationLog());
  }
}
