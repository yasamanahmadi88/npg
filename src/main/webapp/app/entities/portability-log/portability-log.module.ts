import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PortabilityLogComponent } from './list/portability-log.component';
import { PortabilityLogDetailComponent } from './detail/portability-log-detail.component';
import { PortabilityLogUpdateComponent } from './update/portability-log-update.component';
import { PortabilityLogDeleteDialogComponent } from './delete/portability-log-delete-dialog.component';
import { PortabilityLogRoutingModule } from './route/portability-log-routing.module';

@NgModule({
  imports: [SharedModule, PortabilityLogRoutingModule],
  declarations: [
    PortabilityLogComponent,
    PortabilityLogDetailComponent,
    PortabilityLogUpdateComponent,
    PortabilityLogDeleteDialogComponent,
  ],
})
export class PortabilityLogModule {}
