import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthorityService } from '../service/authority.service';
import { IAuthority, Authority } from '../authority.model';

@Component({
  selector: 'jhi-authority-update',
  templateUrl: './authority-update.component.html',
  standalone: false,
})
export class AuthorityUpdateComponent implements OnInit {
  isSaving = false;
  authorities: IAuthority[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    displayName: [null, [Validators.maxLength(500)]],
    parentId: [],
  });

  constructor(protected authorityService: AuthorityService, protected activatedRoute: ActivatedRoute, private fb: UntypedFormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authority }) => {
      this.updateForm(authority);
      this.authorityService.query().subscribe((res: HttpResponse<IAuthority[]>) => (this.authorities = res.body ?? []));
    });
  }

  updateForm(authority: IAuthority): void {
    this.editForm.patchValue({
      id: authority.id,
      name: authority.name,
      displayName: authority.displayName,
      parentId: authority.parentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const authority = this.createFromForm();
    if (authority.id !== undefined) {
      this.subscribeToSaveResponse(this.authorityService.update(authority));
    } else {
      this.subscribeToSaveResponse(this.authorityService.create(authority));
    }
  }

  trackById(index: number, item: IAuthority): any {
    return item.id;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthority>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  private createFromForm(): IAuthority {
    return {
      ...new Authority(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      displayName: this.editForm.get(['displayName'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
    };
  }
}
