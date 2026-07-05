import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUndifinedStatus } from '../undifined-status.model';
import { UndifinedStatusService } from '../service/undifined-status.service';

@Component({
  templateUrl: './undifined-status-delete-dialog.component.html',
  standalone: false,
})
export class UndifinedStatusDeleteDialogComponent {
  undifinedStatus?: IUndifinedStatus;

  constructor(protected undifinedStatusService: UndifinedStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.undifinedStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
