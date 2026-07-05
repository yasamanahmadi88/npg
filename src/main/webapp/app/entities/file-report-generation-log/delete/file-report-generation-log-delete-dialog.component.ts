import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFileReportGenerationLog } from '../file-report-generation-log.model';
import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';

@Component({
  templateUrl: './file-report-generation-log-delete-dialog.component.html',
  standalone: false,
})
export class FileReportGenerationLogDeleteDialogComponent {
  fileReportGenerationLog?: IFileReportGenerationLog;

  constructor(protected fileReportGenerationLogService: FileReportGenerationLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fileReportGenerationLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
