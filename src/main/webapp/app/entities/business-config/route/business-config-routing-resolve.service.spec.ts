jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBusinessConfig, BusinessConfig } from '../business-config.model';
import { BusinessConfigService } from '../service/business-config.service';

import { BusinessConfigRoutingResolveService } from './business-config-routing-resolve.service';

describe('Service Tests', () => {
  describe('BusinessConfig routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BusinessConfigRoutingResolveService;
    let service: BusinessConfigService;
    let resultBusinessConfig: IBusinessConfig | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [BusinessConfigService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BusinessConfigRoutingResolveService);
      service = TestBed.inject(BusinessConfigService);
      resultBusinessConfig = undefined;
    });

    describe('resolve', () => {
      it('should return IBusinessConfig returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: '123' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBusinessConfig = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith('123');
        expect(resultBusinessConfig).toEqual({ id: '123' });
      });

      it('should return new IBusinessConfig if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBusinessConfig = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultBusinessConfig).toEqual(new BusinessConfig());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BusinessConfig })));
        mockActivatedRouteSnapshot.params = { id: '123' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBusinessConfig = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith('123');
        expect(resultBusinessConfig).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
