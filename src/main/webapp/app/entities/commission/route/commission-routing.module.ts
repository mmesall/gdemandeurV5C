import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommissionComponent } from '../list/commission.component';
import { CommissionDetailComponent } from '../detail/commission-detail.component';
import { CommissionUpdateComponent } from '../update/commission-update.component';
import { CommissionRoutingResolveService } from './commission-routing-resolve.service';

const commissionRoute: Routes = [
  {
    path: '',
    component: CommissionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommissionDetailComponent,
    resolve: {
      commission: CommissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommissionUpdateComponent,
    resolve: {
      commission: CommissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommissionUpdateComponent,
    resolve: {
      commission: CommissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commissionRoute)],
  exports: [RouterModule],
})
export class CommissionRoutingModule {}
