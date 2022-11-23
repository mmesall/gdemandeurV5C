import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeurComponent } from './list/demandeur.component';
import { DemandeurDetailComponent } from './detail/demandeur-detail.component';
import { DemandeurUpdateComponent } from './update/demandeur-update.component';
import { DemandeurDeleteDialogComponent } from './delete/demandeur-delete-dialog.component';
import { DemandeurRoutingModule } from './route/demandeur-routing.module';

@NgModule({
  imports: [SharedModule, DemandeurRoutingModule],
  declarations: [DemandeurComponent, DemandeurDetailComponent, DemandeurUpdateComponent, DemandeurDeleteDialogComponent],
  entryComponents: [DemandeurDeleteDialogComponent],
})
export class DemandeurModule {}
