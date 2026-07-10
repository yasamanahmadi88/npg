import { Routes } from '@angular/router';
import { SessionManagementComponent } from './list/session-management.component';

export const sessionManagementRoute: Routes = [
  {
    path: '',
    component: SessionManagementComponent,
    data: {
      pageTitle: 'global.menu.admin.sessionManagement',
    },
  },
];
