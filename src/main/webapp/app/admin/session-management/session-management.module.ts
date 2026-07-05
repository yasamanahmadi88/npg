import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule } from '@angular/router';
import { sessionManagementRoute } from './session-management.route';
import { SessionManagementComponent } from './list/session-management.component';
import { NpgPortalCommonModule } from '../../common/common.module';

@NgModule({
  declarations: [SessionManagementComponent],
  imports: [SharedModule, RouterModule.forChild(sessionManagementRoute), NpgPortalCommonModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SessionManagementModule {}
