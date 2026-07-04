import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICrmToCrdbResponseMap, CrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';
import { CrmToCrdbResponseMapService } from '../service/crm-to-crdb-response-map.service';

@Component({
  selector: 'jhi-crm-to-crdb-response-map-update',
  templateUrl: './crm-to-crdb-response-map-update.component.html',
  standalone: false,
})
export class CrmToCrdbResponseMapUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(20)]],
    description: [null, [Validators.required, Validators.maxLength(200)]],
    crmInterface: [null, [Validators.required, Validators.maxLength(200)]],
    rspCode: [null, [Validators.required, Validators.maxLength(20)]],
    rspNote: [null, [Validators.required, Validators.maxLength(200)]],
  });

  constructor(
    protected crmToCrdbResponseMapService: CrmToCrdbResponseMapService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crmToCrdbResponseMap }) => {
      this.updateForm(crmToCrdbResponseMap);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const crmToCrdbResponseMap = this.createFromForm();
    if (crmToCrdbResponseMap.id !== undefined) {
      this.subscribeToSaveResponse(this.crmToCrdbResponseMapService.update(crmToCrdbResponseMap));
    } else {
      this.subscribeToSaveResponse(this.crmToCrdbResponseMapService.create(crmToCrdbResponseMap));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICrmToCrdbResponseMap>>): void {
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

  protected updateForm(crmToCrdbResponseMap: ICrmToCrdbResponseMap): void {
    this.editForm.patchValue({
      id: crmToCrdbResponseMap.id,
      code: crmToCrdbResponseMap.code,
      description: crmToCrdbResponseMap.description,
      crmInterface: crmToCrdbResponseMap.crmInterface,
      rspCode: crmToCrdbResponseMap.rspCode,
      rspNote: crmToCrdbResponseMap.rspNote,
    });
  }

  protected createFromForm(): ICrmToCrdbResponseMap {
    return {
      ...new CrmToCrdbResponseMap(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
      crmInterface: this.editForm.get(['crmInterface'])!.value,
      rspCode: this.editForm.get(['rspCode'])!.value,
      rspNote: this.editForm.get(['rspNote'])!.value,
    };
  }
}
