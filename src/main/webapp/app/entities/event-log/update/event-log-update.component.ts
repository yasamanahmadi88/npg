import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEventLog, EventLog } from '../event-log.model';
import { EventLogService } from '../service/event-log.service';

@Component({
  selector: 'jhi-event-log-update',
  templateUrl: './event-log-update.component.html',
  standalone: false,
})
export class EventLogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    conversationId: [null, [Validators.maxLength(50)]],
    sender: [null, [Validators.maxLength(50)]],
    receiver: [null, [Validators.maxLength(50)]],
    message: [null, [Validators.maxLength(255)]],
    requestBody: [],
    responseBody: [],
    flg0Ordinary1Exception: [null, [Validators.required]],
    eventSource: [null, [Validators.required, Validators.maxLength(255)]],
    exceptionBody: [],
    httpStatus: [null, [Validators.maxLength(50)]],
    insertTimestamp: [null, [Validators.required]],
  });

  constructor(protected eventLogService: EventLogService, protected activatedRoute: ActivatedRoute, protected fb: UntypedFormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventLog }) => {
      if (eventLog.id === undefined) {
        const today = dayjs().startOf('day');
        eventLog.insertTimestamp = today;
      }

      this.updateForm(eventLog);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventLog = this.createFromForm();
    if (eventLog.id !== undefined) {
      this.subscribeToSaveResponse(this.eventLogService.update(eventLog));
    } else {
      this.subscribeToSaveResponse(this.eventLogService.create(eventLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventLog>>): void {
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

  protected updateForm(eventLog: IEventLog): void {
    this.editForm.patchValue({
      id: eventLog.id,
      conversationId: eventLog.conversationId,
      sender: eventLog.sender,
      receiver: eventLog.receiver,
      message: eventLog.message,
      requestBody: eventLog.requestBody,
      responseBody: eventLog.responseBody,
      flg0Ordinary1Exception: eventLog.flg0Ordinary1Exception,
      eventSource: eventLog.eventSource,
      exceptionBody: eventLog.exceptionBody,
      httpStatus: eventLog.httpStatus,
      insertTimestamp: eventLog.insertTimestamp ? eventLog.insertTimestamp.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IEventLog {
    return {
      ...new EventLog(),
      id: this.editForm.get(['id'])!.value,
      conversationId: this.editForm.get(['conversationId'])!.value,
      sender: this.editForm.get(['sender'])!.value,
      receiver: this.editForm.get(['receiver'])!.value,
      message: this.editForm.get(['message'])!.value,
      requestBody: this.editForm.get(['requestBody'])!.value,
      responseBody: this.editForm.get(['responseBody'])!.value,
      flg0Ordinary1Exception: this.editForm.get(['flg0Ordinary1Exception'])!.value,
      eventSource: this.editForm.get(['eventSource'])!.value,
      exceptionBody: this.editForm.get(['exceptionBody'])!.value,
      httpStatus: this.editForm.get(['httpStatus'])!.value,
      insertTimestamp: this.editForm.get(['insertTimestamp'])!.value
        ? dayjs(this.editForm.get(['insertTimestamp'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
