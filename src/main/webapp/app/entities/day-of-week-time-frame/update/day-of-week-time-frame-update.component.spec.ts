jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TranslateModule } from '@ngx-translate/core';

import { DayOfWeekTimeFrameService } from '../service/day-of-week-time-frame.service';
import { IDayOfWeekTimeFrame, DayOfWeekTimeFrame } from '../day-of-week-time-frame.model';

import { DayOfWeekTimeFrameUpdateComponent } from './day-of-week-time-frame-update.component';

describe('Component Tests', () => {
  describe('DayOfWeekTimeFrame Management Update Component', () => {
    let comp: DayOfWeekTimeFrameUpdateComponent;
    let fixture: ComponentFixture<DayOfWeekTimeFrameUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dayOfWeekTimeFrameService: DayOfWeekTimeFrameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, TranslateModule.forRoot()],
        declarations: [DayOfWeekTimeFrameUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DayOfWeekTimeFrameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DayOfWeekTimeFrameUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dayOfWeekTimeFrameService = TestBed.inject(DayOfWeekTimeFrameService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dayOfWeekTimeFrame: IDayOfWeekTimeFrame = { id: 456 };

        activatedRoute.data = of({ dayOfWeekTimeFrame });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dayOfWeekTimeFrame));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DayOfWeekTimeFrame>>();
        const dayOfWeekTimeFrame = { id: 123 };
        jest.spyOn(dayOfWeekTimeFrameService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ dayOfWeekTimeFrame });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dayOfWeekTimeFrame }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dayOfWeekTimeFrameService.update).toHaveBeenCalledWith(expect.objectContaining({ id: 123 }));
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DayOfWeekTimeFrame>>();
        const dayOfWeekTimeFrame = new DayOfWeekTimeFrame();
        jest.spyOn(dayOfWeekTimeFrameService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ dayOfWeekTimeFrame });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dayOfWeekTimeFrame }));
        saveSubject.complete();

        // THEN
        expect(dayOfWeekTimeFrameService.create).toHaveBeenCalledWith(expect.objectContaining({ id: undefined }));
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DayOfWeekTimeFrame>>();
        const dayOfWeekTimeFrame = { id: 123 };
        jest.spyOn(dayOfWeekTimeFrameService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ dayOfWeekTimeFrame });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dayOfWeekTimeFrameService.update).toHaveBeenCalledWith(expect.objectContaining({ id: 123 }));
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
