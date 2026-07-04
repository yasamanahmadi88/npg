import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDayOfWeekTimeFrame } from '../day-of-week-time-frame.model';
import { DayOfWeekTimeFrameService } from '../service/day-of-week-time-frame.service';

@Component({
  templateUrl: './day-of-week-time-frame-delete-dialog.component.html',
  standalone: false,
})
export class DayOfWeekTimeFrameDeleteDialogComponent {
  dayOfWeekTimeFrame?: IDayOfWeekTimeFrame;

  constructor(protected dayOfWeekTimeFrameService: DayOfWeekTimeFrameService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dayOfWeekTimeFrameService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
