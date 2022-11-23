import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommission, Commission } from '../commission.model';
import { CommissionService } from '../service/commission.service';

@Injectable({ providedIn: 'root' })
export class CommissionRoutingResolveService implements Resolve<ICommission> {
  constructor(protected service: CommissionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommission> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((commission: HttpResponse<Commission>) => {
          if (commission.body) {
            return of(commission.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Commission());
  }
}
