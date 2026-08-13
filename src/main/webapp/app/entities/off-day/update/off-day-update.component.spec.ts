jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OffDayService } from '../service/off-day.service';
import { IOffDay, OffDay } from '../off-day.model';

import { OffDayUpdateComponent } from './off-day-update.component';

describe('Component Tests', () => {
  describe('OffDay Management Update Component', () => {
    let comp: OffDayUpdateComponent;
    let fixture: ComponentFixture<OffDayUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let offDayService: OffDayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OffDayUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OffDayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OffDayUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      offDayService = TestBed.inject(OffDayService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const offDay: IOffDay = { id: 456 };

        activatedRoute.data = of({ offDay });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(offDay));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OffDay>>();
        const offDay = { id: 123 };
        jest.spyOn(offDayService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ offDay });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: offDay }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(offDayService.update).toHaveBeenCalledWith(offDay);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OffDay>>();
        const offDay = new OffDay();
        jest.spyOn(offDayService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ offDay });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: offDay }));
        saveSubject.complete();

        // THEN
        expect(offDayService.create).toHaveBeenCalledWith(offDay);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OffDay>>();
        const offDay = { id: 123 };
        jest.spyOn(offDayService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ offDay });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(offDayService.update).toHaveBeenCalledWith(offDay);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
