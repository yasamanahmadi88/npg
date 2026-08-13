jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICrmToCrdbResponseMap, CrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';
import { CrmToCrdbResponseMapService } from '../service/crm-to-crdb-response-map.service';

import { CrmToCrdbResponseMapRoutingResolveService } from './crm-to-crdb-response-map-routing-resolve.service';

describe('Service Tests', () => {
  describe('CrmToCrdbResponseMap routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CrmToCrdbResponseMapRoutingResolveService;
    let service: CrmToCrdbResponseMapService;
    let resultCrmToCrdbResponseMap: ICrmToCrdbResponseMap | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [CrmToCrdbResponseMapService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CrmToCrdbResponseMapRoutingResolveService);
      service = TestBed.inject(CrmToCrdbResponseMapService);
      resultCrmToCrdbResponseMap = undefined;
    });

    describe('resolve', () => {
      it('should return ICrmToCrdbResponseMap returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCrmToCrdbResponseMap = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultCrmToCrdbResponseMap).toEqual({ id: 123 });
      });

      it('should return new ICrmToCrdbResponseMap if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCrmToCrdbResponseMap = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultCrmToCrdbResponseMap).toEqual(new CrmToCrdbResponseMap());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CrmToCrdbResponseMap })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCrmToCrdbResponseMap = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultCrmToCrdbResponseMap).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
