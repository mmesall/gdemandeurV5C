import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPriseEnCharge, getPriseEnChargeIdentifier } from '../prise-en-charge.model';

export type EntityResponseType = HttpResponse<IPriseEnCharge>;
export type EntityArrayResponseType = HttpResponse<IPriseEnCharge[]>;

@Injectable({ providedIn: 'root' })
export class PriseEnChargeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prise-en-charges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(priseEnCharge: IPriseEnCharge): Observable<EntityResponseType> {
    return this.http.post<IPriseEnCharge>(this.resourceUrl, priseEnCharge, { observe: 'response' });
  }

  update(priseEnCharge: IPriseEnCharge): Observable<EntityResponseType> {
    return this.http.put<IPriseEnCharge>(`${this.resourceUrl}/${getPriseEnChargeIdentifier(priseEnCharge) as number}`, priseEnCharge, {
      observe: 'response',
    });
  }

  partialUpdate(priseEnCharge: IPriseEnCharge): Observable<EntityResponseType> {
    return this.http.patch<IPriseEnCharge>(`${this.resourceUrl}/${getPriseEnChargeIdentifier(priseEnCharge) as number}`, priseEnCharge, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPriseEnCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPriseEnCharge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPriseEnChargeToCollectionIfMissing(
    priseEnChargeCollection: IPriseEnCharge[],
    ...priseEnChargesToCheck: (IPriseEnCharge | null | undefined)[]
  ): IPriseEnCharge[] {
    const priseEnCharges: IPriseEnCharge[] = priseEnChargesToCheck.filter(isPresent);
    if (priseEnCharges.length > 0) {
      const priseEnChargeCollectionIdentifiers = priseEnChargeCollection.map(
        priseEnChargeItem => getPriseEnChargeIdentifier(priseEnChargeItem)!
      );
      const priseEnChargesToAdd = priseEnCharges.filter(priseEnChargeItem => {
        const priseEnChargeIdentifier = getPriseEnChargeIdentifier(priseEnChargeItem);
        if (priseEnChargeIdentifier == null || priseEnChargeCollectionIdentifiers.includes(priseEnChargeIdentifier)) {
          return false;
        }
        priseEnChargeCollectionIdentifiers.push(priseEnChargeIdentifier);
        return true;
      });
      return [...priseEnChargesToAdd, ...priseEnChargeCollection];
    }
    return priseEnChargeCollection;
  }
}
