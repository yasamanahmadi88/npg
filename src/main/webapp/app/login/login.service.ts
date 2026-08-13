import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Login } from './login.model';
import { BackUrl } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { Logout } from './logout.model';
//const url = 'http://localhost:8080/api/cp-eyrtyertye';
@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(
    private accountService: AccountService,
    private authServerProvider: AuthServerProvider,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  login(credentials: Login): Observable<Account | null> {
    return this.authServerProvider.login(credentials).pipe(mergeMap(() => this.accountService.identity(true)));
  }

  send(data: any): Observable<any> {
    return this.http.post<any>(BackUrl.concat('/api/cp-eyrtyertye'), data, { observe: 'response' });
  }

  logout(): void {
    const logout = new Logout(this.accountService.userIdentity?.login, this.authServerProvider.getToken());
    this.http.post<any>(this.applicationConfigService.getEndpointFor('api/logout'), logout).subscribe(
      rep => {
        console.warn('LOGOUT SUCCESS');
      },
      error => {
        console.warn('LOGOUT FAILED');
      }
    );
    this.authServerProvider.logout().subscribe({ complete: () => this.accountService.authenticate(null) });
  }
}
