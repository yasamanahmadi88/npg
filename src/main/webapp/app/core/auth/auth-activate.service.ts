import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RouteAccessDeniedDialogComponent } from 'app/shared/auth/route-access-denied-dialog/route-access-denied-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { Authority } from 'app/config/authority.constants';

@Injectable({ providedIn: 'root' })
export class AuthActivateService {
  private params?: string[];
  private targetUrl?: string;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private stateStorageService: StateStorageService,
    private modalService: NgbModal,
    protected translateService: TranslateService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    this.params = route.data['params'];
    this.targetUrl = state.url;
    return this.checkPermission();
  }

  checkPermission(): Observable<boolean> {
    return this.accountService.identity().pipe(
      map(account => {
        if (!account) {
          this.stateStorageService.storeUrl(this.targetUrl ?? '');
          this.router.navigate(['/login']);
          return false;
        }

        if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
          return true;
        }

        let hasPerm = false;
        if (this.accountService.userIdentity?.resourceAuthorities) {
          for (const resAuth of this.accountService.userIdentity.resourceAuthorities) {
            if (
              this.params &&
              this.params[0]?.toUpperCase() === resAuth.resource?.name?.toUpperCase() &&
              this.params[1]?.toUpperCase() === resAuth.verb?.toUpperCase()
            ) {
              hasPerm = true;
              break;
            }
          }
        }

        if (!hasPerm) {
          const modalRef: NgbModalRef = this.modalService.open(RouteAccessDeniedDialogComponent);
          modalRef.componentInstance.description = this.translateService.instant('login.error.haveNot');
          this.router.navigate(['/accessdenied']);
        }

        return hasPerm;
      })
    );
  }
}
