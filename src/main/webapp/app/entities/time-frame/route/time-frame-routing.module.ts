import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TimeFrameComponent } from '../list/time-frame.component';
import { TimeFrameDetailComponent } from '../detail/time-frame-detail.component';
import { TimeFrameUpdateComponent } from '../update/time-frame-update.component';
import { TimeFrameRoutingResolveService } from './time-frame-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const timeFrameRoute: Routes = [
  {
    path: '',
    component: TimeFrameComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['timeFrame', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: TimeFrameDetailComponent,
    resolve: {
      timeFrame: TimeFrameRoutingResolveService,
    },
    data: {
      params: ['timeFrame', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: TimeFrameUpdateComponent,
    resolve: {
      timeFrame: TimeFrameRoutingResolveService,
    },
    data: {
      params: ['timeFrame', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: TimeFrameUpdateComponent,
    resolve: {
      timeFrame: TimeFrameRoutingResolveService,
    },
    data: {
      params: ['timeFrame', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(timeFrameRoute)],
  exports: [RouterModule],
})
export class TimeFrameRoutingModule {}
