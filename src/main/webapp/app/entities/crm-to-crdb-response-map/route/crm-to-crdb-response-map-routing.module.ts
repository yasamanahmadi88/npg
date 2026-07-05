import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CrmToCrdbResponseMapComponent } from '../list/crm-to-crdb-response-map.component';
import { CrmToCrdbResponseMapDetailComponent } from '../detail/crm-to-crdb-response-map-detail.component';
import { CrmToCrdbResponseMapUpdateComponent } from '../update/crm-to-crdb-response-map-update.component';
import { CrmToCrdbResponseMapRoutingResolveService } from './crm-to-crdb-response-map-routing-resolve.service';
import { AuthActivateService } from '../../../core/auth/auth-activate.service';

const crmToCrdbResponseMapRoute: Routes = [
  {
    path: '',
    component: CrmToCrdbResponseMapComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['crmToCrdbResponseMap', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: CrmToCrdbResponseMapDetailComponent,
    resolve: {
      crmToCrdbResponseMap: CrmToCrdbResponseMapRoutingResolveService,
    },
    data: {
      params: ['crmToCrdbResponseMap', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: CrmToCrdbResponseMapUpdateComponent,
    resolve: {
      crmToCrdbResponseMap: CrmToCrdbResponseMapRoutingResolveService,
    },
    data: {
      params: ['crmToCrdbResponseMap', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: CrmToCrdbResponseMapUpdateComponent,
    resolve: {
      crmToCrdbResponseMap: CrmToCrdbResponseMapRoutingResolveService,
    },
    data: {
      params: ['crmToCrdbResponseMap', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(crmToCrdbResponseMapRoute)],
  exports: [RouterModule],
})
export class CrmToCrdbResponseMapRoutingModule {}
