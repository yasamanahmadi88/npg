jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PortabilityService } from '../service/portability.service';
import { IPortability, Portability } from '../portability.model';

import { PortabilityUpdateComponent } from './portability-update.component';

describe('Component Tests', () => {
  describe('Portability Management Update Component', () => {
    let comp: PortabilityUpdateComponent;
    let fixture: ComponentFixture<PortabilityUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let portabilityService: PortabilityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PortabilityUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PortabilityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PortabilityUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      portabilityService = TestBed.inject(PortabilityService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const portability: IPortability = { id: 456 };

        activatedRoute.data = of({ portability });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(portability));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Portability>>();
        const portability = { id: 123 };
        jest.spyOn(portabilityService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ portability });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: portability }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(portabilityService.update).toHaveBeenCalledWith(expect.objectContaining({ id: 123 }));
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Portability>>();
        const portability = new Portability();
        jest.spyOn(portabilityService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ portability });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: portability }));
        saveSubject.complete();

        // THEN
        expect(portabilityService.create).toHaveBeenCalledWith(expect.objectContaining({ id: undefined, needManualRetry: 0 }));
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Portability>>();
        const portability = { id: 123 };
        jest.spyOn(portabilityService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ portability });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(portabilityService.update).toHaveBeenCalledWith(expect.objectContaining({ id: 123 }));
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
