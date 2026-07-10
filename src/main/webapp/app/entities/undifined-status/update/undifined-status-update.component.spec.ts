jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UndifinedStatusService } from '../service/undifined-status.service';
import { IUndifinedStatus, UndifinedStatus } from '../undifined-status.model';

import { UndifinedStatusUpdateComponent } from './undifined-status-update.component';

describe('Component Tests', () => {
  describe('UndifinedStatus Management Update Component', () => {
    let comp: UndifinedStatusUpdateComponent;
    let fixture: ComponentFixture<UndifinedStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let undifinedStatusService: UndifinedStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UndifinedStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(UndifinedStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UndifinedStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      undifinedStatusService = TestBed.inject(UndifinedStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const undifinedStatus: IUndifinedStatus = { id: 456 };

        activatedRoute.data = of({ undifinedStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(undifinedStatus));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UndifinedStatus>>();
        const undifinedStatus = { id: 123 };
        jest.spyOn(undifinedStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ undifinedStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: undifinedStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(undifinedStatusService.update).toHaveBeenCalledWith(undifinedStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UndifinedStatus>>();
        const undifinedStatus = new UndifinedStatus();
        jest.spyOn(undifinedStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ undifinedStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: undifinedStatus }));
        saveSubject.complete();

        // THEN
        expect(undifinedStatusService.create).toHaveBeenCalledWith(undifinedStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UndifinedStatus>>();
        const undifinedStatus = { id: 123 };
        jest.spyOn(undifinedStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ undifinedStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(undifinedStatusService.update).toHaveBeenCalledWith(undifinedStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
