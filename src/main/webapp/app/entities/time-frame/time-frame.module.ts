import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TimeFrameComponent } from './list/time-frame.component';
import { TimeFrameDetailComponent } from './detail/time-frame-detail.component';
import { TimeFrameUpdateComponent } from './update/time-frame-update.component';
import { TimeFrameDeleteDialogComponent } from './delete/time-frame-delete-dialog.component';
import { TimeFrameRoutingModule } from './route/time-frame-routing.module';
import { DpDatePickerCompatModule as DpDatePickerModule } from 'app/shared/date-picker/dp-date-picker-compat.module';
import { NpgPortalCommonModule } from '../../common/common.module';

@NgModule({
  imports: [SharedModule, TimeFrameRoutingModule, DpDatePickerModule, NpgPortalCommonModule],
  declarations: [TimeFrameComponent, TimeFrameDetailComponent, TimeFrameUpdateComponent, TimeFrameDeleteDialogComponent],
})
export class TimeFrameModule {}
