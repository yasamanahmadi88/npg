jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CrmToCrdbResponseMapService } from '../service/crm-to-crdb-response-map.service';
import { ICrmToCrdbResponseMap, CrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';

import { CrmToCrdbResponseMapUpdateComponent } from './crm-to-crdb-response-map-update.component';

describe('Component Tests', () => {
  describe('CrmToCrdbResponseMap Management Update Component', () => {
    let comp: CrmToCrdbResponseMapUpdateComponent;
    let fixture: ComponentFixture<CrmToCrdbResponseMapUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let crmToCrdbResponseMapService: CrmToCrdbResponseMapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CrmToCrdbResponseMapUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CrmToCrdbResponseMapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CrmToCrdbResponseMapUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      crmToCrdbResponseMapService = TestBed.inject(CrmToCrdbResponseMapService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const crmToCrdbResponseMap: ICrmToCrdbResponseMap = { id: 456 };

        activatedRoute.data = of({ crmToCrdbResponseMap });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(crmToCrdbResponseMap));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CrmToCrdbResponseMap>>();
        const crmToCrdbResponseMap = { id: 123 };
        jest.spyOn(crmToCrdbResponseMapService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ crmToCrdbResponseMap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: crmToCrdbResponseMap }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(crmToCrdbResponseMapService.update).toHaveBeenCalledWith(crmToCrdbResponseMap);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CrmToCrdbResponseMap>>();
        const crmToCrdbResponseMap = new CrmToCrdbResponseMap();
        jest.spyOn(crmToCrdbResponseMapService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ crmToCrdbResponseMap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: crmToCrdbResponseMap }));
        saveSubject.complete();

        // THEN
        expect(crmToCrdbResponseMapService.create).toHaveBeenCalledWith(crmToCrdbResponseMap);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CrmToCrdbResponseMap>>();
        const crmToCrdbResponseMap = { id: 123 };
        jest.spyOn(crmToCrdbResponseMapService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ crmToCrdbResponseMap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(crmToCrdbResponseMapService.update).toHaveBeenCalledWith(crmToCrdbResponseMap);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
