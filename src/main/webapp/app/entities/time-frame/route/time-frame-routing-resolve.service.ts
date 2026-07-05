import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITimeFrame, TimeFrame } from '../time-frame.model';
import { TimeFrameService } from '../service/time-frame.service';

@Injectable({ providedIn: 'root' })
export class TimeFrameRoutingResolveService {
  constructor(protected service: TimeFrameService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeFrame> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((timeFrame: HttpResponse<TimeFrame>) => {
          if (timeFrame.body) {
            return of(timeFrame.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TimeFrame());
  }
}
