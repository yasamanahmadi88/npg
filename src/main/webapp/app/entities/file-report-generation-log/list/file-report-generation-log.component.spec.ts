jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';

import { FileReportGenerationLogComponent } from './file-report-generation-log.component';

describe('Component Tests', () => {
  describe('FileReportGenerationLog Management Component', () => {
    let comp: FileReportGenerationLogComponent;
    let fixture: ComponentFixture<FileReportGenerationLogComponent>;
    let service: FileReportGenerationLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FileReportGenerationLogComponent],
        providers: [
          { provide: Router, useValue: { navigate: jest.fn() } },
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                fileReportGenerationLog: [{ id: 123 }],
                defaultSort: 'id,asc',
              }),
            },
          },
        ],
      })
        .overrideTemplate(FileReportGenerationLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileReportGenerationLogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FileReportGenerationLogService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should load initial data after view init', () => {
      // WHEN
      fixture.detectChanges();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fileReportGenerationLogs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fileReportGenerationLogs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // GIVEN
      comp.predicate = 'id';
      comp.ascending = true;

      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);

      // THEN
      expect(service.query).toHaveBeenCalledWith(expect.objectContaining({ sort: ['id,asc'] }));
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';
      comp.ascending = false;

      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['name,desc', 'id'] }));
    });
  });
});