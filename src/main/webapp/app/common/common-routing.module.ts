import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortabilityDashboardComponent } from './portability-dashboard/portability-dashboard.component';
import { UserRouteAccessService } from '../core/auth/user-route-access.service';

const commonRoute: Routes = [
  {
    path: 'dashboard',
    component: PortabilityDashboardComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commonRoute)],
  exports: [RouterModule],
})
export class CommonRoutingModule {}
