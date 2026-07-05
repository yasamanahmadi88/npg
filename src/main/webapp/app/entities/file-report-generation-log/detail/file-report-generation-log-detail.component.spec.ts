import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FileReportGenerationLogDetailComponent } from './file-report-generation-log-detail.component';

describe('Component Tests', () => {
  describe('FileReportGenerationLog Management Detail Component', () => {
    let comp: FileReportGenerationLogDetailComponent;
    let fixture: ComponentFixture<FileReportGenerationLogDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FileReportGenerationLogDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fileReportGenerationLog: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FileReportGenerationLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileReportGenerationLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fileReportGenerationLog on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileReportGenerationLog).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
