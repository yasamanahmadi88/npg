import { Directive, Input, OnDestroy, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';

@Directive({
  selector: '[jhiHasPermission]',
  standalone: false,
})
export class HasPermissionDirective implements OnDestroy {
  private authenticationSubscription?: Subscription;
  private resourceName?: string;
  private verb?: string;
  private isHidden = true;

  constructor(
    private accountService: AccountService,
    private templateRef: TemplateRef<any>,
    private viewContainerRef: ViewContainerRef
  ) {}

  @Input()
  set jhiHasPermission(val: string[]) {
    this.resourceName = val?.[0];
    this.verb = val?.[1];
    this.updateView();
    this.authenticationSubscription = this.accountService.getAuthenticationState().subscribe(() => this.updateView());
  }

  ngOnDestroy(): void {
    if (this.authenticationSubscription) {
      this.authenticationSubscription.unsubscribe();
    }
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
    }
  }

  private checkPermission(): boolean {
    if (!this.resourceName || !this.verb) {
      return false;
    }

    return this.accountService.hasResourcePermission(this.resourceName, this.verb);
  }
}
