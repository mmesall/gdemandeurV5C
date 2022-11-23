import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBailleur, getBailleurIdentifier } from '../bailleur.model';

export type EntityResponseType = HttpResponse<IBailleur>;
export type EntityArrayResponseType = HttpResponse<IBailleur[]>;

@Injectable({ providedIn: 'root' })
export class BailleurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bailleurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bailleur: IBailleur): Observable<EntityResponseType> {
    return this.http.post<IBailleur>(this.resourceUrl, bailleur, { observe: 'response' });
  }

  update(bailleur: IBailleur): Observable<EntityResponseType> {
    return this.http.put<IBailleur>(`${this.resourceUrl}/${getBailleurIdentifier(bailleur) as number}`, bailleur, { observe: 'response' });
  }

  partialUpdate(bailleur: IBailleur): Observable<EntityResponseType> {
    return this.http.patch<IBailleur>(`${this.resourceUrl}/${getBailleurIdentifier(bailleur) as number}`, bailleur, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBailleur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBailleur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBailleurToCollectionIfMissing(bailleurCollection: IBailleur[], ...bailleursToCheck: (IBailleur | null | undefined)[]): IBailleur[] {
    const bailleurs: IBailleur[] = bailleursToCheck.filter(isPresent);
    if (bailleurs.length > 0) {
      const bailleurCollectionIdentifiers = bailleurCollection.map(bailleurItem => getBailleurIdentifier(bailleurItem)!);
      const bailleursToAdd = bailleurs.filter(bailleurItem => {
        const bailleurIdentifier = getBailleurIdentifier(bailleurItem);
        if (bailleurIdentifier == null || bailleurCollectionIdentifiers.includes(bailleurIdentifier)) {
          return false;
        }
        bailleurCollectionIdentifiers.push(bailleurIdentifier);
        return true;
      });
      return [...bailleursToAdd, ...bailleurCollection];
    }
    return bailleurCollection;
  }
}
