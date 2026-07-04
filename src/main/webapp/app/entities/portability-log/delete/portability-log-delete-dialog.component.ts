import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPortabilityLog } from '../portability-log.model';
import { PortabilityLogService } from '../service/portability-log.service';

@Component({
  templateUrl: './portability-log-delete-dialog.component.html',
  standalone: false,
})
export class PortabilityLogDeleteDialogComponent {
  portabilityLog?: IPortabilityLog;

  constructor(protected portabilityLogService: PortabilityLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.portabilityLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
