import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICrmToCrdbResponseMap, CrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';
import { CrmToCrdbResponseMapService } from '../service/crm-to-crdb-response-map.service';

@Injectable({ providedIn: 'root' })
export class CrmToCrdbResponseMapRoutingResolveService {
  constructor(protected service: CrmToCrdbResponseMapService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICrmToCrdbResponseMap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((crmToCrdbResponseMap: HttpResponse<CrmToCrdbResponseMap>) => {
          if (crmToCrdbResponseMap.body) {
            return of(crmToCrdbResponseMap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CrmToCrdbResponseMap());
  }
}
