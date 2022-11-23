import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BailleurComponent } from './list/bailleur.component';
import { BailleurDetailComponent } from './detail/bailleur-detail.component';
import { BailleurUpdateComponent } from './update/bailleur-update.component';
import { BailleurDeleteDialogComponent } from './delete/bailleur-delete-dialog.component';
import { BailleurRoutingModule } from './route/bailleur-routing.module';

@NgModule({
  imports: [SharedModule, BailleurRoutingModule],
  declarations: [BailleurComponent, BailleurDetailComponent, BailleurUpdateComponent, BailleurDeleteDialogComponent],
  entryComponents: [BailleurDeleteDialogComponent],
})
export class BailleurModule {}
