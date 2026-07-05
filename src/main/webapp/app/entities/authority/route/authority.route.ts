import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { AuthorityService } from '../service/authority.service';
import { AuthorityComponent } from '../list/authority.component';
import { AuthorityDetailComponent } from '../detail/authority-detail.component';
import { AuthorityUpdateComponent } from '../update/authority-update.component';
import { Authority, IAuthority } from '../authority.model';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

@Injectable({ providedIn: 'root' })
export class AuthorityResolve {
  constructor(private service: AuthorityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuthority> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((authority: HttpResponse<Authority>) => {
          if (authority.body) {
            return of(authority.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Authority());
  }
}

export const authorityRoute: Routes = [
  {
    path: '',
    component: AuthorityComponent,
    data: {
      defaultSort: 'id,asc',
      pageTitle: 'npgPortalApp.authority.home.title',
      params: ['authority', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: AuthorityDetailComponent,
    resolve: {
      authority: AuthorityResolve,
    },
    data: {
      params: ['authority', 'view'],
      pageTitle: 'npgPortalApp.authority.home.title',
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: AuthorityUpdateComponent,
    resolve: {
      authority: AuthorityResolve,
    },
    data: {
      pageTitle: 'npgPortalApp.authority.home.title',
      params: ['authority', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: AuthorityUpdateComponent,
    resolve: {
      authority: AuthorityResolve,
    },
    data: {
      pageTitle: 'npgPortalApp.authority.home.title',
      params: ['authority', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];
