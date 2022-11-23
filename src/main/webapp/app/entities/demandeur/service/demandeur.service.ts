import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeur, getDemandeurIdentifier } from '../demandeur.model';

export type EntityResponseType = HttpResponse<IDemandeur>;
export type EntityArrayResponseType = HttpResponse<IDemandeur[]>;

@Injectable({ providedIn: 'root' })
export class DemandeurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demandeurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeur: IDemandeur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeur);
    return this.http
      .post<IDemandeur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demandeur: IDemandeur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeur);
    return this.http
      .put<IDemandeur>(`${this.resourceUrl}/${getDemandeurIdentifier(demandeur) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(demandeur: IDemandeur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeur);
    return this.http
      .patch<IDemandeur>(`${this.resourceUrl}/${getDemandeurIdentifier(demandeur) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemandeur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemandeur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandeurToCollectionIfMissing(
    demandeurCollection: IDemandeur[],
    ...demandeursToCheck: (IDemandeur | null | undefined)[]
  ): IDemandeur[] {
    const demandeurs: IDemandeur[] = demandeursToCheck.filter(isPresent);
    if (demandeurs.length > 0) {
      const demandeurCollectionIdentifiers = demandeurCollection.map(demandeurItem => getDemandeurIdentifier(demandeurItem)!);
      const demandeursToAdd = demandeurs.filter(demandeurItem => {
        const demandeurIdentifier = getDemandeurIdentifier(demandeurItem);
        if (demandeurIdentifier == null || demandeurCollectionIdentifiers.includes(demandeurIdentifier)) {
          return false;
        }
        demandeurCollectionIdentifiers.push(demandeurIdentifier);
        return true;
      });
      return [...demandeursToAdd, ...demandeurCollection];
    }
    return demandeurCollection;
  }

  protected convertDateFromClient(demandeur: IDemandeur): IDemandeur {
    return Object.assign({}, demandeur, {
      dateNaiss: demandeur.dateNaiss?.isValid() ? demandeur.dateNaiss.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaiss = res.body.dateNaiss ? dayjs(res.body.dateNaiss) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((demandeur: IDemandeur) => {
        demandeur.dateNaiss = demandeur.dateNaiss ? dayjs(demandeur.dateNaiss) : undefined;
      });
    }
    return res;
  }
}
