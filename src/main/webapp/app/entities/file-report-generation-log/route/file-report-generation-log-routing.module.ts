import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
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
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FileReportGenerationLogDetailComponent,
    resolve: {
      fileReportGenerationLog: FileReportGenerationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FileReportGenerationLogUpdateComponent,
    resolve: {
      fileReportGenerationLog: FileReportGenerationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FileReportGenerationLogUpdateComponent,
    resolve: {
      fileReportGenerationLog: FileReportGenerationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fileReportGenerationLogRoute)],
  exports: [RouterModule],
})
export class FileReportGenerationLogRoutingModule {}
