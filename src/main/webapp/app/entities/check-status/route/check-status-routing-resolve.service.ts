import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckStatus, CheckStatus } from '../check-status.model';
import { CheckStatusService } from '../service/check-status.service';

@Injectable({ providedIn: 'root' })
export class CheckStatusRoutingResolveService {
  constructor(protected service: CheckStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkStatus: HttpResponse<CheckStatus>) => {
          if (checkStatus.body) {
            return of(checkStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckStatus());
  }
}
