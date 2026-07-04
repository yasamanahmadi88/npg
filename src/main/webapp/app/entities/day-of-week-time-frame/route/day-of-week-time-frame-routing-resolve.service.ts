import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDayOfWeekTimeFrame, DayOfWeekTimeFrame } from '../day-of-week-time-frame.model';
import { DayOfWeekTimeFrameService } from '../service/day-of-week-time-frame.service';

@Injectable({ providedIn: 'root' })
export class DayOfWeekTimeFrameRoutingResolveService {
  constructor(protected service: DayOfWeekTimeFrameService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDayOfWeekTimeFrame> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dayOfWeekTimeFrame: HttpResponse<DayOfWeekTimeFrame>) => {
          if (dayOfWeekTimeFrame.body) {
            return of(dayOfWeekTimeFrame.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DayOfWeekTimeFrame());
  }
}
