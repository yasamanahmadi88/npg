import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FileReportGenerationLogComponent } from './list/file-report-generation-log.component';
import { FileReportGenerationLogDetailComponent } from './detail/file-report-generation-log-detail.component';
import { FileReportGenerationLogUpdateComponent } from './update/file-report-generation-log-update.component';
import { FileReportGenerationLogDeleteDialogComponent } from './delete/file-report-generation-log-delete-dialog.component';
import { FileReportGenerationLogRoutingModule } from './route/file-report-generation-log-routing.module';
import { MatSortModule } from '@angular/material/sort';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';

@NgModule({
  imports: [SharedModule, FileReportGenerationLogRoutingModule, MatSortModule, MatDatepickerModule, MatFormFieldModule, MatNativeDateModule, MatRadioModule],
  declarations: [
    FileReportGenerationLogComponent,
    FileReportGenerationLogDetailComponent,
    FileReportGenerationLogUpdateComponent,
    FileReportGenerationLogDeleteDialogComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class FileReportGenerationLogModule {}
