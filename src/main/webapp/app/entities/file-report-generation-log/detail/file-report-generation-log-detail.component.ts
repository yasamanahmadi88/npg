import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFileReportGenerationLog } from '../file-report-generation-log.model';

@Component({
  selector: 'jhi-file-report-generation-log-detail',
  templateUrl: './file-report-generation-log-detail.component.html',
  standalone: false,
})
export class FileReportGenerationLogDetailComponent implements OnInit {
  fileReportGenerationLog: IFileReportGenerationLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileReportGenerationLog }) => {
      this.fileReportGenerationLog = fileReportGenerationLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
