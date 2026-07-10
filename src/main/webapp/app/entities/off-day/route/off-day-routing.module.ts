import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OffDayComponent } from '../list/off-day.component';
import { OffDayDetailComponent } from '../detail/off-day-detail.component';
import { OffDayUpdateComponent } from '../update/off-day-update.component';
import { OffDayRoutingResolveService } from './off-day-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const offDayRoute: Routes = [
  {
    path: '',
    component: OffDayComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['offDay', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: OffDayDetailComponent,
    data: {
      params: ['offDay', 'view'],
    },
    resolve: {
      offDay: OffDayRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: OffDayUpdateComponent,
    resolve: {
      offDay: OffDayRoutingResolveService,
    },
    data: {
      params: ['offDay', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: OffDayUpdateComponent,
    resolve: {
      offDay: OffDayRoutingResolveService,
    },
    data: {
      params: ['offDay', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(offDayRoute)],
  exports: [RouterModule],
})
export class OffDayRoutingModule {}
