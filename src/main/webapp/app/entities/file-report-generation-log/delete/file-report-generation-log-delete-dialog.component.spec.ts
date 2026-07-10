import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';
import { FileReportGenerationLogDeleteDialogComponent } from './file-report-generation-log-delete-dialog.component';

describe('Component Tests', () => {
  describe('FileReportGenerationLogDeleteDialogComponent', () => {
    let comp: FileReportGenerationLogDeleteDialogComponent;
    let fixture: ComponentFixture<FileReportGenerationLogDeleteDialogComponent>;
    let service: FileReportGenerationLogService;
    let mockActiveModal: { close: jest.Mock; dismiss: jest.Mock };

    beforeEach(() => {
      mockActiveModal = {
        close: jest.fn(),
        dismiss: jest.fn(),
      };

      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FileReportGenerationLogDeleteDialogComponent],
        providers: [{ provide: NgbActiveModal, useValue: mockActiveModal }],
      })
        .overrideTemplate(FileReportGenerationLogDeleteDialogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileReportGenerationLogDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FileReportGenerationLogService);
    });

    it('Should call delete service on confirmDelete', () => {
      // GIVEN
      const entityId = 123;
      jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>({})));

      // WHEN
      comp.confirmDelete(entityId as never);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(entityId);
      expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
    });

    it('Should not call delete service on cancel', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});