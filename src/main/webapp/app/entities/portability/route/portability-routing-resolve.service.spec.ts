jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPortability, Portability } from '../portability.model';
import { PortabilityService } from '../service/portability.service';

import { PortabilityRoutingResolveService } from './portability-routing-resolve.service';

describe('Service Tests', () => {
  describe('Portability routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PortabilityRoutingResolveService;
    let service: PortabilityService;
    let resultPortability: IPortability | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [PortabilityService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PortabilityRoutingResolveService);
      service = TestBed.inject(PortabilityService);
      resultPortability = undefined;
    });

    describe('resolve', () => {
      it('should return IPortability returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPortability = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultPortability).toEqual({ id: 123 });
      });

      it('should return new IPortability if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPortability = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultPortability).toEqual(new Portability());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Portability })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPortability = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultPortability).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
