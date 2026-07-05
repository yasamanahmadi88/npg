import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotificationTemplateComponent } from '../list/notification-template.component';
import { NotificationTemplateDetailComponent } from '../detail/notification-template-detail.component';
import { NotificationTemplateUpdateComponent } from '../update/notification-template-update.component';
import { NotificationTemplateRoutingResolveService } from './notification-template-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const notificationTemplateRoute: Routes = [
  {
    path: '',
    component: NotificationTemplateComponent,
    data: {
      params: ['notificationTemplate', 'view'],
      defaultSort: 'id,asc',
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: NotificationTemplateDetailComponent,
    resolve: {
      notificationTemplate: NotificationTemplateRoutingResolveService,
    },
    data: {
      params: ['notificationTemplate', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: NotificationTemplateUpdateComponent,
    data: {
      params: ['notificationTemplate', 'create'],
    },
    resolve: {
      notificationTemplate: NotificationTemplateRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: NotificationTemplateUpdateComponent,
    data: {
      params: ['notificationTemplate', 'edit'],
    },
    resolve: {
      notificationTemplate: NotificationTemplateRoutingResolveService,
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(notificationTemplateRoute)],
  exports: [RouterModule],
})
export class NotificationTemplateRoutingModule {}
