import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICheckStatus, CheckStatus } from '../check-status.model';
import { CheckStatusService } from '../service/check-status.service';

@Component({
  selector: 'jhi-check-status-update',
  templateUrl: './check-status-update.component.html',
  standalone: false,
})
export class CheckStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    porStatus: [null, [Validators.required, Validators.maxLength(200)]],
    porTechStatus: [null, [Validators.maxLength(200)]],
    porErrCode: [null, [Validators.maxLength(100)]],
    porRspCode: [null, [Validators.maxLength(100)]],
    statusMessageFa: [null, [Validators.required, Validators.maxLength(4000)]],
    statusMessageEn: [null, [Validators.maxLength(4000)]],
  });

  constructor(
    protected checkStatusService: CheckStatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkStatus }) => {
      this.updateForm(checkStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkStatus = this.createFromForm();
    if (checkStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.checkStatusService.update(checkStatus));
    } else {
      this.subscribeToSaveResponse(this.checkStatusService.create(checkStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckStatus>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(checkStatus: ICheckStatus): void {
    this.editForm.patchValue({
      id: checkStatus.id,
      porStatus: checkStatus.porStatus,
      porTechStatus: checkStatus.porTechStatus,
      porErrCode: checkStatus.porErrCode,
      porRspCode: checkStatus.porRspCode,
      statusMessageFa: checkStatus.statusMessageFa,
      statusMessageEn: checkStatus.statusMessageEn,
    });
  }

  protected createFromForm(): ICheckStatus {
    return {
      ...new CheckStatus(),
      id: this.editForm.get(['id'])!.value,
      porStatus: this.editForm.get(['porStatus'])!.value,
      porTechStatus: this.editForm.get(['porTechStatus'])!.value,
      porErrCode: this.editForm.get(['porErrCode'])!.value,
      porRspCode: this.editForm.get(['porRspCode'])!.value,
      statusMessageFa: this.editForm.get(['statusMessageFa'])!.value,
      statusMessageEn: this.editForm.get(['statusMessageEn'])!.value,
    };
  }
}
