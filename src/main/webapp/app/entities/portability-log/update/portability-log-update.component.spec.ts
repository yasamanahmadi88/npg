jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PortabilityLogService } from '../service/portability-log.service';
import { IPortabilityLog, PortabilityLog } from '../portability-log.model';

import { PortabilityLogUpdateComponent } from './portability-log-update.component';

describe('Component Tests', () => {
  describe('PortabilityLog Management Update Component', () => {
    let comp: PortabilityLogUpdateComponent;
    let fixture: ComponentFixture<PortabilityLogUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let portabilityLogService: PortabilityLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PortabilityLogUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PortabilityLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PortabilityLogUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      portabilityLogService = TestBed.inject(PortabilityLogService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const portabilityLog: IPortabilityLog = { id: 456 };

        activatedRoute.data = of({ portabilityLog });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(portabilityLog));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PortabilityLog>>();
        const portabilityLog = { id: 123 };
        jest.spyOn(portabilityLogService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ portabilityLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: portabilityLog }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(portabilityLogService.update).toHaveBeenCalledWith(portabilityLog);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PortabilityLog>>();
        const portabilityLog = new PortabilityLog();
        jest.spyOn(portabilityLogService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ portabilityLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: portabilityLog }));
        saveSubject.complete();

        // THEN
        expect(portabilityLogService.create).toHaveBeenCalledWith(portabilityLog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PortabilityLog>>();
        const portabilityLog = { id: 123 };
        jest.spyOn(portabilityLogService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ portabilityLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(portabilityLogService.update).toHaveBeenCalledWith(portabilityLog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
