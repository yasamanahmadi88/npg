jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BusinessConfigService } from '../service/business-config.service';
import { IBusinessConfig, BusinessConfig } from '../business-config.model';

import { BusinessConfigUpdateComponent } from './business-config-update.component';

describe('Component Tests', () => {
  describe('BusinessConfig Management Update Component', () => {
    let comp: BusinessConfigUpdateComponent;
    let fixture: ComponentFixture<BusinessConfigUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let businessConfigService: BusinessConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BusinessConfigUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BusinessConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessConfigUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      businessConfigService = TestBed.inject(BusinessConfigService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const businessConfig: IBusinessConfig = { id: '456' };

        activatedRoute.data = of({ businessConfig });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(businessConfig));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BusinessConfig>>();
        const businessConfig = { id: '123' };
        jest.spyOn(businessConfigService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ businessConfig });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: businessConfig }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(businessConfigService.update).toHaveBeenCalledWith(businessConfig);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BusinessConfig>>();
        const businessConfig = new BusinessConfig();
        jest.spyOn(businessConfigService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ businessConfig });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: businessConfig }));
        saveSubject.complete();

        // THEN
        expect(businessConfigService.create).toHaveBeenCalledWith(businessConfig);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BusinessConfig>>();
        const businessConfig = { id: '123' };
        jest.spyOn(businessConfigService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ businessConfig });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(businessConfigService.update).toHaveBeenCalledWith(businessConfig);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
