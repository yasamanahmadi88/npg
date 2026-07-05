import { Component } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Subject } from 'rxjs';

import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';

import { HasPermissionDirective } from './has-permission.directive';

@Component({
  template: ` <div *jhiHasPermission="['portability', 'view']" #content></div> `,
  standalone: false,
})
class TestHasPermissionDirectiveComponent {}

describe('HasPermissionDirective tests', () => {
  let mockAccountService: AccountService;
  let authenticationState: Subject<Account | null>;

  beforeEach(() => {
    authenticationState = new Subject<Account | null>();
    mockAccountService = {
      getAuthenticationState: jest.fn(() => authenticationState.asObservable()),
      hasAnyAuthority: jest.fn(() => false),
      userIdentity: null,
    } as unknown as AccountService;

    TestBed.configureTestingModule({
      declarations: [TestHasPermissionDirectiveComponent, HasPermissionDirective],
      providers: [{ provide: AccountService, useValue: mockAccountService }],
    });
  });

  it('should show content for admin users', () => {
    mockAccountService.hasAnyAuthority = jest.fn(() => true);
    const fixture = TestBed.createComponent(TestHasPermissionDirectiveComponent);
    fixture.detectChanges();

    expect(fixture.debugElement.query(By.css('div'))).not.toBeNull();
  });

  it('should show content when resource permission matches', () => {
    mockAccountService.hasAnyAuthority = jest.fn(() => false);
    mockAccountService.userIdentity = {
      resourceAuthorities: [{ resource: { name: 'portability' }, verb: 'VIEW' }],
    } as Account;

    const fixture = TestBed.createComponent(TestHasPermissionDirectiveComponent);
    fixture.detectChanges();

    expect(fixture.debugElement.query(By.css('div'))).not.toBeNull();
  });

  it('should hide content when permission is missing', () => {
    mockAccountService.hasAnyAuthority = jest.fn(() => false);
    mockAccountService.userIdentity = {
      resourceAuthorities: [{ resource: { name: 'other' }, verb: 'VIEW' }],
    } as Account;

    const fixture = TestBed.createComponent(TestHasPermissionDirectiveComponent);
    fixture.detectChanges();

    expect(fixture.debugElement.query(By.css('div'))).toBeNull();
  });
});
