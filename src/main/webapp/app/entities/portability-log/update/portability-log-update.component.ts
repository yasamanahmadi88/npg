import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPortabilityLog, PortabilityLog } from '../portability-log.model';
import { PortabilityLogService } from '../service/portability-log.service';

@Component({
  selector: 'jhi-portability-log-update',
  templateUrl: './portability-log-update.component.html',
  standalone: false,
})
export class PortabilityLogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    porId: [null, [Validators.required]],
    porRequestId: [null, [Validators.maxLength(26)]],
    porNumber: [null, [Validators.maxLength(16)]],
    porLegalTerm: [],
    porOpr: [null, [Validators.maxLength(50)]],
    porAccType: [null, [Validators.maxLength(16)]],
    porIdNumber: [null, [Validators.maxLength(20)]],
    porContactNumber: [null, [Validators.maxLength(20)]],
    porStatus: [null, [Validators.maxLength(50)]],
    porPortedDate: [],
    porRouting: [null, [Validators.maxLength(50)]],
    porType: [null, [Validators.maxLength(32)]],
    porOpOrg: [null, [Validators.maxLength(50)]],
    porRspCode: [null, [Validators.maxLength(20)]],
    porRspNote: [null, [Validators.maxLength(500)]],
    porCancelNote: [null, [Validators.maxLength(500)]],
    porMnpid: [null, [Validators.maxLength(26)]],
    portationDate: [],
    portaCode: [null, [Validators.maxLength(32)]],
    mvno: [null, [Validators.maxLength(50)]],
    context: [null, [Validators.maxLength(1000)]],
    porErrCode: [null, [Validators.maxLength(255)]],
    porErrMessage: [null, [Validators.maxLength(4000)]],
    porOpd: [null, [Validators.maxLength(50)]],
    porNumType: [null, [Validators.maxLength(20)]],
    porNote: [null, [Validators.maxLength(500)]],
    porDeadline: [],
    porResponseTimestamp: [],
    porEligible: [],
    porBillingOk: [],
    intermediaryActionState: [null, [Validators.maxLength(200)]],
    porCrDate: [],
    porUpdDate: [],
    porTechStatus: [null, [Validators.maxLength(100)]],
    porTechDeadline: [],
    refPorId: [],
    needManualRetry: [],
    retryCount: [null, [Validators.required]],
    action: [null, [Validators.maxLength(100)]],
    request: [],
    insertTimestamp: [null, [Validators.required]],
  });

  constructor(
    protected portabilityLogService: PortabilityLogService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ portabilityLog }) => {
      if (portabilityLog.id === undefined) {
        const today = dayjs().startOf('day');
        portabilityLog.porPortedDate = today;
        portabilityLog.portationDate = today;
        portabilityLog.porDeadline = today;
        portabilityLog.porCrDate = today;
        portabilityLog.porUpdDate = today;
        portabilityLog.porTechDeadline = today;
        portabilityLog.insertTimestamp = today;
      }

      this.updateForm(portabilityLog);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const portabilityLog = this.createFromForm();
    if (portabilityLog.id !== undefined) {
      this.subscribeToSaveResponse(this.portabilityLogService.update(portabilityLog));
    } else {
      this.subscribeToSaveResponse(this.portabilityLogService.create(portabilityLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPortabilityLog>>): void {
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

  protected updateForm(portabilityLog: IPortabilityLog): void {
    this.editForm.patchValue({
      id: portabilityLog.id,
      porId: portabilityLog.porId,
      porRequestId: portabilityLog.porRequestId,
      porNumber: portabilityLog.porNumber,
      porLegalTerm: portabilityLog.porLegalTerm,
      porOpr: portabilityLog.porOpr,
      porAccType: portabilityLog.porAccType,
      porIdNumber: portabilityLog.porIdNumber,
      porContactNumber: portabilityLog.porContactNumber,
      porStatus: portabilityLog.porStatus,
      porPortedDate: portabilityLog.porPortedDate ? portabilityLog.porPortedDate.format(DATE_TIME_FORMAT) : null,
      porRouting: portabilityLog.porRouting,
      porType: portabilityLog.porType,
      porOpOrg: portabilityLog.porOpOrg,
      porRspCode: portabilityLog.porRspCode,
      porRspNote: portabilityLog.porRspNote,
      porCancelNote: portabilityLog.porCancelNote,
      porMnpid: portabilityLog.porMnpid,
      portationDate: portabilityLog.portationDate ? portabilityLog.portationDate.format(DATE_TIME_FORMAT) : null,
      portaCode: portabilityLog.portaCode,
      mvno: portabilityLog.mvno,
      context: portabilityLog.context,
      porErrCode: portabilityLog.porErrCode,
      porErrMessage: portabilityLog.porErrMessage,
      porOpd: portabilityLog.porOpd,
      porNumType: portabilityLog.porNumType,
      porNote: portabilityLog.porNote,
      porDeadline: portabilityLog.porDeadline ? portabilityLog.porDeadline.format(DATE_TIME_FORMAT) : null,
      porResponseTimestamp: portabilityLog.porResponseTimestamp,
      porEligible: portabilityLog.porEligible,
      porBillingOk: portabilityLog.porBillingOk,
      intermediaryActionState: portabilityLog.intermediaryActionState,
      porCrDate: portabilityLog.porCrDate ? portabilityLog.porCrDate.format(DATE_TIME_FORMAT) : null,
      porUpdDate: portabilityLog.porUpdDate ? portabilityLog.porUpdDate.format(DATE_TIME_FORMAT) : null,
      porTechStatus: portabilityLog.porTechStatus,
      porTechDeadline: portabilityLog.porTechDeadline ? portabilityLog.porTechDeadline.format(DATE_TIME_FORMAT) : null,
      refPorId: portabilityLog.refPorId,
      needManualRetry: portabilityLog.needManualRetry,
      retryCount: portabilityLog.retryCount,
      action: portabilityLog.action,
      request: portabilityLog.request,
      insertTimestamp: portabilityLog.insertTimestamp ? portabilityLog.insertTimestamp.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPortabilityLog {
    return {
      ...new PortabilityLog(),
      id: this.editForm.get(['id'])!.value,
      porId: this.editForm.get(['porId'])!.value,
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
      needManualRetry: this.editForm.get(['needManualRetry'])!.value,
      retryCount: this.editForm.get(['retryCount'])!.value,
      action: this.editForm.get(['action'])!.value,
      request: this.editForm.get(['request'])!.value,
      insertTimestamp: this.editForm.get(['insertTimestamp'])!.value
        ? dayjs(this.editForm.get(['insertTimestamp'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
