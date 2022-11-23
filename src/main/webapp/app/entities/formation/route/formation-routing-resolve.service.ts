import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormation, Formation } from '../formation.model';
import { FormationService } from '../service/formation.service';

@Injectable({ providedIn: 'root' })
export class FormationRoutingResolveService implements Resolve<IFormation> {
  constructor(protected service: FormationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formation: HttpResponse<Formation>) => {
          if (formation.body) {
            return of(formation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Formation());
  }
}
