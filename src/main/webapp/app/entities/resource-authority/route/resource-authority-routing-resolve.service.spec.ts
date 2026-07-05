jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IResourceAuthority, ResourceAuthority } from '../resource-authority.model';
import { ResourceAuthorityService } from '../service/resource-authority.service';

import { ResourceAuthorityRoutingResolveService } from './resource-authority-routing-resolve.service';

describe('Service Tests', () => {
  describe('ResourceAuthority routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ResourceAuthorityRoutingResolveService;
    let service: ResourceAuthorityService;
    let resultResourceAuthority: IResourceAuthority | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [ResourceAuthorityService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ResourceAuthorityRoutingResolveService);
      service = TestBed.inject(ResourceAuthorityService);
      resultResourceAuthority = undefined;
    });

    describe('resolve', () => {
      it('should return IResourceAuthority returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResourceAuthority = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultResourceAuthority).toEqual({ id: 123 });
      });

      it('should return new IResourceAuthority if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResourceAuthority = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultResourceAuthority).toEqual(new ResourceAuthority());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResourceAuthority = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultResourceAuthority).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
