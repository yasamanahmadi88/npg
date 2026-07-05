import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';
import { CrmToCrdbResponseMapService } from '../service/crm-to-crdb-response-map.service';

@Component({
  templateUrl: './crm-to-crdb-response-map-delete-dialog.component.html',
  standalone: false,
})
export class CrmToCrdbResponseMapDeleteDialogComponent {
  crmToCrdbResponseMap?: ICrmToCrdbResponseMap;

  constructor(protected crmToCrdbResponseMapService: CrmToCrdbResponseMapService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.crmToCrdbResponseMapService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
