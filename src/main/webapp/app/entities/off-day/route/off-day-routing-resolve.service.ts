import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOffDay, OffDay } from '../off-day.model';
import { OffDayService } from '../service/off-day.service';

@Injectable({ providedIn: 'root' })
export class OffDayRoutingResolveService {
  constructor(protected service: OffDayService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOffDay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((offDay: HttpResponse<OffDay>) => {
          if (offDay.body) {
            return of(offDay.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OffDay());
  }
}
