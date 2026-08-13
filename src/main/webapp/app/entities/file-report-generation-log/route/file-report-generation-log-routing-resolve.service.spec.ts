jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFileReportGenerationLog, FileReportGenerationLog } from '../file-report-generation-log.model';
import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';

import { FileReportGenerationLogRoutingResolveService } from './file-report-generation-log-routing-resolve.service';

describe('Service Tests', () => {
  describe('FileReportGenerationLog routing resolve service', () => {
    let mockRouter: { navigate: jest.Mock };
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FileReportGenerationLogRoutingResolveService;
    let service: FileReportGenerationLogService;
    let resultFileReportGenerationLog: IFileReportGenerationLog | undefined;

    beforeEach(() => {
      mockRouter = { navigate: jest.fn() };
        TestBed.configureTestingModule({
        providers: [FileReportGenerationLogService, { provide: Router, useValue: mockRouter }, { provide: ActivatedRouteSnapshot, useValue: new ActivatedRouteSnapshot() }],
      });
      mockRouter = TestBed.inject(Router) as unknown as { navigate: jest.Mock };
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FileReportGenerationLogRoutingResolveService);
      service = TestBed.inject(FileReportGenerationLogService);
      resultFileReportGenerationLog = undefined;
    });

    describe('resolve', () => {
      it('should return IFileReportGenerationLog returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFileReportGenerationLog = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultFileReportGenerationLog).toEqual({ id: 123 });
      });

      it('should return new IFileReportGenerationLog if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFileReportGenerationLog = result;
        });

        // THEN
        expect(service.find).not.toHaveBeenCalled();
        expect(resultFileReportGenerationLog).toEqual(new FileReportGenerationLog());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FileReportGenerationLog })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFileReportGenerationLog = result;
        });

        // THEN
        expect(service.find).toHaveBeenCalledWith(123);
        expect(resultFileReportGenerationLog).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
