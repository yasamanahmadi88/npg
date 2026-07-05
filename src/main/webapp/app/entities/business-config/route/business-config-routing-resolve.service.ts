import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBusinessConfig, BusinessConfig } from '../business-config.model';
import { BusinessConfigService } from '../service/business-config.service';

@Injectable({ providedIn: 'root' })
export class BusinessConfigRoutingResolveService {
  constructor(protected service: BusinessConfigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBusinessConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((businessConfig: HttpResponse<BusinessConfig>) => {
          if (businessConfig.body) {
            return of(businessConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BusinessConfig());
  }
}
