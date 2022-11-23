import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPriseEnCharge, PriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeService } from '../service/prise-en-charge.service';

@Injectable({ providedIn: 'root' })
export class PriseEnChargeRoutingResolveService implements Resolve<IPriseEnCharge> {
  constructor(protected service: PriseEnChargeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriseEnCharge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((priseEnCharge: HttpResponse<PriseEnCharge>) => {
          if (priseEnCharge.body) {
            return of(priseEnCharge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PriseEnCharge());
  }
}
