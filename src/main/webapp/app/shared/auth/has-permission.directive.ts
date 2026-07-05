import { Directive, Input, OnDestroy, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';
import { Router } from '@angular/router';

@Directive({
  selector: '[jhiHasPermission]',
  standalone: false,
})
export class HasPermissionDirective implements OnDestroy {
  private logicalOp = 'AND';
  private authenticationSubscription?: Subscription;
  private params?: any[];
  private isHidden = true;

  constructor(
    private accountService: AccountService,
    protected router: Router,
    private templateRef: TemplateRef<any>,
    private viewContainerRef: ViewContainerRef
  ) {}

  @Input()
  set jhiHasPermission(val: any) {
    this.params = val;
    this.subscribeToAuthChanges();
    this.updateView();
  }

  @Input()
  set jhiHasPermissionOp(permop: any) {
    this.logicalOp = permop;
    this.subscribeToAuthChanges();
    this.updateView();
  }

  ngOnDestroy(): void {
    this.authenticationSubscription?.unsubscribe();
  }

  private subscribeToAuthChanges(): void {
    this.authenticationSubscription?.unsubscribe();
    this.authenticationSubscription = this.accountService.getAuthenticationState().subscribe(() => this.updateView());
  }

  private updateView(): void {
    if (this.checkPermission()) {
      if (this.isHidden) {
        this.viewContainerRef.createEmbeddedView(this.templateRef);
        this.isHidden = false;
      }
    } else {
      this.isHidden = true;
      this.viewContainerRef.clear();
      // this.router.navigate(['/not-permitted/forbidden']);
    }
  }

  private checkPermission(): boolean {
    if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
      return true;
    }

    const resources = this.accountService.userIdentity?.resourceAuthorities;
    if (!resources || !this.params) {
      return false;
    }

    const resourceName = String(this.params[0] ?? '').toUpperCase().trim();
    const verb = String(this.params[1] ?? '').toUpperCase().trim();

    return resources.some(resAuth => {
      const resName = String(resAuth.resource?.name ?? resAuth.resourceName ?? '')
        .toUpperCase()
        .trim();
      return resourceName === resName && verb === String(resAuth.verb).toUpperCase();
    });
  }
}
