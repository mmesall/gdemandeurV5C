import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Profil } from 'app/entities/enumerations/profil.model';
import { IDemandeur, Demandeur } from '../demandeur.model';

import { DemandeurService } from './demandeur.service';

describe('Demandeur Service', () => {
  let service: DemandeurService;
  let httpMock: HttpTestingController;
  let elemDefault: IDemandeur;
  let expectedResult: IDemandeur | IDemandeur[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeurService);
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
      email: 'AAAAAAA',
      profil: Profil.ELEVE,
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

    it('should create a Demandeur', () => {
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

      service.create(new Demandeur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Demandeur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          dateNaiss: currentDate.format(DATE_FORMAT),
          lieuNaiss: 'BBBBBB',
          sexe: 'BBBBBB',
          telephone: 1,
          email: 'BBBBBB',
          profil: 'BBBBBB',
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

    it('should partial update a Demandeur', () => {
      const patchObject = Object.assign(
        {
          prenom: 'BBBBBB',
          lieuNaiss: 'BBBBBB',
        },
        new Demandeur()
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

    it('should return a list of Demandeur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          dateNaiss: currentDate.format(DATE_FORMAT),
          lieuNaiss: 'BBBBBB',
          sexe: 'BBBBBB',
          telephone: 1,
          email: 'BBBBBB',
          profil: 'BBBBBB',
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

    it('should delete a Demandeur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDemandeurToCollectionIfMissing', () => {
      it('should add a Demandeur to an empty array', () => {
        const demandeur: IDemandeur = { id: 123 };
        expectedResult = service.addDemandeurToCollectionIfMissing([], demandeur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeur);
      });

      it('should not add a Demandeur to an array that contains it', () => {
        const demandeur: IDemandeur = { id: 123 };
        const demandeurCollection: IDemandeur[] = [
          {
            ...demandeur,
          },
          { id: 456 },
        ];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, demandeur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Demandeur to an array that doesn't contain it", () => {
        const demandeur: IDemandeur = { id: 123 };
        const demandeurCollection: IDemandeur[] = [{ id: 456 }];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, demandeur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeur);
      });

      it('should add only unique Demandeur to an array', () => {
        const demandeurArray: IDemandeur[] = [{ id: 123 }, { id: 456 }, { id: 88397 }];
        const demandeurCollection: IDemandeur[] = [{ id: 123 }];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, ...demandeurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeur: IDemandeur = { id: 123 };
        const demandeur2: IDemandeur = { id: 456 };
        expectedResult = service.addDemandeurToCollectionIfMissing([], demandeur, demandeur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeur);
        expect(expectedResult).toContain(demandeur2);
      });

      it('should accept null and undefined values', () => {
        const demandeur: IDemandeur = { id: 123 };
        expectedResult = service.addDemandeurToCollectionIfMissing([], null, demandeur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeur);
      });

      it('should return initial array if no Demandeur is added', () => {
        const demandeurCollection: IDemandeur[] = [{ id: 123 }];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, undefined, null);
        expect(expectedResult).toEqual(demandeurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
