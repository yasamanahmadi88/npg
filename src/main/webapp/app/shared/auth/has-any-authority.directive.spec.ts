jest.mock('app/core/auth/account.service');

import {Component, ElementRef, ViewChild, NgModule} from '@angular/core';
import { TestBed, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Subject } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import { HasAnyAuthorityDirective } from './has-any-authority.directive';

@Component({
  template: ` <div *jhiHasAnyAuthority="'ROLE_ADMIN'" #content></div> `,
})
class TestHasAnyAuthorityDirectiveComponent {
  @ViewChild('content', { static: false })
  content?: ElementRef;
}

@NgModule({
  declarations: [HasAnyAuthorityDirective],
  exports: [HasAnyAuthorityDirective],
})
class HasAnyAuthorityDirectiveTestingModule {}

describe('HasAnyAuthorityDirective tests', () => {
  let mockAccountService: AccountService;
  const authenticationState = new Subject<Account | null>();

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        imports: [TestHasAnyAuthorityDirectiveComponent],
        providers: [AccountService],
      });
      TestBed.overrideComponent(TestHasAnyAuthorityDirectiveComponent, {
        add: { imports: [HasAnyAuthorityDirectiveTestingModule] },
      });
    })
  );

  beforeEach(() => {
    mockAccountService = TestBed.inject(AccountService);
    mockAccountService.getAuthenticationState = jest.fn(() => authenticationState.asObservable());
  });

  describe('set jhiHasAnyAuthority', () => {
    it('should show restricted content to user if user has required role', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => true);
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const comp = fixture.componentInstance;

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeDefined();
    });

    it('should not show restricted content to user if user has not required role', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => false);
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const comp = fixture.componentInstance;

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeUndefined();
    });
  });

  describe('change authorities', () => {
    it('should show or not show restricted content correctly if user authorities are changing', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => true);
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const comp = fixture.componentInstance;

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeDefined();

      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => false);

      // WHEN
      authenticationState.next(null as any);
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeUndefined();

      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => true);

      // WHEN
      authenticationState.next(null as any);
      fixture.detectChanges();

      // THEN
      expect(comp.content).toBeDefined();
    });
  });

  describe('ngOnDestroy', () => {
    it('should destroy authentication state subscription on component destroy', () => {
      // GIVEN
      mockAccountService.hasAnyAuthority = jest.fn(() => true);
      const fixture = TestBed.createComponent(TestHasAnyAuthorityDirectiveComponent);
      const div = fixture.debugElement.queryAllNodes(By.directive(HasAnyAuthorityDirective))[0];
      const hasAnyAuthorityDirective = div.injector.get(HasAnyAuthorityDirective);

      // WHEN
      fixture.detectChanges();

      // THEN
      expect(mockAccountService.hasAnyAuthority).toHaveBeenCalled();

      // WHEN
      jest.clearAllMocks();
      authenticationState.next(null as any);

      // THEN
      expect(mockAccountService.hasAnyAuthority).toHaveBeenCalled();

      // WHEN
      jest.clearAllMocks();
      hasAnyAuthorityDirective.ngOnDestroy();
      authenticationState.next(null as any);

      // THEN
      expect(mockAccountService.hasAnyAuthority).not.toHaveBeenCalled();
    });
  });
});
