import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UndifinedStatusComponent } from './list/undifined-status.component';
import { UndifinedStatusDetailComponent } from './detail/undifined-status-detail.component';
import { UndifinedStatusUpdateComponent } from './update/undifined-status-update.component';
import { UndifinedStatusDeleteDialogComponent } from './delete/undifined-status-delete-dialog.component';
import { UndifinedStatusRoutingModule } from './route/undifined-status-routing.module';

@NgModule({
  imports: [SharedModule, UndifinedStatusRoutingModule],
  declarations: [
    UndifinedStatusComponent,
    UndifinedStatusDetailComponent,
    UndifinedStatusUpdateComponent,
    UndifinedStatusDeleteDialogComponent,
  ],
})
export class UndifinedStatusModule {}
