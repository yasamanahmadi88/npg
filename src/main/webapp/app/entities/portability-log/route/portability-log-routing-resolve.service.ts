import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPortabilityLog, PortabilityLog } from '../portability-log.model';
import { PortabilityLogService } from '../service/portability-log.service';

@Injectable({ providedIn: 'root' })
export class PortabilityLogRoutingResolveService {
  constructor(protected service: PortabilityLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPortabilityLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((portabilityLog: HttpResponse<PortabilityLog>) => {
          if (portabilityLog.body) {
            return of(portabilityLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PortabilityLog());
  }
}
