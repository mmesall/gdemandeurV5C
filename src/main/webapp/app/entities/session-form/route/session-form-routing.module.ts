import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SessionFormComponent } from '../list/session-form.component';
import { SessionFormDetailComponent } from '../detail/session-form-detail.component';
import { SessionFormUpdateComponent } from '../update/session-form-update.component';
import { SessionFormRoutingResolveService } from './session-form-routing-resolve.service';

const sessionFormRoute: Routes = [
  {
    path: '',
    component: SessionFormComponent,
    data: {
      defaultSort: 'id,asc',
    },
    // canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SessionFormDetailComponent,
    resolve: {
      sessionForm: SessionFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SessionFormUpdateComponent,
    resolve: {
      sessionForm: SessionFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SessionFormUpdateComponent,
    resolve: {
      sessionForm: SessionFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sessionFormRoute)],
  exports: [RouterModule],
})
export class SessionFormRoutingModule {}
