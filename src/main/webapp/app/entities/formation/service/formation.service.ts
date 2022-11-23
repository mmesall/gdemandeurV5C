import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormation, getFormationIdentifier } from '../formation.model';

export type EntityResponseType = HttpResponse<IFormation>;
export type EntityArrayResponseType = HttpResponse<IFormation[]>;

@Injectable({ providedIn: 'root' })
export class FormationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/formations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(formation: IFormation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formation);
    return this.http
      .post<IFormation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formation: IFormation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formation);
    return this.http
      .put<IFormation>(`${this.resourceUrl}/${getFormationIdentifier(formation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(formation: IFormation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formation);
    return this.http
      .patch<IFormation>(`${this.resourceUrl}/${getFormationIdentifier(formation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFormationToCollectionIfMissing(
    formationCollection: IFormation[],
    ...formationsToCheck: (IFormation | null | undefined)[]
  ): IFormation[] {
    const formations: IFormation[] = formationsToCheck.filter(isPresent);
    if (formations.length > 0) {
      const formationCollectionIdentifiers = formationCollection.map(formationItem => getFormationIdentifier(formationItem)!);
      const formationsToAdd = formations.filter(formationItem => {
        const formationIdentifier = getFormationIdentifier(formationItem);
        if (formationIdentifier == null || formationCollectionIdentifiers.includes(formationIdentifier)) {
          return false;
        }
        formationCollectionIdentifiers.push(formationIdentifier);
        return true;
      });
      return [...formationsToAdd, ...formationCollection];
    }
    return formationCollection;
  }

  protected convertDateFromClient(formation: IFormation): IFormation {
    return Object.assign({}, formation, {
      dateOuverture: formation.dateOuverture?.isValid() ? formation.dateOuverture.format(DATE_FORMAT) : undefined,
      dateCloture: formation.dateCloture?.isValid() ? formation.dateCloture.format(DATE_FORMAT) : undefined,
      dateConcours: formation.dateConcours?.isValid() ? formation.dateConcours.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOuverture = res.body.dateOuverture ? dayjs(res.body.dateOuverture) : undefined;
      res.body.dateCloture = res.body.dateCloture ? dayjs(res.body.dateCloture) : undefined;
      res.body.dateConcours = res.body.dateConcours ? dayjs(res.body.dateConcours) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((formation: IFormation) => {
        formation.dateOuverture = formation.dateOuverture ? dayjs(formation.dateOuverture) : undefined;
        formation.dateCloture = formation.dateCloture ? dayjs(formation.dateCloture) : undefined;
        formation.dateConcours = formation.dateConcours ? dayjs(formation.dateConcours) : undefined;
      });
    }
    return res;
  }
}
