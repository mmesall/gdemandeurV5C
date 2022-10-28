import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommissionComponent } from './list/commission.component';
import { CommissionDetailComponent } from './detail/commission-detail.component';
import { CommissionUpdateComponent } from './update/commission-update.component';
import { CommissionDeleteDialogComponent } from './delete/commission-delete-dialog.component';
import { CommissionRoutingModule } from './route/commission-routing.module';

@NgModule({
  imports: [SharedModule, CommissionRoutingModule],
  declarations: [CommissionComponent, CommissionDetailComponent, CommissionUpdateComponent, CommissionDeleteDialogComponent],
  entryComponents: [CommissionDeleteDialogComponent],
})
export class CommissionModule {}
