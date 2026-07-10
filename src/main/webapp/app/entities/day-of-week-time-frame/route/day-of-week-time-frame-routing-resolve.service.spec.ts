jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDayOfWeekTimeFrame, DayOfWeekTimeFrame } from '../day-of-week-time-frame.model';
import { DayOfWeekTimeFrameService } from '../service/day-of-week-time-frame.service';

import { DayOfWeekTimeFrameRoutingResolveService } from './day-of-week-time-frame-routing-resolve.service';

describe('Service Tests', () => {
  describe('DayOfWeekTimeFrame routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DayOfWeekTimeFrameRoutingResolveService;
    let service: DayOfWeekTimeFrameService;
    let resultDayOfWeekTimeFrame: IDayOfWeekTimeFrame | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [DayOfWeekTimeFrameService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DayOfWeekTimeFrameRoutingResolveService);
      service = TestBed.inject(DayOfWeekTimeFrameService);
      resultDayOfWeekTimeFrame = undefined;
    });

    describe('resolve', () => {
      it('should return IDayOfWeekTimeFrame returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDayOfWeekTimeFrame = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultDayOfWeekTimeFrame).toEqual({ id: 123 });
      });

      it('should return new IDayOfWeekTimeFrame if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDayOfWeekTimeFrame = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultDayOfWeekTimeFrame).toEqual(new DayOfWeekTimeFrame());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DayOfWeekTimeFrame })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDayOfWeekTimeFrame = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultDayOfWeekTimeFrame).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
