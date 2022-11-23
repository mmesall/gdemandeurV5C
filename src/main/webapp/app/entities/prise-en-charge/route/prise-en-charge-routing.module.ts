import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PriseEnChargeComponent } from '../list/prise-en-charge.component';
import { PriseEnChargeDetailComponent } from '../detail/prise-en-charge-detail.component';
import { PriseEnChargeUpdateComponent } from '../update/prise-en-charge-update.component';
import { PriseEnChargeRoutingResolveService } from './prise-en-charge-routing-resolve.service';

const priseEnChargeRoute: Routes = [
  {
    path: '',
    component: PriseEnChargeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PriseEnChargeDetailComponent,
    resolve: {
      priseEnCharge: PriseEnChargeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PriseEnChargeUpdateComponent,
    resolve: {
      priseEnCharge: PriseEnChargeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PriseEnChargeUpdateComponent,
    resolve: {
      priseEnCharge: PriseEnChargeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(priseEnChargeRoute)],
  exports: [RouterModule],
})
export class PriseEnChargeRoutingModule {}
