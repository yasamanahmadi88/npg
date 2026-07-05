import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthorityComponent } from './list/authority.component';
import { AuthorityDetailComponent } from './detail/authority-detail.component';
import { AuthorityUpdateComponent } from './update/authority-update.component';
import { AuthorityDeleteDialogComponent } from './delete/authority-delete-dialog.component';
import { authorityRoute } from './route/authority.route';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(authorityRoute)],
  declarations: [AuthorityComponent, AuthorityDetailComponent, AuthorityUpdateComponent, AuthorityDeleteDialogComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class NpgAuthorityModule {}
