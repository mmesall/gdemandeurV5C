import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { IProfessionnel, Professionnel } from '../professionnel.model';

import { ProfessionnelService } from './professionnel.service';

describe('Professionnel Service', () => {
  let service: ProfessionnelService;
  let httpMock: HttpTestingController;
  let elemDefault: IProfessionnel;
  let expectedResult: IProfessionnel | IProfessionnel[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProfessionnelService);
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

    it('should create a Professionnel', () => {
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

      service.create(new Professionnel()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Professionnel', () => {
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

    it('should partial update a Professionnel', () => {
      const patchObject = Object.assign(
        {
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          dateNaiss: currentDate.format(DATE_FORMAT),
          sexe: 'BBBBBB',
          telephone: 1,
          cni: 1,
        },
        new Professionnel()
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

    it('should return a list of Professionnel', () => {
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

    it('should delete a Professionnel', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProfessionnelToCollectionIfMissing', () => {
      it('should add a Professionnel to an empty array', () => {
        const professionnel: IProfessionnel = { id: 123 };
        expectedResult = service.addProfessionnelToCollectionIfMissing([], professionnel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(professionnel);
      });

      it('should not add a Professionnel to an array that contains it', () => {
        const professionnel: IProfessionnel = { id: 123 };
        const professionnelCollection: IProfessionnel[] = [
          {
            ...professionnel,
          },
          { id: 456 },
        ];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, professionnel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Professionnel to an array that doesn't contain it", () => {
        const professionnel: IProfessionnel = { id: 123 };
        const professionnelCollection: IProfessionnel[] = [{ id: 456 }];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, professionnel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(professionnel);
      });

      it('should add only unique Professionnel to an array', () => {
        const professionnelArray: IProfessionnel[] = [{ id: 123 }, { id: 456 }, { id: 27105 }];
        const professionnelCollection: IProfessionnel[] = [{ id: 123 }];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, ...professionnelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const professionnel: IProfessionnel = { id: 123 };
        const professionnel2: IProfessionnel = { id: 456 };
        expectedResult = service.addProfessionnelToCollectionIfMissing([], professionnel, professionnel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(professionnel);
        expect(expectedResult).toContain(professionnel2);
      });

      it('should accept null and undefined values', () => {
        const professionnel: IProfessionnel = { id: 123 };
        expectedResult = service.addProfessionnelToCollectionIfMissing([], null, professionnel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(professionnel);
      });

      it('should return initial array if no Professionnel is added', () => {
        const professionnelCollection: IProfessionnel[] = [{ id: 123 }];
        expectedResult = service.addProfessionnelToCollectionIfMissing(professionnelCollection, undefined, null);
        expect(expectedResult).toEqual(professionnelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
