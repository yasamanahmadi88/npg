import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PortabilityComponent } from './list/portability.component';
import { PortabilityDetailComponent } from './detail/portability-detail.component';
import { PortabilityUpdateComponent } from './update/portability-update.component';
import { PortabilityDeleteDialogComponent } from './delete/portability-delete-dialog.component';
import { PortabilityRoutingModule } from './route/portability-routing.module';
import { DpDatePickerCompatModule as DpDatePickerModule } from 'app/shared/date-picker/dp-date-picker-compat.module';
import { PortabilityLogDialogComponent } from './portability-log-dialog/portability-log-dialog.component';
import { PortabilitySearchComponent } from './portabilty-search/portability-search.component';
import { MatSortModule } from '@angular/material/sort';

@NgModule({
  imports: [SharedModule, PortabilityRoutingModule, DpDatePickerModule, MatSortModule],
  declarations: [
    PortabilityComponent,
    PortabilityDetailComponent,
    PortabilityUpdateComponent,
    PortabilityDeleteDialogComponent,
    PortabilitySearchComponent,
    PortabilityLogDialogComponent,
    PortabilityLogDialogComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PortabilityModule {}
