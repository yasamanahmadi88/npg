import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResourceAuthority, ResourceAuthority } from '../resource-authority.model';
import { ResourceAuthorityService } from '../service/resource-authority.service';
import { IResource } from 'app/entities/resource/resource.model';
import { ResourceService } from 'app/entities/resource/service/resource.service';
import { IAuthority } from '../../authority/authority.model';
import { AuthorityService } from '../../authority/service/authority.service';

@Component({
  selector: 'jhi-resource-authority-update',
  templateUrl: './resource-authority-update.component.html',
  standalone: false,
})
export class ResourceAuthorityUpdateComponent implements OnInit {
  isSaving = false;

  resourcesSharedCollection: IResource[] = [];
  authoritiesSharedCollection!: IAuthority[];
  editForm = this.fb.group({
    id: [],
    verb: [null, [Validators.required]],
    authorityId: [null, [Validators.required]],
    resource: [],
  });

  constructor(
    protected resourceAuthorityService: ResourceAuthorityService,
    protected resourceService: ResourceService,
    protected authorityService: AuthorityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resourceAuthority }) => {
      this.updateForm(resourceAuthority);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resourceAuthority = this.createFromForm();
    if (resourceAuthority.id !== undefined) {
      this.subscribeToSaveResponse(this.resourceAuthorityService.update(resourceAuthority));
    } else {
      this.subscribeToSaveResponse(this.resourceAuthorityService.create(resourceAuthority));
    }
  }

  trackResourceById(index: number, item: IResource): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResourceAuthority>>): void {
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

  protected updateForm(resourceAuthority: IResourceAuthority): void {
    this.editForm.patchValue({
      id: resourceAuthority.id,
      verb: resourceAuthority.verb,
      authorityId: resourceAuthority.authorityId,
      resource: resourceAuthority.resource,
    });

    this.resourcesSharedCollection = this.resourceService.addResourceToCollectionIfMissing(
      this.resourcesSharedCollection,
      resourceAuthority.resource
    );
  }

  protected loadRelationshipsOptions(): void {
    this.resourceService
      .queryAll()
      .pipe(map((res: HttpResponse<IResource[]>) => res.body ?? []))
      .pipe(
        map((resources: IResource[]) =>
          this.resourceService.addResourceToCollectionIfMissing(resources, this.editForm.get('resource')!.value)
        )
      )
      .subscribe((resources: IResource[]) => (this.resourcesSharedCollection = resources));

    this.authorityService
      .queryAll()
      .pipe(map((res: HttpResponse<IAuthority[]>) => res.body ?? []))
      .subscribe((authorityxes: IAuthority[]) => (this.authoritiesSharedCollection = authorityxes));
  }

  protected createFromForm(): IResourceAuthority {
    return {
      ...new ResourceAuthority(),
      id: this.editForm.get(['id'])!.value,
      verb: this.editForm.get(['verb'])!.value,
      authorityId: this.editForm.get(['authorityId'])!.value,
      resource: this.editForm.get(['resource'])!.value,
    };
  }
}
