import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { IPortability, Portability } from '../portability.model';
import { PortabilityService } from '../service/portability.service';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class PorReqIdResolveService {
  constructor(protected service: PortabilityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPortability> | Observable<never> {
    const porRequestId = route.params['porRequestId'];
    if (porRequestId) {
      return this.service.findPorReq(porRequestId).pipe(
        mergeMap((portability: HttpResponse<Portability>) => {
          if (portability.body) {
            return of(portability.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Portability());
  }
}
