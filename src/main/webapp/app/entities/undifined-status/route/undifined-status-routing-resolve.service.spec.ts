jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUndifinedStatus, UndifinedStatus } from '../undifined-status.model';
import { UndifinedStatusService } from '../service/undifined-status.service';

import { UndifinedStatusRoutingResolveService } from './undifined-status-routing-resolve.service';

describe('Service Tests', () => {
  describe('UndifinedStatus routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: UndifinedStatusRoutingResolveService;
    let service: UndifinedStatusService;
    let resultUndifinedStatus: IUndifinedStatus | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [UndifinedStatusService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(UndifinedStatusRoutingResolveService);
      service = TestBed.inject(UndifinedStatusService);
      resultUndifinedStatus = undefined;
    });

    describe('resolve', () => {
      it('should return IUndifinedStatus returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUndifinedStatus = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultUndifinedStatus).toEqual({ id: 123 });
      });

      it('should return new IUndifinedStatus if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUndifinedStatus = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultUndifinedStatus).toEqual(new UndifinedStatus());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UndifinedStatus })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUndifinedStatus = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultUndifinedStatus).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
