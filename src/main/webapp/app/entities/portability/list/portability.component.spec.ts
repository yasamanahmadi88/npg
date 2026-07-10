jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { TranslateModule } from '@ngx-translate/core';
import { FormBuilder } from '@angular/forms';

import { PortabilityService } from '../service/portability.service';

import { PortabilityComponent } from './portability.component';

describe('Component Tests', () => {
  describe('Portability Management Component', () => {
    let comp: PortabilityComponent;
    let fixture: ComponentFixture<PortabilityComponent>;
    let service: PortabilityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, TranslateModule.forRoot()],
        declarations: [PortabilityComponent],
        providers: [
          Router,
          FormBuilder,
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                portability: [{ id: 123 }],
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                jest.requireActual('@angular/router').convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(PortabilityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PortabilityComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PortabilityService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should initialize from route data on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).not.toHaveBeenCalled();
      expect(comp.portabilities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 }, true);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.portabilities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for default porCrDate sort', () => {
      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 }, true);

      // THEN
      expect(service.query).toHaveBeenCalledWith(expect.objectContaining({ sort: ['porCrDateSearch,asc', 'id,asc'] }));
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';
      comp.ascending = false;

      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 }, true);

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['name,asc', 'id,asc'] }));
    });
  });
});