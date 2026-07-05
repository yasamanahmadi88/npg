import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IResource, Resource } from '../resource.model';
import { ResourceService } from '../service/resource.service';

@Component({
  selector: 'jhi-resource-update',
  templateUrl: './resource-update.component.html',
  standalone: false,
})
export class ResourceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(200)]],
    displayName: [null, [Validators.required, Validators.maxLength(300)]],
    apiUri: [null, [Validators.required, Validators.maxLength(1000)]],
    resourceType: [null, [Validators.required]],
  });

  constructor(protected resourceService: ResourceService, protected activatedRoute: ActivatedRoute, protected fb: UntypedFormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resource }) => {
      this.updateForm(resource);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resource = this.createFromForm();
    if (resource.id !== undefined) {
      this.subscribeToSaveResponse(this.resourceService.update(resource));
    } else {
      this.subscribeToSaveResponse(this.resourceService.create(resource));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResource>>): void {
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

  protected updateForm(resource: IResource): void {
    this.editForm.patchValue({
      id: resource.id,
      name: resource.name,
      displayName: resource.displayName,
      apiUri: resource.apiUri,
      resourceType: resource.resourceType,
    });
  }

  protected createFromForm(): IResource {
    return {
      ...new Resource(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      displayName: this.editForm.get(['displayName'])!.value,
      apiUri: this.editForm.get(['apiUri'])!.value,
      resourceType: this.editForm.get(['resourceType'])!.value,
    };
  }
}
