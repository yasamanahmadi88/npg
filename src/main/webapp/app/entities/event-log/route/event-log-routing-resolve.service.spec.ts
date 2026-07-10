jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEventLog, EventLog } from '../event-log.model';
import { EventLogService } from '../service/event-log.service';

import { EventLogRoutingResolveService } from './event-log-routing-resolve.service';

describe('Service Tests', () => {
  describe('EventLog routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EventLogRoutingResolveService;
    let service: EventLogService;
    let resultEventLog: IEventLog | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [EventLogService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EventLogRoutingResolveService);
      service = TestBed.inject(EventLogService);
      resultEventLog = undefined;
    });

    describe('resolve', () => {
      it('should return IEventLog returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEventLog = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultEventLog).toEqual({ id: 123 });
      });

      it('should return new IEventLog if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEventLog = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultEventLog).toEqual(new EventLog());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EventLog })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEventLog = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultEventLog).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
