import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUndifinedStatus, UndifinedStatus } from '../undifined-status.model';
import { UndifinedStatusService } from '../service/undifined-status.service';

@Injectable({ providedIn: 'root' })
export class UndifinedStatusRoutingResolveService {
  constructor(protected service: UndifinedStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUndifinedStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((undifinedStatus: HttpResponse<UndifinedStatus>) => {
          if (undifinedStatus.body) {
            return of(undifinedStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UndifinedStatus());
  }
}
