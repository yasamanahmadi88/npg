import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckStatusComponent } from './list/check-status.component';
import { CheckStatusDetailComponent } from './detail/check-status-detail.component';
import { CheckStatusUpdateComponent } from './update/check-status-update.component';
import { CheckStatusDeleteDialogComponent } from './delete/check-status-delete-dialog.component';
import { CheckStatusRoutingModule } from './route/check-status-routing.module';

@NgModule({
  imports: [SharedModule, CheckStatusRoutingModule],
  declarations: [CheckStatusComponent, CheckStatusDetailComponent, CheckStatusUpdateComponent, CheckStatusDeleteDialogComponent],
})
export class CheckStatusModule {}
