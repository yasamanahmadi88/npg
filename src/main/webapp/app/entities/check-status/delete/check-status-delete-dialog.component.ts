import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckStatus } from '../check-status.model';
import { CheckStatusService } from '../service/check-status.service';

@Component({
  templateUrl: './check-status-delete-dialog.component.html',
  standalone: false,
})
export class CheckStatusDeleteDialogComponent {
  checkStatus?: ICheckStatus;

  constructor(protected checkStatusService: CheckStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
