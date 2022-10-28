import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISessionForm, SessionForm } from '../session-form.model';
import { SessionFormService } from '../service/session-form.service';

@Injectable({ providedIn: 'root' })
export class SessionFormRoutingResolveService implements Resolve<ISessionForm> {
  constructor(protected service: SessionFormService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISessionForm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sessionForm: HttpResponse<SessionForm>) => {
          if (sessionForm.body) {
            return of(sessionForm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SessionForm());
  }
}
