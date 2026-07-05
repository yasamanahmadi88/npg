import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPortability } from '../portability.model';
import { PortabilityService } from '../service/portability.service';

@Component({
  templateUrl: './portability-delete-dialog.component.html',
  standalone: false,
})
export class PortabilityDeleteDialogComponent {
  portability?: IPortability;

  constructor(protected portabilityService: PortabilityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.portabilityService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
