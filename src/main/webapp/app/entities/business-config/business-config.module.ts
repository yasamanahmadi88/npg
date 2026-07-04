import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BusinessConfigComponent } from './list/business-config.component';
import { BusinessConfigDetailComponent } from './detail/business-config-detail.component';
import { BusinessConfigUpdateComponent } from './update/business-config-update.component';
import { BusinessConfigDeleteDialogComponent } from './delete/business-config-delete-dialog.component';
import { BusinessConfigRoutingModule } from './route/business-config-routing.module';

@NgModule({
  imports: [SharedModule, BusinessConfigRoutingModule],
  declarations: [
    BusinessConfigComponent,
    BusinessConfigDetailComponent,
    BusinessConfigUpdateComponent,
    BusinessConfigDeleteDialogComponent,
  ],
})
export class BusinessConfigModule {}
