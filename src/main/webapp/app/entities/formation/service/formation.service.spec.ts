import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { TypeFormation } from 'app/entities/enumerations/type-formation.model';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { Secteur } from 'app/entities/enumerations/secteur.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';
import { IFormation, Formation } from '../formation.model';

import { FormationService } from './formation.service';

describe('Formation Service', () => {
  let service: FormationService;
  let httpMock: HttpTestingController;
  let elemDefault: IFormation;
  let expectedResult: IFormation | IFormation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nomFormation: 'AAAAAAA',
      typeFormation: TypeFormation.INITIALE,
      duree: 'AAAAAAA',
      admission: Admission.CONCOURS,
      diplomeRequis: DiplomeRequis.ATTESTATION,
      autreDiplome: 'AAAAAAA',
      secteur: Secteur.SANTE,
      autreSecteur: 'AAAAAAA',
      ficheFormationContentType: 'image/png',
      ficheFormation: 'AAAAAAA',
      programmes: 'AAAAAAA',
      nomConcours: 'AAAAAAA',
      dateOuverture: currentDate,
      dateCloture: currentDate,
      dateConcours: currentDate,
      libellePC: 'AAAAAAA',
      montantPriseEnCharge: 0,
      detailPC: 'AAAAAAA',
      nomDiplome: NomDiplome.CPS,
      nomDebouche: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateCloture: currentDate.format(DATE_FORMAT),
          dateConcours: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Formation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateCloture: currentDate.format(DATE_FORMAT),
          dateConcours: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateCloture: currentDate,
          dateConcours: currentDate,
        },
        returnedFromService
      );

      service.create(new Formation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Formation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomFormation: 'BBBBBB',
          typeFormation: 'BBBBBB',
          duree: 'BBBBBB',
          admission: 'BBBBBB',
          diplomeRequis: 'BBBBBB',
          autreDiplome: 'BBBBBB',
          secteur: 'BBBBBB',
          autreSecteur: 'BBBBBB',
          ficheFormation: 'BBBBBB',
          programmes: 'BBBBBB',
          nomConcours: 'BBBBBB',
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateCloture: currentDate.format(DATE_FORMAT),
          dateConcours: currentDate.format(DATE_FORMAT),
          libellePC: 'BBBBBB',
          montantPriseEnCharge: 1,
          detailPC: 'BBBBBB',
          nomDiplome: 'BBBBBB',
          nomDebouche: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateCloture: currentDate,
          dateConcours: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Formation', () => {
      const patchObject = Object.assign(
        {
          nomFormation: 'BBBBBB',
          typeFormation: 'BBBBBB',
          admission: 'BBBBBB',
          diplomeRequis: 'BBBBBB',
          ficheFormation: 'BBBBBB',
          programmes: 'BBBBBB',
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateCloture: currentDate.format(DATE_FORMAT),
          montantPriseEnCharge: 1,
          detailPC: 'BBBBBB',
          nomDiplome: 'BBBBBB',
        },
        new Formation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateCloture: currentDate,
          dateConcours: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Formation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomFormation: 'BBBBBB',
          typeFormation: 'BBBBBB',
          duree: 'BBBBBB',
          admission: 'BBBBBB',
          diplomeRequis: 'BBBBBB',
          autreDiplome: 'BBBBBB',
          secteur: 'BBBBBB',
          autreSecteur: 'BBBBBB',
          ficheFormation: 'BBBBBB',
          programmes: 'BBBBBB',
          nomConcours: 'BBBBBB',
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateCloture: currentDate.format(DATE_FORMAT),
          dateConcours: currentDate.format(DATE_FORMAT),
          libellePC: 'BBBBBB',
          montantPriseEnCharge: 1,
          detailPC: 'BBBBBB',
          nomDiplome: 'BBBBBB',
          nomDebouche: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateCloture: currentDate,
          dateConcours: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Formation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFormationToCollectionIfMissing', () => {
      it('should add a Formation to an empty array', () => {
        const formation: IFormation = { id: 123 };
        expectedResult = service.addFormationToCollectionIfMissing([], formation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formation);
      });

      it('should not add a Formation to an array that contains it', () => {
        const formation: IFormation = { id: 123 };
        const formationCollection: IFormation[] = [
          {
            ...formation,
          },
          { id: 456 },
        ];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, formation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Formation to an array that doesn't contain it", () => {
        const formation: IFormation = { id: 123 };
        const formationCollection: IFormation[] = [{ id: 456 }];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, formation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formation);
      });

      it('should add only unique Formation to an array', () => {
        const formationArray: IFormation[] = [{ id: 123 }, { id: 456 }, { id: 41914 }];
        const formationCollection: IFormation[] = [{ id: 123 }];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, ...formationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formation: IFormation = { id: 123 };
        const formation2: IFormation = { id: 456 };
        expectedResult = service.addFormationToCollectionIfMissing([], formation, formation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formation);
        expect(expectedResult).toContain(formation2);
      });

      it('should accept null and undefined values', () => {
        const formation: IFormation = { id: 123 };
        expectedResult = service.addFormationToCollectionIfMissing([], null, formation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formation);
      });

      it('should return initial array if no Formation is added', () => {
        const formationCollection: IFormation[] = [{ id: 123 }];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, undefined, null);
        expect(expectedResult).toEqual(formationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
