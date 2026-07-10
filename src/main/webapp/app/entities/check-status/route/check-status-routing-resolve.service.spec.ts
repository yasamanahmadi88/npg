jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICheckStatus, CheckStatus } from '../check-status.model';
import { CheckStatusService } from '../service/check-status.service';

import { CheckStatusRoutingResolveService } from './check-status-routing-resolve.service';

describe('Service Tests', () => {
  describe('CheckStatus routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CheckStatusRoutingResolveService;
    let service: CheckStatusService;
    let resultCheckStatus: ICheckStatus | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [CheckStatusService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CheckStatusRoutingResolveService);
      service = TestBed.inject(CheckStatusService);
      resultCheckStatus = undefined;
    });

    describe('resolve', () => {
      it('should return ICheckStatus returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckStatus = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultCheckStatus).toEqual({ id: 123 });
      });

      it('should return new ICheckStatus if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckStatus = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultCheckStatus).toEqual(new CheckStatus());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CheckStatus })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckStatus = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultCheckStatus).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
