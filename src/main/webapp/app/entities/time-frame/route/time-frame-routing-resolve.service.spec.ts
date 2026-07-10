jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITimeFrame, TimeFrame } from '../time-frame.model';
import { TimeFrameService } from '../service/time-frame.service';

import { TimeFrameRoutingResolveService } from './time-frame-routing-resolve.service';

describe('Service Tests', () => {
  describe('TimeFrame routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TimeFrameRoutingResolveService;
    let service: TimeFrameService;
    let resultTimeFrame: ITimeFrame | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [TimeFrameService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TimeFrameRoutingResolveService);
      service = TestBed.inject(TimeFrameService);
      resultTimeFrame = undefined;
    });

    describe('resolve', () => {
      it('should return ITimeFrame returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTimeFrame = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultTimeFrame).toEqual({ id: 123 });
      });

      it('should return new ITimeFrame if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTimeFrame = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultTimeFrame).toEqual(new TimeFrame());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TimeFrame })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTimeFrame = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultTimeFrame).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
