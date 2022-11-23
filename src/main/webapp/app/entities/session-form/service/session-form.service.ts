import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISessionForm, getSessionFormIdentifier } from '../session-form.model';

export type EntityResponseType = HttpResponse<ISessionForm>;
export type EntityArrayResponseType = HttpResponse<ISessionForm[]>;

@Injectable({ providedIn: 'root' })
export class SessionFormService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/session-forms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sessionForm: ISessionForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sessionForm);
    return this.http
      .post<ISessionForm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sessionForm: ISessionForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sessionForm);
    return this.http
      .put<ISessionForm>(`${this.resourceUrl}/${getSessionFormIdentifier(sessionForm) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sessionForm: ISessionForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sessionForm);
    return this.http
      .patch<ISessionForm>(`${this.resourceUrl}/${getSessionFormIdentifier(sessionForm) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISessionForm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISessionForm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSessionFormToCollectionIfMissing(
    sessionFormCollection: ISessionForm[],
    ...sessionFormsToCheck: (ISessionForm | null | undefined)[]
  ): ISessionForm[] {
    const sessionForms: ISessionForm[] = sessionFormsToCheck.filter(isPresent);
    if (sessionForms.length > 0) {
      const sessionFormCollectionIdentifiers = sessionFormCollection.map(sessionFormItem => getSessionFormIdentifier(sessionFormItem)!);
      const sessionFormsToAdd = sessionForms.filter(sessionFormItem => {
        const sessionFormIdentifier = getSessionFormIdentifier(sessionFormItem);
        if (sessionFormIdentifier == null || sessionFormCollectionIdentifiers.includes(sessionFormIdentifier)) {
          return false;
        }
        sessionFormCollectionIdentifiers.push(sessionFormIdentifier);
        return true;
      });
      return [...sessionFormsToAdd, ...sessionFormCollection];
    }
    return sessionFormCollection;
  }

  protected convertDateFromClient(sessionForm: ISessionForm): ISessionForm {
    return Object.assign({}, sessionForm, {
      dateDebutSess: sessionForm.dateDebutSess?.isValid() ? sessionForm.dateDebutSess.format(DATE_FORMAT) : undefined,
      dateFinSess: sessionForm.dateFinSess?.isValid() ? sessionForm.dateFinSess.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebutSess = res.body.dateDebutSess ? dayjs(res.body.dateDebutSess) : undefined;
      res.body.dateFinSess = res.body.dateFinSess ? dayjs(res.body.dateFinSess) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sessionForm: ISessionForm) => {
        sessionForm.dateDebutSess = sessionForm.dateDebutSess ? dayjs(sessionForm.dateDebutSess) : undefined;
        sessionForm.dateFinSess = sessionForm.dateFinSess ? dayjs(sessionForm.dateFinSess) : undefined;
      });
    }
    return res;
  }
}
