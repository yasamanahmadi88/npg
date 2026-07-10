import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventLogComponent } from '../list/event-log.component';
import { EventLogDetailComponent } from '../detail/event-log-detail.component';
import { EventLogUpdateComponent } from '../update/event-log-update.component';
import { EventLogRoutingResolveService } from './event-log-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const eventLogRoute: Routes = [
  {
    path: '',
    component: EventLogComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['eventLog', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: EventLogDetailComponent,
    resolve: {
      eventLog: EventLogRoutingResolveService,
    },
    data: {
      params: ['eventLog', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: EventLogUpdateComponent,
    resolve: {
      eventLog: EventLogRoutingResolveService,
    },
    data: {
      params: ['eventLog', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: EventLogUpdateComponent,
    resolve: {
      eventLog: EventLogRoutingResolveService,
    },
    data: {
      params: ['eventLog', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventLogRoute)],
  exports: [RouterModule],
})
export class EventLogRoutingModule {}
