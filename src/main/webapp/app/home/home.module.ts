import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { NpgPortalCommonModule } from '../common/common.module';
import { LoginModule } from '../login/login.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), NpgPortalCommonModule, LoginModule],
  declarations: [HomeComponent],
})
export class HomeModule {}
