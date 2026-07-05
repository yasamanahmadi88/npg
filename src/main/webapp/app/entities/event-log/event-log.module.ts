import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventLogComponent } from './list/event-log.component';
import { EventLogDetailComponent } from './detail/event-log-detail.component';
import { EventLogUpdateComponent } from './update/event-log-update.component';
import { EventLogDeleteDialogComponent } from './delete/event-log-delete-dialog.component';
import { EventLogRoutingModule } from './route/event-log-routing.module';
import { DpDatePickerCompatModule as DpDatePickerModule } from 'app/shared/date-picker/dp-date-picker-compat.module';
import { MatRadioModule } from '@angular/material/radio';
import { NpgPortalCommonModule } from '../../common/common.module';
import { MatSortModule } from '@angular/material/sort';

@NgModule({
  imports: [SharedModule, EventLogRoutingModule, DpDatePickerModule, MatRadioModule, NpgPortalCommonModule, MatSortModule],
  declarations: [EventLogComponent, EventLogDetailComponent, EventLogUpdateComponent, EventLogDeleteDialogComponent],
})
export class EventLogModule {}
