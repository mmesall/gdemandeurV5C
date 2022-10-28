import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PriseEnChargeComponent } from './list/prise-en-charge.component';
import { PriseEnChargeDetailComponent } from './detail/prise-en-charge-detail.component';
import { PriseEnChargeUpdateComponent } from './update/prise-en-charge-update.component';
import { PriseEnChargeDeleteDialogComponent } from './delete/prise-en-charge-delete-dialog.component';
import { PriseEnChargeRoutingModule } from './route/prise-en-charge-routing.module';

@NgModule({
  imports: [SharedModule, PriseEnChargeRoutingModule],
  declarations: [PriseEnChargeComponent, PriseEnChargeDetailComponent, PriseEnChargeUpdateComponent, PriseEnChargeDeleteDialogComponent],
  entryComponents: [PriseEnChargeDeleteDialogComponent],
})
export class PriseEnChargeModule {}
