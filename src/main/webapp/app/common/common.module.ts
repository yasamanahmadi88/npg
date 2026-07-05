import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BooleanAnswerPipe } from './boolean-answer.pipe';
import { ActionTypePipe } from 'app/common/action-type.pipe';
import { CommonRoutingModule } from 'app/common/common-routing.module';

import { NotificationLanguagePipe } from './notification-language.pipe';
import { NotificationTypePipe } from './notification-type.pipe';
import { SharedModule } from '../shared/shared.module';
import { JalaliFormatterPipe } from './jalali-pipe';
import { PortabilityDashboardComponent } from './portability-dashboard/portability-dashboard.component';
import { BarChartModule, PieChartModule } from '@swimlane/ngx-charts';
import { DpDatePickerCompatModule as DpDatePickerModule } from 'app/shared/date-picker/dp-date-picker-compat.module';
import { NumberToTimePipe } from './numberToTime.pipe';
import { EventLogStatusPipe } from './eventLogStatus';

@NgModule({
  imports: [RouterModule.forChild([]), SharedModule, BarChartModule, PieChartModule, CommonRoutingModule, DpDatePickerModule],
  declarations: [
    NumberToTimePipe,
    PortabilityDashboardComponent,
    BooleanAnswerPipe,
    ActionTypePipe,
    NotificationLanguagePipe,
    NotificationTypePipe,
    JalaliFormatterPipe,
    EventLogStatusPipe,
  ],
  exports: [
    NumberToTimePipe,
    PortabilityDashboardComponent,
    BooleanAnswerPipe,
    ActionTypePipe,
    NotificationLanguagePipe,
    NotificationTypePipe,
    JalaliFormatterPipe,
    EventLogStatusPipe,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class NpgPortalCommonModule {}
