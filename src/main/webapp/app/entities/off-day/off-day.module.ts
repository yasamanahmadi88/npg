import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OffDayComponent } from './list/off-day.component';
import { OffDayDetailComponent } from './detail/off-day-detail.component';
import { OffDayUpdateComponent } from './update/off-day-update.component';
import { OffDayDeleteDialogComponent } from './delete/off-day-delete-dialog.component';
import { OffDayRoutingModule } from './route/off-day-routing.module';

@NgModule({
  imports: [SharedModule, OffDayRoutingModule],
  declarations: [OffDayComponent, OffDayDetailComponent, OffDayUpdateComponent, OffDayDeleteDialogComponent],
})
export class OffDayModule {}
