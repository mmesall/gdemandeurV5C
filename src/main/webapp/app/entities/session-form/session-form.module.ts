import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SessionFormComponent } from './list/session-form.component';
import { SessionFormDetailComponent } from './detail/session-form-detail.component';
import { SessionFormUpdateComponent } from './update/session-form-update.component';
import { SessionFormDeleteDialogComponent } from './delete/session-form-delete-dialog.component';
import { SessionFormRoutingModule } from './route/session-form-routing.module';

@NgModule({
  imports: [SharedModule, SessionFormRoutingModule],
  declarations: [SessionFormComponent, SessionFormDetailComponent, SessionFormUpdateComponent, SessionFormDeleteDialogComponent],
  entryComponents: [SessionFormDeleteDialogComponent],
})
export class SessionFormModule {}
