import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPieceJointe, getPieceJointeIdentifier } from '../piece-jointe.model';

export type EntityResponseType = HttpResponse<IPieceJointe>;
export type EntityArrayResponseType = HttpResponse<IPieceJointe[]>;

@Injectable({ providedIn: 'root' })
export class PieceJointeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/piece-jointes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pieceJointe: IPieceJointe): Observable<EntityResponseType> {
    return this.http.post<IPieceJointe>(this.resourceUrl, pieceJointe, { observe: 'response' });
  }

  update(pieceJointe: IPieceJointe): Observable<EntityResponseType> {
    return this.http.put<IPieceJointe>(`${this.resourceUrl}/${getPieceJointeIdentifier(pieceJointe) as number}`, pieceJointe, {
      observe: 'response',
    });
  }

  partialUpdate(pieceJointe: IPieceJointe): Observable<EntityResponseType> {
    return this.http.patch<IPieceJointe>(`${this.resourceUrl}/${getPieceJointeIdentifier(pieceJointe) as number}`, pieceJointe, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPieceJointe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPieceJointe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPieceJointeToCollectionIfMissing(
    pieceJointeCollection: IPieceJointe[],
    ...pieceJointesToCheck: (IPieceJointe | null | undefined)[]
  ): IPieceJointe[] {
    const pieceJointes: IPieceJointe[] = pieceJointesToCheck.filter(isPresent);
    if (pieceJointes.length > 0) {
      const pieceJointeCollectionIdentifiers = pieceJointeCollection.map(pieceJointeItem => getPieceJointeIdentifier(pieceJointeItem)!);
      const pieceJointesToAdd = pieceJointes.filter(pieceJointeItem => {
        const pieceJointeIdentifier = getPieceJointeIdentifier(pieceJointeItem);
        if (pieceJointeIdentifier == null || pieceJointeCollectionIdentifiers.includes(pieceJointeIdentifier)) {
          return false;
        }
        pieceJointeCollectionIdentifiers.push(pieceJointeIdentifier);
        return true;
      });
      return [...pieceJointesToAdd, ...pieceJointeCollection];
    }
    return pieceJointeCollection;
  }
}
