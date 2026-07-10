import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettingComponent } from '../list/setting.component';
import { SettingDetailComponent } from '../detail/setting-detail.component';
import { SettingUpdateComponent } from '../update/setting-update.component';
import { SettingRoutingResolveService } from './setting-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const settingRoute: Routes = [
  {
    path: '',
    component: SettingComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['setting', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: SettingDetailComponent,
    resolve: {
      setting: SettingRoutingResolveService,
    },
    data: {
      params: ['setting', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: SettingUpdateComponent,
    resolve: {
      setting: SettingRoutingResolveService,
    },
    data: {
      params: ['setting', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: SettingUpdateComponent,
    resolve: {
      setting: SettingRoutingResolveService,
    },
    data: {
      params: ['setting', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(settingRoute)],
  exports: [RouterModule],
})
export class SettingRoutingModule {}
