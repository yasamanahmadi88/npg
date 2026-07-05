import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DayOfWeekTimeFrameComponent } from './list/day-of-week-time-frame.component';
import { DayOfWeekTimeFrameDetailComponent } from './detail/day-of-week-time-frame-detail.component';
import { DayOfWeekTimeFrameUpdateComponent } from './update/day-of-week-time-frame-update.component';
import { DayOfWeekTimeFrameDeleteDialogComponent } from './delete/day-of-week-time-frame-delete-dialog.component';
import { DayOfWeekTimeFrameRoutingModule } from './route/day-of-week-time-frame-routing.module';
import { DpDatePickerCompatModule as DpDatePickerModule } from 'app/shared/date-picker/dp-date-picker-compat.module';
import { NpgPortalCommonModule } from '../../common/common.module';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [SharedModule, CommonModule, DayOfWeekTimeFrameRoutingModule, DpDatePickerModule, NpgPortalCommonModule],
  declarations: [
    DayOfWeekTimeFrameComponent,
    DayOfWeekTimeFrameDetailComponent,
    DayOfWeekTimeFrameUpdateComponent,
    DayOfWeekTimeFrameDeleteDialogComponent,
  ],
})
export class DayOfWeekTimeFrameModule {}
