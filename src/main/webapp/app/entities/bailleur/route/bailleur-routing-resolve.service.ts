import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBailleur, Bailleur } from '../bailleur.model';
import { BailleurService } from '../service/bailleur.service';

@Injectable({ providedIn: 'root' })
export class BailleurRoutingResolveService implements Resolve<IBailleur> {
  constructor(protected service: BailleurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBailleur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bailleur: HttpResponse<Bailleur>) => {
          if (bailleur.body) {
            return of(bailleur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bailleur());
  }
}
