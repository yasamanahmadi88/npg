import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INotificationTemplate, NotificationTemplate } from '../notification-template.model';
import { NotificationTemplateService } from '../service/notification-template.service';

@Component({
  selector: 'jhi-notification-template-update',
  templateUrl: './notification-template-update.component.html',
  standalone: false,
})
export class NotificationTemplateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    templateCode: [null, [Validators.maxLength(100)]],
    language: [null, [Validators.maxLength(6)]],
    content: [null, [Validators.maxLength(1000)]],
    type: [null, [Validators.maxLength(12)]],
  });

  constructor(
    protected notificationTemplateService: NotificationTemplateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationTemplate }) => {
      this.updateForm(notificationTemplate);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notificationTemplate = this.createFromForm();
    if (notificationTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationTemplateService.update(notificationTemplate));
    } else {
      this.subscribeToSaveResponse(this.notificationTemplateService.create(notificationTemplate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificationTemplate>>): void {
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

  protected updateForm(notificationTemplate: INotificationTemplate): void {
    this.editForm.patchValue({
      id: notificationTemplate.id,
      templateCode: notificationTemplate.templateCode,
      language: notificationTemplate.language,
      content: notificationTemplate.content,
      type: notificationTemplate.type,
    });
  }

  protected createFromForm(): INotificationTemplate {
    return {
      ...new NotificationTemplate(),
      id: this.editForm.get(['id'])!.value,
      templateCode: this.editForm.get(['templateCode'])!.value,
      language: this.editForm.get(['language'])!.value,
      content: this.editForm.get(['content'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }
}
