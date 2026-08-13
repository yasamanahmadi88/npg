import { Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';
import { Observable, ReplaySubject, of } from 'rxjs';
import { catchError, shareReplay, tap } from 'rxjs/operators';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Account } from 'app/core/auth/account.model';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private _userIdentity: Account | null = null;
  private authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account | null>;

  constructor(private injector: Injector) {}

  private get http(): HttpClient {
    return this.injector.get(HttpClient);
  }

  private get translateService(): TranslateService {
    return this.injector.get(TranslateService);
  }

  private get sessionStorageService(): SessionStorageService {
    return this.injector.get(SessionStorageService);
  }

  private get stateStorageService(): StateStorageService {
    return this.injector.get(StateStorageService);
  }

  private get router(): Router {
    return this.injector.get(Router);
  }

  private get applicationConfigService(): ApplicationConfigService {
    return this.injector.get(ApplicationConfigService);
  }

  save(account: Account): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/account'), account);
  }

  authenticate(identity: Account | null): void {
    this.userIdentity = identity;
    this.authenticationState.next(this.userIdentity);

    if (identity === null) {
      this.accountCache$ = undefined;
    }
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    if (!this.userIdentity) {
      return false;
    }

    const authoritiesToCheck = Array.isArray(authorities) ? authorities : [authorities];

    return this.userIdentity.authorities.some((authority: string) => authoritiesToCheck.includes(authority));
  }

  identity(force?: boolean): Observable<Account | null> {
    if (!this.accountCache$ || force || !this.isAuthenticated()) {
      this.accountCache$ = this.fetch().pipe(
        catchError(() => of(null)),
        tap((account: Account | null) => {
          this.authenticate(account);

          if (!this.sessionStorageService.retrieve('locale') && account?.langKey) {
            this.translateService.use(account.langKey);
          }

          if (account) {
            this.navigateToStoredUrl();
          }
        }),
        shareReplay({ bufferSize: 1, refCount: false })
      );
    }

    return this.accountCache$;
  }

  isAuthenticated(): boolean {
    return this.userIdentity !== null;
  }

  getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  get userIdentity(): Account | null {
    return this._userIdentity;
  }

  set userIdentity(value: Account | null) {
    this._userIdentity = value;
  }

  private fetch(): Observable<Account> {
    return this.http.get<Account>(this.applicationConfigService.getEndpointFor('api/account'));
  }

  private navigateToStoredUrl(): void {
    const previousUrl = this.stateStorageService.getUrl();

    if (previousUrl) {
      this.stateStorageService.clearUrl();
      this.router.navigateByUrl(previousUrl);
    }
  }
}
