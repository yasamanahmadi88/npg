jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOffDay, OffDay } from '../off-day.model';
import { OffDayService } from '../service/off-day.service';

import { OffDayRoutingResolveService } from './off-day-routing-resolve.service';

describe('Service Tests', () => {
  describe('OffDay routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OffDayRoutingResolveService;
    let service: OffDayService;
    let resultOffDay: IOffDay | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [OffDayService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OffDayRoutingResolveService);
      service = TestBed.inject(OffDayService);
      resultOffDay = undefined;
    });

    describe('resolve', () => {
      it('should return IOffDay returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOffDay = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultOffDay).toEqual({ id: 123 });
      });

      it('should return new IOffDay if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOffDay = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultOffDay).toEqual(new OffDay());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OffDay })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOffDay = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultOffDay).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
