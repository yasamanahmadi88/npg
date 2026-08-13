jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPortabilityLog, PortabilityLog } from '../portability-log.model';
import { PortabilityLogService } from '../service/portability-log.service';

import { PortabilityLogRoutingResolveService } from './portability-log-routing-resolve.service';

describe('Service Tests', () => {
  describe('PortabilityLog routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PortabilityLogRoutingResolveService;
    let service: PortabilityLogService;
    let resultPortabilityLog: IPortabilityLog | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [PortabilityLogService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PortabilityLogRoutingResolveService);
      service = TestBed.inject(PortabilityLogService);
      resultPortabilityLog = undefined;
    });

    describe('resolve', () => {
      it('should return IPortabilityLog returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPortabilityLog = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultPortabilityLog).toEqual({ id: 123 });
      });

      it('should return new IPortabilityLog if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPortabilityLog = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultPortabilityLog).toEqual(new PortabilityLog());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PortabilityLog })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPortabilityLog = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultPortabilityLog).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
