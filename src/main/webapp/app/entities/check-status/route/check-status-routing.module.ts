import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CheckStatusComponent } from '../list/check-status.component';
import { CheckStatusDetailComponent } from '../detail/check-status-detail.component';
import { CheckStatusUpdateComponent } from '../update/check-status-update.component';
import { CheckStatusRoutingResolveService } from './check-status-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const checkStatusRoute: Routes = [
  {
    path: '',
    component: CheckStatusComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['checkStatus', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: CheckStatusDetailComponent,
    resolve: {
      checkStatus: CheckStatusRoutingResolveService,
    },
    data: {
      params: ['checkStatus', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: CheckStatusUpdateComponent,
    data: {
      params: ['checkStatus', 'create'],
    },
    resolve: {
      checkStatus: CheckStatusRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: CheckStatusUpdateComponent,
    data: {
      params: ['checkStatus', 'edit'],
    },
    resolve: {
      checkStatus: CheckStatusRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkStatusRoute)],
  exports: [RouterModule],
})
export class CheckStatusRoutingModule {}
