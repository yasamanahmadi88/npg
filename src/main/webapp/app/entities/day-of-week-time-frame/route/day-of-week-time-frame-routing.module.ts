import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DayOfWeekTimeFrameComponent } from '../list/day-of-week-time-frame.component';
import { DayOfWeekTimeFrameDetailComponent } from '../detail/day-of-week-time-frame-detail.component';
import { DayOfWeekTimeFrameUpdateComponent } from '../update/day-of-week-time-frame-update.component';
import { DayOfWeekTimeFrameRoutingResolveService } from './day-of-week-time-frame-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const dayOfWeekTimeFrameRoute: Routes = [
  {
    path: '',
    component: DayOfWeekTimeFrameComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['DayOfWeekTimeFrame', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: DayOfWeekTimeFrameDetailComponent,
    data: {
      params: ['dayOfWeekTimeFrame', 'view'],
    },
    resolve: {
      dayOfWeekTimeFrame: DayOfWeekTimeFrameRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: DayOfWeekTimeFrameUpdateComponent,
    data: {
      params: ['dayOfWeekTimeFrame', 'create'],
      pageTitle: 'global.menu.site-map',
    },
    resolve: {
      dayOfWeekTimeFrame: DayOfWeekTimeFrameRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: DayOfWeekTimeFrameUpdateComponent,
    data: {
      params: ['dayOfWeekTimeFrame', 'edit'],
    },
    resolve: {
      dayOfWeekTimeFrame: DayOfWeekTimeFrameRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dayOfWeekTimeFrameRoute)],
  exports: [RouterModule],
})
export class DayOfWeekTimeFrameRoutingModule {}
