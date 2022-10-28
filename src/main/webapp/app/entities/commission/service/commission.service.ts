import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommission, getCommissionIdentifier } from '../commission.model';

export type EntityResponseType = HttpResponse<ICommission>;
export type EntityArrayResponseType = HttpResponse<ICommission[]>;

@Injectable({ providedIn: 'root' })
export class CommissionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commissions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(commission: ICommission): Observable<EntityResponseType> {
    return this.http.post<ICommission>(this.resourceUrl, commission, { observe: 'response' });
  }

  update(commission: ICommission): Observable<EntityResponseType> {
    return this.http.put<ICommission>(`${this.resourceUrl}/${getCommissionIdentifier(commission) as number}`, commission, {
      observe: 'response',
    });
  }

  partialUpdate(commission: ICommission): Observable<EntityResponseType> {
    return this.http.patch<ICommission>(`${this.resourceUrl}/${getCommissionIdentifier(commission) as number}`, commission, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommission>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommission[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCommissionToCollectionIfMissing(
    commissionCollection: ICommission[],
    ...commissionsToCheck: (ICommission | null | undefined)[]
  ): ICommission[] {
    const commissions: ICommission[] = commissionsToCheck.filter(isPresent);
    if (commissions.length > 0) {
      const commissionCollectionIdentifiers = commissionCollection.map(commissionItem => getCommissionIdentifier(commissionItem)!);
      const commissionsToAdd = commissions.filter(commissionItem => {
        const commissionIdentifier = getCommissionIdentifier(commissionItem);
        if (commissionIdentifier == null || commissionCollectionIdentifiers.includes(commissionIdentifier)) {
          return false;
        }
        commissionCollectionIdentifiers.push(commissionIdentifier);
        return true;
      });
      return [...commissionsToAdd, ...commissionCollection];
    }
    return commissionCollection;
  }
}
