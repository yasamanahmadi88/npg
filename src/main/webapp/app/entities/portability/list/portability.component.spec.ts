jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TranslateModule } from '@ngx-translate/core';
import { FormBuilder } from '@angular/forms';

import { IPortability } from '../portability.model';
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
          {
            provide: Router,
            useValue: {
              navigate: jest.fn().mockResolvedValue(true),
            },
          },
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

    it('Should load the first page when route data has no portability array', () => {
      // GIVEN
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;
      Object.defineProperty(route, 'data', {
        value: of({ defaultSort: 'porCrDate,asc' }),
      });
      Object.defineProperty(route, 'queryParamMap', {
        value: of(jest.requireActual('@angular/router').convertToParamMap({})),
      });

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalledWith(
        expect.objectContaining({
          page: 0,
          size: 10,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(comp.page).toBe(1);
      expect(comp.itemsPerPage).toBe(10);
      expect(comp.predicate).toBe('porCrDate');
      expect(comp.ascending).toBe(true);
    });

    it('should restore page size and descending Sort from route query parameters', () => {
      // GIVEN
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;
      Object.defineProperty(route, 'data', {
        value: of({ defaultSort: 'porCrDate,asc' }),
      });
      Object.defineProperty(route, 'queryParamMap', {
        value: of(
          jest.requireActual('@angular/router').convertToParamMap({
            page: '3',
            size: '50',
            sort: 'porNumber,desc',
          })
        ),
      });

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          page: 2,
          size: 50,
          sort: ['porNumber,desc', 'id,desc'],
        })
      );
      expect(comp.page).toBe(3);
      expect(comp.itemsPerPage).toBe(50);
      expect(comp.predicate).toBe('porNumber');
      expect(comp.ascending).toBe(false);
    });

    it('should fall back to safe defaults for invalid route parameters', () => {
      // GIVEN
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;
      Object.defineProperty(route, 'data', {
        value: of({ defaultSort: 'porCrDate,asc' }),
      });
      Object.defineProperty(route, 'queryParamMap', {
        value: of(
          jest.requireActual('@angular/router').convertToParamMap({
            page: '-3',
            size: '10000',
            sort: 'unknownField,sideways',
          })
        ),
      });

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          page: 0,
          size: 10,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(comp.page).toBe(1);
      expect(comp.itemsPerPage).toBe(10);
      expect(comp.predicate).toBe('porCrDate');
      expect(comp.ascending).toBe(true);
    });

    it('should apply browser route changes without writing the URL again', () => {
      // GIVEN
      const routeData = new Subject<Record<string, unknown>>();
      const routeParams = new Subject<ParamMap>();
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;
      const navigateMock = jest.mocked(TestBed.inject(Router).navigate);

      navigateMock.mockClear();
      Object.defineProperty(route, 'data', { value: routeData });
      Object.defineProperty(route, 'queryParamMap', { value: routeParams });

      comp.ngOnInit();
      routeData.next({ defaultSort: 'porCrDate,asc' });
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '1',
          size: '10',
          sort: 'porCrDate,asc',
        })
      );

      // WHEN
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '2',
          size: '50',
          sort: 'porNumber,desc',
        })
      );

      // THEN
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          page: 1,
          size: 50,
          sort: ['porNumber,desc', 'id,desc'],
        })
      );
      expect(navigateMock).not.toHaveBeenCalled();
    });

    it('should not duplicate the HTTP request when the URL reflects current state', () => {
      // GIVEN
      const routeData = new Subject<Record<string, unknown>>();
      const routeParams = new Subject<ParamMap>();
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;
      const navigateMock = jest.mocked(TestBed.inject(Router).navigate);

      navigateMock.mockClear();
      Object.defineProperty(route, 'data', { value: routeData });
      Object.defineProperty(route, 'queryParamMap', { value: routeParams });

      comp.ngOnInit();
      routeData.next({ defaultSort: 'porCrDate,asc' });
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '1',
          size: '10',
          sort: 'porCrDate,asc',
        })
      );

      // WHEN
      comp.loadPage(2);
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '2',
          size: '10',
          sort: 'porCrDate,asc',
        })
      );

      // THEN
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(navigateMock).toHaveBeenCalledTimes(1);
      expect(navigateMock).toHaveBeenLastCalledWith(['/portability'], {
        queryParams: {
          page: 2,
          size: 10,
          sort: 'porCrDate,asc',
        },
      });
    });

    it('should translate a zero-based paginator event to a one-based route page', () => {
      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 }, true);

      // THEN
      expect(service.query).toHaveBeenCalledWith(
        expect.objectContaining({
          page: 1,
          size: 1,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(comp.page).toBe(2);
      expect(comp.itemsPerPage).toBe(1);
      expect(comp.portabilities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should reload the current one-based route page using a zero-based backend page', () => {
      // GIVEN
      comp.page = 2;

      // WHEN
      comp.loadPage();

      // THEN
      expect(service.query).toHaveBeenCalledWith(
        expect.objectContaining({
          page: 1,
          size: comp.itemsPerPage,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(comp.page).toBe(2);
    });

    it('should translate a one-based route page to a zero-based backend page', () => {
      // WHEN
      comp.loadPage(3, true);

      // THEN
      expect(service.query).toHaveBeenCalledWith(
        expect.objectContaining({
          page: 2,
          size: comp.itemsPerPage,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(comp.page).toBe(3);
    });

    it('should cancel the previous request and keep only the latest response', () => {
      // GIVEN
      const firstRequest = new Subject<HttpResponse<IPortability[]>>();
      const secondRequest = new Subject<HttpResponse<IPortability[]>>();
      const headers = new HttpHeaders().set('X-Total-Count', '1');

      jest
        .mocked(service.query)
        .mockReset()
        .mockReturnValueOnce(firstRequest)
        .mockReturnValueOnce(secondRequest);

      // WHEN
      comp.loadPage(1, true);
      comp.loadPage(2, true);

      // THEN
      expect(comp.isLoading).toBe(true);

      // WHEN
      firstRequest.next(new HttpResponse({ body: [{ id: 111 }], headers }));
      secondRequest.next(new HttpResponse({ body: [{ id: 222 }], headers }));
      secondRequest.complete();

      // THEN
      expect(comp.portabilities?.[0]?.id).toBe(222);
      expect(comp.page).toBe(2);
      expect(comp.isLoading).toBe(false);
    });

    it('should finalize loading when the active request fails', () => {
      // GIVEN
      const request = new Subject<HttpResponse<IPortability[]>>();
      jest.mocked(service.query).mockReset().mockReturnValue(request);

      // WHEN
      comp.loadPage(1, true);

      // THEN
      expect(comp.isLoading).toBe(true);

      // WHEN
      request.error(new Error('request failed'));

      // THEN
      expect(comp.isLoading).toBe(false);
      expect(comp.ngbPaginationPage).toBe(1);
    });

    it('should cancel the active request on destroy', () => {
      // GIVEN
      const request = new Subject<HttpResponse<IPortability[]>>();
      const headers = new HttpHeaders().set('X-Total-Count', '1');
      jest.mocked(service.query).mockReset().mockReturnValue(request);

      comp.loadPage(1, true);
      expect(comp.isLoading).toBe(true);

      // WHEN
      comp.ngOnDestroy();
      request.next(new HttpResponse({ body: [{ id: 999 }], headers }));
      request.complete();

      // THEN
      expect(comp.isLoading).toBe(false);
      expect(comp.portabilities).toBeUndefined();
    });

    it('should stop reacting to route state after destroy', () => {
      // GIVEN
      const routeData = new Subject<Record<string, unknown>>();
      const routeParams = new Subject<ParamMap>();
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;

      Object.defineProperty(route, 'data', { value: routeData });
      Object.defineProperty(route, 'queryParamMap', { value: routeParams });
      jest.mocked(service.query).mockClear();

      comp.ngOnInit();

      // WHEN
      comp.ngOnDestroy();
      routeData.next({ defaultSort: 'porCrDate,asc' });
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '2',
          size: '50',
          sort: 'porNumber,desc',
        })
      );

      // THEN
      expect(service.query).not.toHaveBeenCalled();
    });

    it('should search from page one using the selected page size', () => {
      // GIVEN
      comp.page = 4;
      comp.pageToLoad = 4;
      comp.itemsPerPage = 50;
      comp.editForm.patchValue({ porNumber: '123' });

      // WHEN
      comp.search();

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          'porNumber.equals': '123',
          page: 0,
          size: 50,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(comp.page).toBe(1);
      expect(comp.pageToLoad).toBe(1);
    });

    it('should clear filters and reload the first unfiltered page', () => {
      // GIVEN
      comp.itemsPerPage = 50;
      comp.editForm.patchValue({ porNumber: '123' });
      comp.search();

      const callsBeforeClear = jest.mocked(service.query).mock.calls.length;

      // WHEN
      comp.clear();

      // THEN
      expect(service.query).toHaveBeenCalledTimes(callsBeforeClear + 1);
      expect(service.query).toHaveBeenLastCalledWith({
        page: 0,
        size: 50,
        sort: ['porCrDate,asc', 'id,asc'],
      });
      expect(comp.editForm.get('porNumber')?.value).toBeNull();
      expect(comp.page).toBe(1);
      expect(comp.pageToLoad).toBe(1);
    });

    it('should maintain one canonical Sort subscription and remove it on destroy', () => {
      // GIVEN
      const navigateMock = jest.mocked(TestBed.inject(Router).navigate);

      navigateMock.mockClear();
      comp.ngAfterViewInit();
      comp.ngAfterViewInit();

      const callsBeforeSort = jest.mocked(service.query).mock.calls.length;

      // WHEN
      comp.empTbSort.sortChange.emit({
        active: 'porNumber',
        direction: 'desc',
      });

      // THEN
      expect(service.query).toHaveBeenCalledTimes(callsBeforeSort + 1);
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          page: 0,
          size: comp.itemsPerPage,
          sort: ['porNumber,desc', 'id,desc'],
        })
      );
      expect(comp.ascending).toBe(false);
      expect(comp.page).toBe(1);
      expect(comp.pageToLoad).toBe(1);
      expect(navigateMock).toHaveBeenLastCalledWith(['/portability'], {
        queryParams: {
          page: 1,
          size: comp.itemsPerPage,
          sort: 'porNumber,desc',
        },
      });

      // WHEN
      comp.ngOnDestroy();
      comp.empTbSort.sortChange.emit({
        active: 'id',
        direction: 'asc',
      });

      // THEN
      expect(service.query).toHaveBeenCalledTimes(callsBeforeSort + 1);
    });

    it('should restore URL filters into the form and backend query', () => {
      // GIVEN
      const routeData = new Subject<Record<string, unknown>>();
      const routeParams = new Subject<ParamMap>();
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;
      const navigateMock = jest.mocked(TestBed.inject(Router).navigate);

      navigateMock.mockClear();
      Object.defineProperty(route, 'data', { value: routeData });
      Object.defineProperty(route, 'queryParamMap', { value: routeParams });

      comp.ngOnInit();
      routeData.next({ defaultSort: 'porCrDate,asc' });

      // WHEN
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '2',
          size: '50',
          sort: 'porNumber,desc',
          porRequestId: 'REQ-1',
          porCrDate: '2026-07-29',
          donor: 'MCI',
          recipient: 'MTN',
        })
      );

      // THEN
      expect(comp.editForm.value).toEqual(
        expect.objectContaining({
          porRequestId: 'REQ-1',
          porCrDate: '2026-07-29',
          donor: 'MCI',
          recipient: 'MTN',
        })
      );
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          'porRequestId.equals': 'REQ-1',
          'porOpd.equals': 'MCI',
          'porOpr.equals': 'MTN',
          page: 1,
          size: 50,
          sort: ['porNumber,desc', 'id,desc'],
        })
      );
      expect(navigateMock).not.toHaveBeenCalled();
    });

    it('should write active filters to the URL after search', () => {
      // GIVEN
      const navigateMock = jest.mocked(TestBed.inject(Router).navigate);

      navigateMock.mockClear();
      comp.itemsPerPage = 50;
      comp.editForm.patchValue({
        porRequestId: 'REQ-2',
        porCrDate: '2026-07-29',
        donor: 'MCI',
        recipient: 'MTN',
      });

      // WHEN
      comp.search();

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          'porRequestId.equals': 'REQ-2',
          'porOpd.equals': 'MCI',
          'porOpr.equals': 'MTN',
          page: 0,
          size: 50,
          sort: ['porCrDate,asc', 'id,asc'],
        })
      );
      expect(navigateMock).toHaveBeenLastCalledWith(['/portability'], {
        queryParams: expect.objectContaining({
          page: 1,
          size: 50,
          sort: 'porCrDate,asc',
          porRequestId: 'REQ-2',
          porCrDate: expect.any(String),
          donor: 'MCI',
          recipient: 'MTN',
        }),
      });
    });

    it('should not duplicate the request when the URL reflects searched filters', () => {
      // GIVEN
      const routeData = new Subject<Record<string, unknown>>();
      const routeParams = new Subject<ParamMap>();
      const route = (comp as unknown as { activatedRoute: ActivatedRoute }).activatedRoute;

      Object.defineProperty(route, 'data', { value: routeData });
      Object.defineProperty(route, 'queryParamMap', { value: routeParams });

      comp.ngOnInit();
      routeData.next({ defaultSort: 'porCrDate,asc' });
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '1',
          size: '10',
          sort: 'porCrDate,asc',
        })
      );

      comp.editForm.patchValue({ porRequestId: 'REQ-3' });

      // WHEN
      comp.search();
      routeParams.next(
        jest.requireActual('@angular/router').convertToParamMap({
          page: '1',
          size: '10',
          sort: 'porCrDate,asc',
          porRequestId: 'REQ-3',
        })
      );

      // THEN
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(service.query).toHaveBeenLastCalledWith(
        expect.objectContaining({
          'porRequestId.equals': 'REQ-3',
          page: 0,
          size: 10,
        })
      );
    });

    it('should remove filter query parameters after clear', () => {
      // GIVEN
      const navigateMock = jest.mocked(TestBed.inject(Router).navigate);

      navigateMock.mockClear();
      comp.editForm.patchValue({
        porRequestId: 'REQ-4',
        donor: 'MCI',
      });
      comp.search();
      navigateMock.mockClear();

      // WHEN
      comp.clear();

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(
        expect.not.objectContaining({
          'porRequestId.equals': expect.anything(),
          'porOpd.equals': expect.anything(),
        })
      );
      expect(navigateMock).toHaveBeenLastCalledWith(['/portability'], {
        queryParams: {
          page: 1,
          size: comp.itemsPerPage,
          sort: 'porCrDate,asc',
        },
      });
    });
    it('should calculate the sort attribute for default porCrDate sort', () => {
      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 }, true);

      // THEN
      expect(service.query).toHaveBeenCalledWith(expect.objectContaining({ sort: ['porCrDate,asc', 'id,asc'] }));
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';
      comp.ascending = false;

      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 }, true);

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['name,desc', 'id,desc'] }));
    });
  });
});
