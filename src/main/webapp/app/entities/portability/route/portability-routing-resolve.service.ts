import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPortability, Portability } from '../portability.model';
import { PortabilityService } from '../service/portability.service';

@Injectable({ providedIn: 'root' })
export class PortabilityRoutingResolveService {
  constructor(protected service: PortabilityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPortability> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
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
