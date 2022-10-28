import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { IAgent, Agent } from '../agent.model';

import { AgentService } from './agent.service';

describe('Agent Service', () => {
  let service: AgentService;
  let httpMock: HttpTestingController;
  let elemDefault: IAgent;
  let expectedResult: IAgent | IAgent[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AgentService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
      prenom: 'AAAAAAA',
      dateNaiss: currentDate,
      lieuNaiss: 'AAAAAAA',
      sexe: Sexe.MASCULIN,
      telephone: 0,
      adressePhysique: 'AAAAAAA',
      email: 'AAAAAAA',
      cni: 0,
      matricule: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateNaiss: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Agent', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateNaiss: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaiss: currentDate,
        },
        returnedFromService
      );

      service.create(new Agent()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Agent', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          dateNaiss: currentDate.format(DATE_FORMAT),
          lieuNaiss: 'BBBBBB',
          sexe: 'BBBBBB',
          telephone: 1,
          adressePhysique: 'BBBBBB',
          email: 'BBBBBB',
          cni: 1,
          matricule: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaiss: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Agent', () => {
      const patchObject = Object.assign(
        {
          prenom: 'BBBBBB',
          dateNaiss: currentDate.format(DATE_FORMAT),
          sexe: 'BBBBBB',
          telephone: 1,
          adressePhysique: 'BBBBBB',
          email: 'BBBBBB',
        },
        new Agent()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateNaiss: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Agent', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          dateNaiss: currentDate.format(DATE_FORMAT),
          lieuNaiss: 'BBBBBB',
          sexe: 'BBBBBB',
          telephone: 1,
          adressePhysique: 'BBBBBB',
          email: 'BBBBBB',
          cni: 1,
          matricule: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateNaiss: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Agent', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAgentToCollectionIfMissing', () => {
      it('should add a Agent to an empty array', () => {
        const agent: IAgent = { id: 123 };
        expectedResult = service.addAgentToCollectionIfMissing([], agent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agent);
      });

      it('should not add a Agent to an array that contains it', () => {
        const agent: IAgent = { id: 123 };
        const agentCollection: IAgent[] = [
          {
            ...agent,
          },
          { id: 456 },
        ];
        expectedResult = service.addAgentToCollectionIfMissing(agentCollection, agent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Agent to an array that doesn't contain it", () => {
        const agent: IAgent = { id: 123 };
        const agentCollection: IAgent[] = [{ id: 456 }];
        expectedResult = service.addAgentToCollectionIfMissing(agentCollection, agent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agent);
      });

      it('should add only unique Agent to an array', () => {
        const agentArray: IAgent[] = [{ id: 123 }, { id: 456 }, { id: 42409 }];
        const agentCollection: IAgent[] = [{ id: 123 }];
        expectedResult = service.addAgentToCollectionIfMissing(agentCollection, ...agentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agent: IAgent = { id: 123 };
        const agent2: IAgent = { id: 456 };
        expectedResult = service.addAgentToCollectionIfMissing([], agent, agent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agent);
        expect(expectedResult).toContain(agent2);
      });

      it('should accept null and undefined values', () => {
        const agent: IAgent = { id: 123 };
        expectedResult = service.addAgentToCollectionIfMissing([], null, agent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agent);
      });

      it('should return initial array if no Agent is added', () => {
        const agentCollection: IAgent[] = [{ id: 123 }];
        expectedResult = service.addAgentToCollectionIfMissing(agentCollection, undefined, null);
        expect(expectedResult).toEqual(agentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
