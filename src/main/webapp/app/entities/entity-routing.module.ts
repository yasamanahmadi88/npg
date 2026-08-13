import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'resource',
        data: { pageTitle: 'npgPortalApp.resource.home.title' },
        loadChildren: () => import('./resource/resource.module').then(m => m.ResourceModule),
      },
      {
        path: 'resource-authority',
        data: { pageTitle: 'npgPortalApp.resourceAuthority.home.title' },
        loadChildren: () => import('./resource-authority/resource-authority.module').then(m => m.ResourceAuthorityModule),
      },
      {
        path: 'day-of-week-time-frame',
        data: { pageTitle: 'npgPortalApp.dayOfWeekTimeFrame.home.title' },
        loadChildren: () => import('./day-of-week-time-frame/day-of-week-time-frame.module').then(m => m.DayOfWeekTimeFrameModule),
      },
      {
        path: 'crm-to-crdb-response-map',
        data: { pageTitle: 'npgPortalApp.crmToCrdbResponseMap.home.title' },
        loadChildren: () => import('./crm-to-crdb-response-map/crm-to-crdb-response-map.module').then(m => m.CrmToCrdbResponseMapModule),
      },
      {
        path: 'off-day',
        data: { pageTitle: 'npgPortalApp.offDay.home.title' },
        loadChildren: () => import('./off-day/off-day.module').then(m => m.OffDayModule),
      },
      {
        path: 'time-frame',
        data: { pageTitle: 'npgPortalApp.timeFrame.home.title' },
        loadChildren: () => import('./time-frame/time-frame.module').then(m => m.TimeFrameModule),
      },
      {
        path: 'portability',
        data: { pageTitle: 'npgPortalApp.portability.home.title' },
        loadChildren: () => import('./portability/portability.module').then(m => m.PortabilityModule),
      },
      {
        path: 'authority',
        data: { pageTitle: 'npgPortalApp.authority.home.title' },
        loadChildren: () => import('./authority/authority.module').then(m => m.NpgAuthorityModule),
      },
      {
        path: 'setting',
        data: { pageTitle: 'npgPortalApp.setting.home.title' },
        loadChildren: () => import('./setting/setting.module').then(m => m.SettingModule),
      },
      {
        path: 'undifined-status',
        data: { pageTitle: 'npgPortalApp.undifinedStatus.home.title' },
        loadChildren: () => import('./undifined-status/undifined-status.module').then(m => m.UndifinedStatusModule),
      },
      {
        path: 'notification-template',
        data: { pageTitle: 'npgPortalApp.notificationTemplate.home.title' },
        loadChildren: () => import('./notification-template/notification-template.module').then(m => m.NotificationTemplateModule),
      },
      {
        path: 'business-config',
        data: { pageTitle: 'npgPortalApp.businessConfig.home.title' },
        loadChildren: () => import('./business-config/business-config.module').then(m => m.BusinessConfigModule),
      },
      {
        path: 'portability-log',
        data: { pageTitle: 'npgPortalApp.portabilityLog.home.title' },
        loadChildren: () => import('./portability-log/portability-log.module').then(m => m.PortabilityLogModule),
      },
      {
        path: 'event-log',
        data: { pageTitle: 'npgPortalApp.eventLog.home.title' },
        loadChildren: () => import('./event-log/event-log.module').then(m => m.EventLogModule),
      },
      {
        path: 'check-status',
        data: { pageTitle: 'npgPortalApp.checkStatus.home.title' },
        loadChildren: () => import('./check-status/check-status.module').then(m => m.CheckStatusModule),
      },
      {
        path: 'file-report-generation-log',
        data: { pageTitle: 'npgPortalApp.fileReportGenerationLog.home.title' },
        loadChildren: () =>
          import('./file-report-generation-log/file-report-generation-log.module').then(m => m.FileReportGenerationLogModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
