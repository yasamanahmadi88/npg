import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResourceComponent } from '../list/resource.component';
import { ResourceDetailComponent } from '../detail/resource-detail.component';
import { ResourceUpdateComponent } from '../update/resource-update.component';
import { ResourceRoutingResolveService } from './resource-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const resourceRoute: Routes = [
  {
    path: '',
    component: ResourceComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['resource', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: ResourceDetailComponent,
    resolve: {
      resource: ResourceRoutingResolveService,
    },
    data: {
      params: ['resource', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: ResourceUpdateComponent,
    resolve: {
      resource: ResourceRoutingResolveService,
    },
    data: {
      params: ['resource', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: ResourceUpdateComponent,
    resolve: {
      resource: ResourceRoutingResolveService,
    },
    data: {
      params: ['resource', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resourceRoute)],
  exports: [RouterModule],
})
export class ResourceRoutingModule {}
