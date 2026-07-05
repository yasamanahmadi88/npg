import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UndifinedStatusComponent } from '../list/undifined-status.component';
import { UndifinedStatusDetailComponent } from '../detail/undifined-status-detail.component';
import { UndifinedStatusUpdateComponent } from '../update/undifined-status-update.component';
import { UndifinedStatusRoutingResolveService } from './undifined-status-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const undifinedStatusRoute: Routes = [
  {
    path: '',
    component: UndifinedStatusComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['undifinedStatus', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: UndifinedStatusDetailComponent,
    resolve: {
      undifinedStatus: UndifinedStatusRoutingResolveService,
    },
    data: {
      params: ['undifinedStatus', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: UndifinedStatusUpdateComponent,
    resolve: {
      undifinedStatus: UndifinedStatusRoutingResolveService,
    },
    data: {
      params: ['undifinedStatus', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: UndifinedStatusUpdateComponent,
    resolve: {
      undifinedStatus: UndifinedStatusRoutingResolveService,
    },
    data: {
      params: ['undifinedStatus', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(undifinedStatusRoute)],
  exports: [RouterModule],
})
export class UndifinedStatusRoutingModule {}
