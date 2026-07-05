import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBusinessConfig, BusinessConfig } from '../business-config.model';
import { BusinessConfigService } from '../service/business-config.service';

@Component({
  selector: 'jhi-business-config-update',
  templateUrl: './business-config-update.component.html',
  standalone: false,
})
export class BusinessConfigUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.required, Validators.maxLength(512)]],
  });

  constructor(
    protected businessConfigService: BusinessConfigService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessConfig }) => {
      this.updateForm(businessConfig);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const businessConfig = this.createFromForm();
    if (businessConfig.id) {
      this.subscribeToSaveResponse(this.businessConfigService.update(businessConfig));
    } else {
      this.subscribeToSaveResponse(this.businessConfigService.create(businessConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessConfig>>): void {
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

  protected updateForm(businessConfig: IBusinessConfig): void {
    this.editForm.patchValue({
      id: businessConfig.id,
      value: businessConfig.value,
    });
  }

  protected createFromForm(): IBusinessConfig {
    return {
      ...new BusinessConfig(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }
}
