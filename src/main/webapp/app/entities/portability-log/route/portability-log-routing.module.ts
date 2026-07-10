import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortabilityLogComponent } from '../list/portability-log.component';
import { PortabilityLogDetailComponent } from '../detail/portability-log-detail.component';
import { PortabilityLogUpdateComponent } from '../update/portability-log-update.component';
import { PortabilityLogRoutingResolveService } from './portability-log-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const portabilityLogRoute: Routes = [
  {
    path: '',
    component: PortabilityLogComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['portabilityLog', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: PortabilityLogDetailComponent,
    resolve: {
      portabilityLog: PortabilityLogRoutingResolveService,
    },
    data: {
      params: ['portabilityLog', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: PortabilityLogUpdateComponent,
    resolve: {
      portabilityLog: PortabilityLogRoutingResolveService,
    },
    data: {
      params: ['portabilityLog', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: PortabilityLogUpdateComponent,
    resolve: {
      portabilityLog: PortabilityLogRoutingResolveService,
    },
    data: {
      params: ['portabilityLog', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(portabilityLogRoute)],
  exports: [RouterModule],
})
export class PortabilityLogRoutingModule {}
