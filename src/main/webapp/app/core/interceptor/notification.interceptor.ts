import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { AlertService } from 'app/core/util/alert.service';

@Injectable()
export class NotificationInterceptor implements HttpInterceptor {
  constructor(private injector: Injector) {}

  private get alertService(): AlertService {
    return this.injector.get(AlertService);
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        if (!(event instanceof HttpResponse)) {
          return;
        }

        let alert: string | null = null;
        let alertParams: string | null = null;

        for (const headerKey of event.headers.keys()) {
          const normalizedHeaderKey = headerKey.toLowerCase();

          if (normalizedHeaderKey.endsWith('app-alert')) {
            alert = event.headers.get(headerKey);
          } else if (normalizedHeaderKey.endsWith('app-params')) {
            const rawParams = event.headers.get(headerKey);
            alertParams = rawParams ? decodeURIComponent(rawParams.replace(/\+/g, ' ')) : null;
          }
        }

        if (alert) {
          this.alertService.addAlert({
            type: 'success',
            translationKey: alert,
            translationParams: { param: alertParams },
          });
        }
      })
    );
  }
}
