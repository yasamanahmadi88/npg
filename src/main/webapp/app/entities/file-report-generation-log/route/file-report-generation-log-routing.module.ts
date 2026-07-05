import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthActivateService } from 'app/core/auth/auth-activate.service';
import { FileReportGenerationLogComponent } from '../list/file-report-generation-log.component';
import { FileReportGenerationLogDetailComponent } from '../detail/file-report-generation-log-detail.component';
import { FileReportGenerationLogUpdateComponent } from '../update/file-report-generation-log-update.component';
import { FileReportGenerationLogRoutingResolveService } from './file-report-generation-log-routing-resolve.service';

const fileReportGenerationLogRoute: Routes = [
  {
    path: '',
    component: FileReportGenerationLogComponent,
    data: {
      defaultSort: 'id,asc',
      params: ['fileReportGenerationLog', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/view',
    component: FileReportGenerationLogDetailComponent,
    resolve: {
      fileReportGenerationLog: FileReportGenerationLogRoutingResolveService,
    },
    data: {
      params: ['fileReportGenerationLog', 'view'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: 'new',
    component: FileReportGenerationLogUpdateComponent,
    resolve: {
      fileReportGenerationLog: FileReportGenerationLogRoutingResolveService,
    },
    data: {
      params: ['fileReportGenerationLog', 'create'],
    },
    canActivate: [AuthActivateService],
  },
  {
    path: ':id/edit',
    component: FileReportGenerationLogUpdateComponent,
    resolve: {
      fileReportGenerationLog: FileReportGenerationLogRoutingResolveService,
    },
    data: {
      params: ['fileReportGenerationLog', 'edit'],
    },
    canActivate: [AuthActivateService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fileReportGenerationLogRoute)],
  exports: [RouterModule],
})
export class FileReportGenerationLogRoutingModule {}
