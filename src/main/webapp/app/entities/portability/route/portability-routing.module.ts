import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortabilityComponent } from '../list/portability.component';
import { PortabilityDetailComponent } from '../detail/portability-detail.component';
import { PortabilityUpdateComponent } from '../update/portability-update.component';
import { PortabilityRoutingResolveService } from './portability-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';
import { PorReqIdResolveService } from './porRequest-resolve.service';
import {PortabilityGlobalSearchResolveService} from "./portability-global-search-resolve.service";

const portabilityRoute: Routes = [
  {
    path: '',
    component: PortabilityComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['portability', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: PortabilityUpdateComponent,
    resolve: {
      portability: PortabilityRoutingResolveService,
    },
    data: {
      params: ['portability', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':porRequestId/view',
    component: PortabilityDetailComponent,
    resolve: {
      portability: PorReqIdResolveService,
    },
    data: {
      params: ['portability', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':porNumber',
    component: PortabilityComponent,
    resolve: {
      portability: PortabilityGlobalSearchResolveService,
    },
    data: {
      params: ['portability', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':porRequestId/edit',
    component: PortabilityUpdateComponent,
    resolve: {
      portability: PorReqIdResolveService,
    },
    data: {
      params: ['portability', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(portabilityRoute)],
  exports: [RouterModule],
})
export class PortabilityRoutingModule {}
