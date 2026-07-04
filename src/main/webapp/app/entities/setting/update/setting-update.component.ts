import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISetting, Setting } from '../setting.model';
import { SettingService } from '../service/setting.service';

@Component({
  selector: 'jhi-setting-update',
  templateUrl: './setting-update.component.html',
  standalone: false,
})
export class SettingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(200)]],
    key: [null, [Validators.required, Validators.maxLength(70)]],
    value: [null, [Validators.required, Validators.maxLength(200)]],
  });

  constructor(protected settingService: SettingService, protected activatedRoute: ActivatedRoute, protected fb: UntypedFormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ setting }) => {
      this.updateForm(setting);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const setting = this.createFromForm();
    if (setting.id !== undefined) {
      this.subscribeToSaveResponse(this.settingService.update(setting));
    } else {
      this.subscribeToSaveResponse(this.settingService.create(setting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISetting>>): void {
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

  protected updateForm(setting: ISetting): void {
    this.editForm.patchValue({
      id: setting.id,
      name: setting.name,
      key: setting.key,
      value: setting.value,
    });
  }

  protected createFromForm(): ISetting {
    return {
      ...new Setting(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      key: this.editForm.get(['key'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }
}
