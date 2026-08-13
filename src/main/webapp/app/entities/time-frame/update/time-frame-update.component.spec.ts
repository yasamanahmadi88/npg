jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TranslateModule } from '@ngx-translate/core';

import { TimeFrameService } from '../service/time-frame.service';
import { OffDayService } from '../../off-day/service/off-day.service';
import { ITimeFrame, TimeFrame } from '../time-frame.model';

import { TimeFrameUpdateComponent } from './time-frame-update.component';

describe('Component Tests', () => {
  describe('TimeFrame Management Update Component', () => {
    let comp: TimeFrameUpdateComponent;
    let fixture: ComponentFixture<TimeFrameUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let timeFrameService: TimeFrameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, TranslateModule.forRoot()],
        declarations: [TimeFrameUpdateComponent],
        providers: [
          FormBuilder,
          ActivatedRoute,
          {
            provide: OffDayService,
            useValue: {
              queryAll: () => of(new HttpResponse({ body: [] })),
            },
          },
        ],
      })
        .overrideTemplate(TimeFrameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeFrameUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      timeFrameService = TestBed.inject(TimeFrameService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const timeFrame: ITimeFrame = { id: 456 };

        activatedRoute.data = of({ timeFrame });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(timeFrame));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TimeFrame>>();
        const timeFrame = { id: 123 };
        jest.spyOn(timeFrameService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ timeFrame });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: timeFrame }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(timeFrameService.update).toHaveBeenCalledWith(expect.objectContaining({ id: 123 }));
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TimeFrame>>();
        const timeFrame = new TimeFrame();
        jest.spyOn(timeFrameService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ timeFrame });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: timeFrame }));
        saveSubject.complete();

        // THEN
        expect(timeFrameService.create).toHaveBeenCalledWith(expect.objectContaining({ id: undefined }));
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TimeFrame>>();
        const timeFrame = { id: 123 };
        jest.spyOn(timeFrameService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ timeFrame });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(timeFrameService.update).toHaveBeenCalledWith(expect.objectContaining({ id: 123 }));
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
