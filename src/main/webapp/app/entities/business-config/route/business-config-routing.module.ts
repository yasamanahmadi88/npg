import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusinessConfigComponent } from '../list/business-config.component';
import { BusinessConfigDetailComponent } from '../detail/business-config-detail.component';
import { BusinessConfigUpdateComponent } from '../update/business-config-update.component';
import { BusinessConfigRoutingResolveService } from './business-config-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const businessConfigRoute: Routes = [
  {
    path: '',
    component: BusinessConfigComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['businessConfig', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: BusinessConfigDetailComponent,
    data: {
      params: ['businessConfig', 'view'],
    },
    resolve: {
      businessConfig: BusinessConfigRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: BusinessConfigUpdateComponent,
    data: {
      params: ['businessConfig', 'create'],
    },
    resolve: {
      businessConfig: BusinessConfigRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: BusinessConfigUpdateComponent,
    data: {
      params: ['businessConfig', 'edit'],
    },
    resolve: {
      businessConfig: BusinessConfigRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(businessConfigRoute)],
  exports: [RouterModule],
})
export class BusinessConfigRoutingModule {}
