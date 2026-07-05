import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CrmToCrdbResponseMapComponent } from './list/crm-to-crdb-response-map.component';
import { CrmToCrdbResponseMapDetailComponent } from './detail/crm-to-crdb-response-map-detail.component';
import { CrmToCrdbResponseMapUpdateComponent } from './update/crm-to-crdb-response-map-update.component';
import { CrmToCrdbResponseMapDeleteDialogComponent } from './delete/crm-to-crdb-response-map-delete-dialog.component';
import { CrmToCrdbResponseMapRoutingModule } from './route/crm-to-crdb-response-map-routing.module';

@NgModule({
  imports: [SharedModule, CrmToCrdbResponseMapRoutingModule],
  declarations: [
    CrmToCrdbResponseMapComponent,
    CrmToCrdbResponseMapDetailComponent,
    CrmToCrdbResponseMapUpdateComponent,
    CrmToCrdbResponseMapDeleteDialogComponent,
  ],
})
export class CrmToCrdbResponseMapModule {}
