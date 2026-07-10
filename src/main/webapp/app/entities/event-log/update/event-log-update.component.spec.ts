jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EventLogService } from '../service/event-log.service';
import { IEventLog, EventLog } from '../event-log.model';

import { EventLogUpdateComponent } from './event-log-update.component';

describe('Component Tests', () => {
  describe('EventLog Management Update Component', () => {
    let comp: EventLogUpdateComponent;
    let fixture: ComponentFixture<EventLogUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let eventLogService: EventLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EventLogUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EventLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventLogUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      eventLogService = TestBed.inject(EventLogService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const eventLog: IEventLog = { id: 456 };

        activatedRoute.data = of({ eventLog });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(eventLog));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EventLog>>();
        const eventLog = { id: 123 };
        jest.spyOn(eventLogService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eventLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eventLog }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(eventLogService.update).toHaveBeenCalledWith(eventLog);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EventLog>>();
        const eventLog = new EventLog();
        jest.spyOn(eventLogService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eventLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: eventLog }));
        saveSubject.complete();

        // THEN
        expect(eventLogService.create).toHaveBeenCalledWith(eventLog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EventLog>>();
        const eventLog = { id: 123 };
        jest.spyOn(eventLogService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ eventLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(eventLogService.update).toHaveBeenCalledWith(eventLog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
