import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessConfig } from '../business-config.model';
import { BusinessConfigService } from '../service/business-config.service';

@Component({
  templateUrl: './business-config-delete-dialog.component.html',
  standalone: false,
})
export class BusinessConfigDeleteDialogComponent {
  businessConfig?: IBusinessConfig;

  constructor(protected businessConfigService: BusinessConfigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.businessConfigService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
