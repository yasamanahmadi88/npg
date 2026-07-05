import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFileReportGenerationLog, FileReportGenerationLog } from '../file-report-generation-log.model';
import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';

@Component({
  selector: 'jhi-file-report-generation-log-update',
  templateUrl: './file-report-generation-log-update.component.html',
  standalone: false,
})
export class FileReportGenerationLogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    reportName: [null, [Validators.required, Validators.maxLength(255)]],
    reportDate: [null, [Validators.required]],
    fileName: [null, [Validators.required, Validators.maxLength(255)]],
    rowNumber: [null, [Validators.required]],
    porNumber: [null, [Validators.required, Validators.maxLength(16)]],
    content: [null, [Validators.required, Validators.maxLength(4000)]],
  });

  constructor(
    protected fileReportGenerationLogService: FileReportGenerationLogService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileReportGenerationLog }) => {
      this.updateForm(fileReportGenerationLog);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fileReportGenerationLog = this.createFromForm();
    if (fileReportGenerationLog.id !== undefined) {
      this.subscribeToSaveResponse(this.fileReportGenerationLogService.update(fileReportGenerationLog));
    } else {
      this.subscribeToSaveResponse(this.fileReportGenerationLogService.create(fileReportGenerationLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileReportGenerationLog>>): void {
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

  protected updateForm(fileReportGenerationLog: IFileReportGenerationLog): void {
    this.editForm.patchValue({
      id: fileReportGenerationLog.id,
      reportName: fileReportGenerationLog.reportName,
      reportDate: fileReportGenerationLog.reportDate,
      fileName: fileReportGenerationLog.fileName,
      rowNumber: fileReportGenerationLog.rowNumber,
      porNumber: fileReportGenerationLog.porNumber,
      content: fileReportGenerationLog.content,
    });
  }

  protected createFromForm(): IFileReportGenerationLog {
    return {
      ...new FileReportGenerationLog(),
      id: this.editForm.get(['id'])!.value,
      reportName: this.editForm.get(['reportName'])!.value,
      reportDate: this.editForm.get(['reportDate'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      rowNumber: this.editForm.get(['rowNumber'])!.value,
      porNumber: this.editForm.get(['porNumber'])!.value,
      content: this.editForm.get(['content'])!.value,
    };
  }
}
