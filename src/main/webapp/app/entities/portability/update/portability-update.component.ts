import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPortability, Portability } from '../portability.model';
import { PortabilityService } from '../service/portability.service';

@Component({
  selector: 'jhi-portability-update',
  templateUrl: './portability-update.component.html',
  standalone: false,
})
export class PortabilityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    porRequestId: [null, [Validators.maxLength(32)]],
    porNumber: [null, [Validators.maxLength(16)]],
    porLegalTerm: [],
    porOpr: [null, [Validators.maxLength(50)]],
    porAccType: [null, [Validators.maxLength(16)]],
    porIdNumber: [null, [Validators.maxLength(128)]],
    porContactNumber: [null, [Validators.maxLength(16)]],
    porStatus: [null, [Validators.maxLength(50)]],
    porPortedDate: [],
    porRouting: [null, [Validators.maxLength(50)]],
    porType: [null, [Validators.maxLength(32)]],
    porOpOrg: [null, [Validators.maxLength(50)]],
    porRspCode: [null, [Validators.maxLength(20)]],
    porRspNote: [null, [Validators.maxLength(256)]],
    porCancelNote: [null, [Validators.maxLength(256)]],
    porMnpid: [null, [Validators.maxLength(32)]],
    portationDate: [],
    portaCode: [null, [Validators.maxLength(32)]],
    mvno: [null, [Validators.maxLength(50)]],
    context: [null, [Validators.maxLength(100)]],
    porErrCode: [null, [Validators.maxLength(20)]],
    porErrMessage: [null, [Validators.maxLength(256)]],
    porOpd: [null, [Validators.maxLength(50)]],
    porNumType: [null, [Validators.maxLength(20)]],
    porNote: [null, [Validators.maxLength(200)]],
    porDeadline: [],
    porResponseTimestamp: [],
    porEligible: [],
    porBillingOk: [],
    intermediaryActionState: [null, [Validators.maxLength(100)]],
    porCrDate: [],
    porUpdDate: [],
    porTechStatus: [null, [Validators.maxLength(100)]],
    porTechDeadline: [],
    refPorId: [],
    needManualRetry: [],
    retryCount: [null, [Validators.required]],
  });

  constructor(
    protected portabilityService: PortabilityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ portability }) => {
      if (portability.id === undefined) {
        const today = dayjs().startOf('day');
        portability.porPortedDate = today;
        portability.portationDate = today;
        portability.porDeadline = today;
        portability.porCrDate = today;
        portability.porUpdDate = today;
        portability.porTechDeadline = today;
      }

      this.updateForm(portability);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const portability = this.createFromForm();
    if (portability.id !== undefined) {
      this.subscribeToSaveResponse(this.portabilityService.update(portability));
    } else {
      this.subscribeToSaveResponse(this.portabilityService.create(portability));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPortability>>): void {
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

  protected updateForm(portability: IPortability): void {
    this.editForm.patchValue({
      id: portability.id,
      porRequestId: portability.porRequestId,
      porNumber: portability.porNumber,
      porLegalTerm: portability.porLegalTerm,
      porOpr: portability.porOpr,
      porAccType: portability.porAccType,
      porIdNumber: portability.porIdNumber,
      porContactNumber: portability.porContactNumber,
      porStatus: portability.porStatus,
      porPortedDate: portability.porPortedDate ? portability.porPortedDate.format(DATE_TIME_FORMAT) : null,
      porRouting: portability.porRouting,
      porType: portability.porType,
      porOpOrg: portability.porOpOrg,
      porRspCode: portability.porRspCode,
      porRspNote: portability.porRspNote,
      porCancelNote: portability.porCancelNote,
      porMnpid: portability.porMnpid,
      portationDate: portability.portationDate ? portability.portationDate.format(DATE_TIME_FORMAT) : null,
      portaCode: portability.portaCode,
      mvno: portability.mvno,
      context: portability.context,
      porErrCode: portability.porErrCode,
      porErrMessage: portability.porErrMessage,
      porOpd: portability.porOpd,
      porNumType: portability.porNumType,
      porNote: portability.porNote,
      porDeadline: portability.porDeadline ? portability.porDeadline.format(DATE_TIME_FORMAT) : null,
      porResponseTimestamp: portability.porResponseTimestamp,
      porEligible: portability.porEligible,
      porBillingOk: portability.porBillingOk,
      intermediaryActionState: portability.intermediaryActionState,
      porCrDate: portability.porCrDate ? portability.porCrDate.format(DATE_TIME_FORMAT) : null,
      porUpdDate: portability.porUpdDate ? portability.porUpdDate.format(DATE_TIME_FORMAT) : null,
      porTechStatus: portability.porTechStatus,
      porTechDeadline: portability.porTechDeadline ? portability.porTechDeadline.format(DATE_TIME_FORMAT) : null,
      refPorId: portability.refPorId,
      needManualRetry: portability.needManualRetry === 1,
      retryCount: portability.retryCount,
    });
  }

  protected createFromForm(): IPortability {
    return {
      ...new Portability(),
      id: this.editForm.get(['id'])!.value,
      porRequestId: this.editForm.get(['porRequestId'])!.value,
      porNumber: this.editForm.get(['porNumber'])!.value,
      porLegalTerm: this.editForm.get(['porLegalTerm'])!.value,
      porOpr: this.editForm.get(['porOpr'])!.value,
      porAccType: this.editForm.get(['porAccType'])!.value,
      porIdNumber: this.editForm.get(['porIdNumber'])!.value,
      porContactNumber: this.editForm.get(['porContactNumber'])!.value,
      porStatus: this.editForm.get(['porStatus'])!.value,
      porPortedDate: this.editForm.get(['porPortedDate'])!.value
        ? dayjs(this.editForm.get(['porPortedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      porRouting: this.editForm.get(['porRouting'])!.value,
      porType: this.editForm.get(['porType'])!.value,
      porOpOrg: this.editForm.get(['porOpOrg'])!.value,
      porRspCode: this.editForm.get(['porRspCode'])!.value,
      porRspNote: this.editForm.get(['porRspNote'])!.value,
      porCancelNote: this.editForm.get(['porCancelNote'])!.value,
      porMnpid: this.editForm.get(['porMnpid'])!.value,
      portationDate: this.editForm.get(['portationDate'])!.value
        ? dayjs(this.editForm.get(['portationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      portaCode: this.editForm.get(['portaCode'])!.value,
      mvno: this.editForm.get(['mvno'])!.value,
      context: this.editForm.get(['context'])!.value,
      porErrCode: this.editForm.get(['porErrCode'])!.value,
      porErrMessage: this.editForm.get(['porErrMessage'])!.value,
      porOpd: this.editForm.get(['porOpd'])!.value,
      porNumType: this.editForm.get(['porNumType'])!.value,
      porNote: this.editForm.get(['porNote'])!.value,
      porDeadline: this.editForm.get(['porDeadline'])!.value
        ? dayjs(this.editForm.get(['porDeadline'])!.value, DATE_TIME_FORMAT)
        : undefined,
      porResponseTimestamp: this.editForm.get(['porResponseTimestamp'])!.value,
      porEligible: this.editForm.get(['porEligible'])!.value,
      porBillingOk: this.editForm.get(['porBillingOk'])!.value,
      intermediaryActionState: this.editForm.get(['intermediaryActionState'])!.value,
      porCrDate: this.editForm.get(['porCrDate'])!.value ? dayjs(this.editForm.get(['porCrDate'])!.value, DATE_TIME_FORMAT) : undefined,
      porUpdDate: this.editForm.get(['porUpdDate'])!.value ? dayjs(this.editForm.get(['porUpdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      porTechStatus: this.editForm.get(['porTechStatus'])!.value,
      porTechDeadline: this.editForm.get(['porTechDeadline'])!.value
        ? dayjs(this.editForm.get(['porTechDeadline'])!.value, DATE_TIME_FORMAT)
        : undefined,
      refPorId: this.editForm.get(['refPorId'])!.value,
      needManualRetry: this.editForm.get(['needManualRetry'])!.value ? 1 : 0,
      retryCount: this.editForm.get(['retryCount'])!.value,
    };
  }
}
