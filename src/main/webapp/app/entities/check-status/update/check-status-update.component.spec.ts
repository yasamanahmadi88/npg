jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CheckStatusService } from '../service/check-status.service';
import { ICheckStatus, CheckStatus } from '../check-status.model';

import { CheckStatusUpdateComponent } from './check-status-update.component';

describe('Component Tests', () => {
  describe('CheckStatus Management Update Component', () => {
    let comp: CheckStatusUpdateComponent;
    let fixture: ComponentFixture<CheckStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let checkStatusService: CheckStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CheckStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CheckStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      checkStatusService = TestBed.inject(CheckStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const checkStatus: ICheckStatus = { id: 456 };

        activatedRoute.data = of({ checkStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(checkStatus));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CheckStatus>>();
        const checkStatus = { id: 123 };
        jest.spyOn(checkStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(checkStatusService.update).toHaveBeenCalledWith(checkStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CheckStatus>>();
        const checkStatus = new CheckStatus();
        jest.spyOn(checkStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: checkStatus }));
        saveSubject.complete();

        // THEN
        expect(checkStatusService.create).toHaveBeenCalledWith(checkStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CheckStatus>>();
        const checkStatus = { id: 123 };
        jest.spyOn(checkStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ checkStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(checkStatusService.update).toHaveBeenCalledWith(checkStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
