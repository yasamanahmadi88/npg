import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOffDay } from '../off-day.model';
import { OffDayService } from '../service/off-day.service';

@Component({
  templateUrl: './off-day-delete-dialog.component.html',
  standalone: false,
})
export class OffDayDeleteDialogComponent {
  offDay?: IOffDay;

  constructor(protected offDayService: OffDayService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offDayService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
