import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUndifinedStatus, UndifinedStatus } from '../undifined-status.model';
import { UndifinedStatusService } from '../service/undifined-status.service';

@Component({
  selector: 'jhi-undifined-status-update',
  templateUrl: './undifined-status-update.component.html',
  standalone: false,
})
export class UndifinedStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    porStatus: [null, [Validators.maxLength(4000)]],
    porTechStatus: [null, [Validators.maxLength(4000)]],
    porErrCode: [null, [Validators.maxLength(4000)]],
    porRspCode: [null, [Validators.maxLength(4000)]],
    porRequestId: [null, [Validators.maxLength(4000)]],
    insertDate: [null, [Validators.required]],
  });

  constructor(
    protected undifinedStatusService: UndifinedStatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ undifinedStatus }) => {
      if (undifinedStatus.id === undefined) {
        const today = dayjs().startOf('day');
        undifinedStatus.insertDate = today;
      }

      this.updateForm(undifinedStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const undifinedStatus = this.createFromForm();
    if (undifinedStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.undifinedStatusService.update(undifinedStatus));
    } else {
      this.subscribeToSaveResponse(this.undifinedStatusService.create(undifinedStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUndifinedStatus>>): void {
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

  protected updateForm(undifinedStatus: IUndifinedStatus): void {
    this.editForm.patchValue({
      id: undifinedStatus.id,
      porStatus: undifinedStatus.porStatus,
      porTechStatus: undifinedStatus.porTechStatus,
      porErrCode: undifinedStatus.porErrCode,
      porRspCode: undifinedStatus.porRspCode,
      porRequestId: undifinedStatus.porRequestId,
      insertDate: undifinedStatus.insertDate ? undifinedStatus.insertDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IUndifinedStatus {
    return {
      ...new UndifinedStatus(),
      id: this.editForm.get(['id'])!.value,
      porStatus: this.editForm.get(['porStatus'])!.value,
      porTechStatus: this.editForm.get(['porTechStatus'])!.value,
      porErrCode: this.editForm.get(['porErrCode'])!.value,
      porRspCode: this.editForm.get(['porRspCode'])!.value,
      porRequestId: this.editForm.get(['porRequestId'])!.value,
      insertDate: this.editForm.get(['insertDate'])!.value ? dayjs(this.editForm.get(['insertDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
