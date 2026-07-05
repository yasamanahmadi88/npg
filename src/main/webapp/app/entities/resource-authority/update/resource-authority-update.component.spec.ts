jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ResourceAuthorityService } from '../service/resource-authority.service';
import { IResourceAuthority, ResourceAuthority } from '../resource-authority.model';
import { IResource } from 'app/entities/resource/resource.model';
import { ResourceService } from 'app/entities/resource/service/resource.service';

import { ResourceAuthorityUpdateComponent } from './resource-authority-update.component';

describe('Component Tests', () => {
  describe('ResourceAuthority Management Update Component', () => {
    let comp: ResourceAuthorityUpdateComponent;
    let fixture: ComponentFixture<ResourceAuthorityUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let resourceAuthorityService: ResourceAuthorityService;
    let resourceService: ResourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResourceAuthorityUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ResourceAuthorityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResourceAuthorityUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      resourceAuthorityService = TestBed.inject(ResourceAuthorityService);
      resourceService = TestBed.inject(ResourceService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Resource query and add missing value', () => {
        const resourceAuthority: IResourceAuthority = { id: 456 };
        const resource: IResource = { id: 13651 };
        resourceAuthority.resource = resource;

        const resourceCollection: IResource[] = [{ id: 72184 }];
        spyOn(resourceService, 'queryAll').and.returnValue(of(new HttpResponse({ body: resourceCollection })));
        const additionalResources = [resource];
        const expectedCollection: IResource[] = [...additionalResources, ...resourceCollection];
        spyOn(resourceService, 'addResourceToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ resourceAuthority });
        comp.ngOnInit();

        expect(resourceService.queryAll).toHaveBeenCalled();
        expect(resourceService.addResourceToCollectionIfMissing).toHaveBeenCalledWith(resourceCollection, ...additionalResources);
        expect(comp.resourcesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const resourceAuthority: IResourceAuthority = { id: 456 };
        const resource: IResource = { id: 3585 };
        resourceAuthority.resource = resource;

        activatedRoute.data = of({ resourceAuthority });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(resourceAuthority));
        expect(comp.resourcesSharedCollection).toContain(resource);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const resourceAuthority = { id: 123 };
        spyOn(resourceAuthorityService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ resourceAuthority });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: resourceAuthority }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(resourceAuthorityService.update).toHaveBeenCalledWith(resourceAuthority);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const resourceAuthority = new ResourceAuthority();
        spyOn(resourceAuthorityService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ resourceAuthority });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: resourceAuthority }));
        saveSubject.complete();

        // THEN
        expect(resourceAuthorityService.create).toHaveBeenCalledWith(resourceAuthority);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const resourceAuthority = { id: 123 };
        spyOn(resourceAuthorityService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ resourceAuthority });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(resourceAuthorityService.update).toHaveBeenCalledWith(resourceAuthority);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackResourceById', () => {
        it('Should return tracked Resource primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackResourceById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

