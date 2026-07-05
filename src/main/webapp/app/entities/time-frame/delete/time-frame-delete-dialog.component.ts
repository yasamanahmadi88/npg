import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeFrame } from '../time-frame.model';
import { TimeFrameService } from '../service/time-frame.service';

@Component({
  templateUrl: './time-frame-delete-dialog.component.html',
  standalone: false,
})
export class TimeFrameDeleteDialogComponent {
  timeFrame?: ITimeFrame;

  constructor(protected timeFrameService: TimeFrameService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.timeFrameService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
