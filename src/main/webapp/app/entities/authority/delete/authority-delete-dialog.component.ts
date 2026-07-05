import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthorityService } from '../service/authority.service';
import { IAuthority } from '../authority.model';

@Component({
  templateUrl: './authority-delete-dialog.component.html',
  standalone: false,
})
export class AuthorityDeleteDialogComponent {
  authority?: IAuthority;

  constructor(
    protected authorityService: AuthorityService,
    public activeModal: NgbActiveModal // protected toastr: ToastrService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.authorityService.delete(id).subscribe(() => {
      this.activeModal.close();
    });
  }
}
