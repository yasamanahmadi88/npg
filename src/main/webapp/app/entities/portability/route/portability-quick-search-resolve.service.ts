import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPortability, Portability } from '../portability.model';
import { PortabilityService } from '../service/portability.service';

@Injectable({ providedIn: 'root' })
export class PortabilityQuickSearchResolveService implements Resolve<IPortability> {
  constructor(protected service: PortabilityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPortability> | any {
    const porNumber = route.params['porNumber'];
    return this.service
      .query({ 'porNumber.equals': porNumber })
      .pipe(mergeMap((portability: HttpResponse<Portability | any>) => of(portability.body)));
  }
}
