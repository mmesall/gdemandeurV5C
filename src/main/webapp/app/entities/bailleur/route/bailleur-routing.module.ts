import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BailleurComponent } from '../list/bailleur.component';
import { BailleurDetailComponent } from '../detail/bailleur-detail.component';
import { BailleurUpdateComponent } from '../update/bailleur-update.component';
import { BailleurRoutingResolveService } from './bailleur-routing-resolve.service';

const bailleurRoute: Routes = [
  {
    path: '',
    component: BailleurComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BailleurDetailComponent,
    resolve: {
      bailleur: BailleurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BailleurUpdateComponent,
    resolve: {
      bailleur: BailleurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BailleurUpdateComponent,
    resolve: {
      bailleur: BailleurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bailleurRoute)],
  exports: [RouterModule],
})
export class BailleurRoutingModule {}
