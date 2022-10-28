import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgent, getAgentIdentifier } from '../agent.model';

export type EntityResponseType = HttpResponse<IAgent>;
export type EntityArrayResponseType = HttpResponse<IAgent[]>;

@Injectable({ providedIn: 'root' })
export class AgentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(agent: IAgent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agent);
    return this.http
      .post<IAgent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agent: IAgent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agent);
    return this.http
      .put<IAgent>(`${this.resourceUrl}/${getAgentIdentifier(agent) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(agent: IAgent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agent);
    return this.http
      .patch<IAgent>(`${this.resourceUrl}/${getAgentIdentifier(agent) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAgentToCollectionIfMissing(agentCollection: IAgent[], ...agentsToCheck: (IAgent | null | undefined)[]): IAgent[] {
    const agents: IAgent[] = agentsToCheck.filter(isPresent);
    if (agents.length > 0) {
      const agentCollectionIdentifiers = agentCollection.map(agentItem => getAgentIdentifier(agentItem)!);
      const agentsToAdd = agents.filter(agentItem => {
        const agentIdentifier = getAgentIdentifier(agentItem);
        if (agentIdentifier == null || agentCollectionIdentifiers.includes(agentIdentifier)) {
          return false;
        }
        agentCollectionIdentifiers.push(agentIdentifier);
        return true;
      });
      return [...agentsToAdd, ...agentCollection];
    }
    return agentCollection;
  }

  protected convertDateFromClient(agent: IAgent): IAgent {
    return Object.assign({}, agent, {
      dateNaiss: agent.dateNaiss?.isValid() ? agent.dateNaiss.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((agent: IAgent) => {
        agent.dateNaiss = agent.dateNaiss ? dayjs(agent.dateNaiss) : undefined;
      });
    }
    return res;
  }
}
