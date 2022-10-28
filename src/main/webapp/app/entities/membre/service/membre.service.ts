import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMembre, getMembreIdentifier } from '../membre.model';

export type EntityResponseType = HttpResponse<IMembre>;
export type EntityArrayResponseType = HttpResponse<IMembre[]>;

@Injectable({ providedIn: 'root' })
export class MembreService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/membres');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(membre: IMembre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membre);
    return this.http
      .post<IMembre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(membre: IMembre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membre);
    return this.http
      .put<IMembre>(`${this.resourceUrl}/${getMembreIdentifier(membre) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(membre: IMembre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membre);
    return this.http
      .patch<IMembre>(`${this.resourceUrl}/${getMembreIdentifier(membre) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMembre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMembreToCollectionIfMissing(membreCollection: IMembre[], ...membresToCheck: (IMembre | null | undefined)[]): IMembre[] {
    const membres: IMembre[] = membresToCheck.filter(isPresent);
    if (membres.length > 0) {
      const membreCollectionIdentifiers = membreCollection.map(membreItem => getMembreIdentifier(membreItem)!);
      const membresToAdd = membres.filter(membreItem => {
        const membreIdentifier = getMembreIdentifier(membreItem);
        if (membreIdentifier == null || membreCollectionIdentifiers.includes(membreIdentifier)) {
          return false;
        }
        membreCollectionIdentifiers.push(membreIdentifier);
        return true;
      });
      return [...membresToAdd, ...membreCollection];
    }
    return membreCollection;
  }

  protected convertDateFromClient(membre: IMembre): IMembre {
    return Object.assign({}, membre, {
      dateNaiss: membre.dateNaiss?.isValid() ? membre.dateNaiss.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((membre: IMembre) => {
        membre.dateNaiss = membre.dateNaiss ? dayjs(membre.dateNaiss) : undefined;
      });
    }
    return res;
  }
}
