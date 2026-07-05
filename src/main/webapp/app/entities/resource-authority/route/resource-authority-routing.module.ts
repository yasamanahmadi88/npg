import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResourceAuthorityComponent } from '../list/resource-authority.component';
import { ResourceAuthorityDetailComponent } from '../detail/resource-authority-detail.component';
import { ResourceAuthorityUpdateComponent } from '../update/resource-authority-update.component';
import { ResourceAuthorityRoutingResolveService } from './resource-authority-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const resourceAuthorityRoute: Routes = [
  {
    path: '',
    component: ResourceAuthorityComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['resourceAuthority', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: ResourceAuthorityDetailComponent,
    resolve: {
      resourceAuthority: ResourceAuthorityRoutingResolveService,
    },
    data: {
      params: ['resourceAuthority', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: ResourceAuthorityUpdateComponent,
    resolve: {
      resourceAuthority: ResourceAuthorityRoutingResolveService,
    },
    data: {
      params: ['resourceAuthority', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: ResourceAuthorityUpdateComponent,
    resolve: {
      resourceAuthority: ResourceAuthorityRoutingResolveService,
    },
    data: {
      params: ['resourceAuthority', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resourceAuthorityRoute)],
  exports: [RouterModule],
})
export class ResourceAuthorityRoutingModule {}
