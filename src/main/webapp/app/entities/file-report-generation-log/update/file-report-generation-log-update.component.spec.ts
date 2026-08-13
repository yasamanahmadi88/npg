jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';
import { IFileReportGenerationLog, FileReportGenerationLog } from '../file-report-generation-log.model';

import { FileReportGenerationLogUpdateComponent } from './file-report-generation-log-update.component';

describe('Component Tests', () => {
  describe('FileReportGenerationLog Management Update Component', () => {
    let comp: FileReportGenerationLogUpdateComponent;
    let fixture: ComponentFixture<FileReportGenerationLogUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fileReportGenerationLogService: FileReportGenerationLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FileReportGenerationLogUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FileReportGenerationLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileReportGenerationLogUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fileReportGenerationLogService = TestBed.inject(FileReportGenerationLogService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fileReportGenerationLog: IFileReportGenerationLog = { id: 456 };

        activatedRoute.data = of({ fileReportGenerationLog });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fileReportGenerationLog));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FileReportGenerationLog>>();
        const fileReportGenerationLog = { id: 123 };
        jest.spyOn(fileReportGenerationLogService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fileReportGenerationLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fileReportGenerationLog }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fileReportGenerationLogService.update).toHaveBeenCalledWith(fileReportGenerationLog);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FileReportGenerationLog>>();
        const fileReportGenerationLog = new FileReportGenerationLog();
        jest.spyOn(fileReportGenerationLogService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fileReportGenerationLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fileReportGenerationLog }));
        saveSubject.complete();

        // THEN
        expect(fileReportGenerationLogService.create).toHaveBeenCalledWith(fileReportGenerationLog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FileReportGenerationLog>>();
        const fileReportGenerationLog = { id: 123 };
        jest.spyOn(fileReportGenerationLogService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fileReportGenerationLog });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fileReportGenerationLogService.update).toHaveBeenCalledWith(fileReportGenerationLog);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
