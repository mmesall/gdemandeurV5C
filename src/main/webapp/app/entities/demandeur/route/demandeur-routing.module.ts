import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeurComponent } from '../list/demandeur.component';
import { DemandeurDetailComponent } from '../detail/demandeur-detail.component';
import { DemandeurUpdateComponent } from '../update/demandeur-update.component';
import { DemandeurRoutingResolveService } from './demandeur-routing-resolve.service';

const demandeurRoute: Routes = [
  {
    path: '',
    component: DemandeurComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeurDetailComponent,
    resolve: {
      demandeur: DemandeurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeurUpdateComponent,
    resolve: {
      demandeur: DemandeurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeurUpdateComponent,
    resolve: {
      demandeur: DemandeurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeurRoute)],
  exports: [RouterModule],
})
export class DemandeurRoutingModule {}
