import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { DAKAR } from 'app/entities/enumerations/dakar.model';
import { DIOURBEL } from 'app/entities/enumerations/diourbel.model';
import { FATICK } from 'app/entities/enumerations/fatick.model';
import { KAFFRINE } from 'app/entities/enumerations/kaffrine.model';
import { KAOLACK } from 'app/entities/enumerations/kaolack.model';
import { KEDOUGOU } from 'app/entities/enumerations/kedougou.model';
import { KOLDA } from 'app/entities/enumerations/kolda.model';
import { LOUGA } from 'app/entities/enumerations/louga.model';
import { MATAM } from 'app/entities/enumerations/matam.model';
import { SAINTLOUIS } from 'app/entities/enumerations/saintlouis.model';
import { SEDHIOU } from 'app/entities/enumerations/sedhiou.model';
import { TAMBACOUNDA } from 'app/entities/enumerations/tambacounda.model';
import { THIES } from 'app/entities/enumerations/thies.model';
import { ZIGUINCHOR } from 'app/entities/enumerations/ziguinchor.model';
import { ILocalite, Localite } from '../localite.model';

import { LocaliteService } from './localite.service';

describe('Localite Service', () => {
  let service: LocaliteService;
  let httpMock: HttpTestingController;
  let elemDefault: ILocalite;
  let expectedResult: ILocalite | ILocalite[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LocaliteService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      region: NomRegion.DAKAR,
      autreRegion: 'AAAAAAA',
      departementDakar: DAKAR.DAKAR,
      departementDiourbel: DIOURBEL.BAMBAEY,
      departementFatick: FATICK.FATICK,
      departementKaffrine: KAFFRINE.BIRKILANE,
      departementKaolack: KAOLACK.GUINGUINEO,
      departementKedougou: KEDOUGOU.KEDOUGOU,
      departementKolda: KOLDA.KOLDA,
      departementLouga: LOUGA.KEBEMERE,
      departementMatam: MATAM.KANELKANEL,
      departementSaint: SAINTLOUIS.DAGANA,
      departementSedhiou: SEDHIOU.BOUNKILING,
      departementTambacounda: TAMBACOUNDA.BAKEL,
      departementThis: THIES.MBOUR,
      departementZiguinchor: ZIGUINCHOR.BIGNONA,
      autredepartementDakar: 'AAAAAAA',
      autredepartementDiourbel: 'AAAAAAA',
      autredepartementFatick: 'AAAAAAA',
      autredepartementKaffrine: 'AAAAAAA',
      autredepartementKaolack: 'AAAAAAA',
      autredepartementKedougou: 'AAAAAAA',
      autredepartementKolda: 'AAAAAAA',
      autredepartementLouga: 'AAAAAAA',
      autredepartementMatam: 'AAAAAAA',
      autredepartementSaint: 'AAAAAAA',
      autredepartementSedhiou: 'AAAAAAA',
      autredepartementTambacounda: 'AAAAAAA',
      autredepartementThis: 'AAAAAAA',
      autredepartementZiguinchor: 'AAAAAAA',
      commune: 'AAAAAAA',
      nomQuartier: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Localite', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Localite()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Localite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          region: 'BBBBBB',
          autreRegion: 'BBBBBB',
          departementDakar: 'BBBBBB',
          departementDiourbel: 'BBBBBB',
          departementFatick: 'BBBBBB',
          departementKaffrine: 'BBBBBB',
          departementKaolack: 'BBBBBB',
          departementKedougou: 'BBBBBB',
          departementKolda: 'BBBBBB',
          departementLouga: 'BBBBBB',
          departementMatam: 'BBBBBB',
          departementSaint: 'BBBBBB',
          departementSedhiou: 'BBBBBB',
          departementTambacounda: 'BBBBBB',
          departementThis: 'BBBBBB',
          departementZiguinchor: 'BBBBBB',
          autredepartementDakar: 'BBBBBB',
          autredepartementDiourbel: 'BBBBBB',
          autredepartementFatick: 'BBBBBB',
          autredepartementKaffrine: 'BBBBBB',
          autredepartementKaolack: 'BBBBBB',
          autredepartementKedougou: 'BBBBBB',
          autredepartementKolda: 'BBBBBB',
          autredepartementLouga: 'BBBBBB',
          autredepartementMatam: 'BBBBBB',
          autredepartementSaint: 'BBBBBB',
          autredepartementSedhiou: 'BBBBBB',
          autredepartementTambacounda: 'BBBBBB',
          autredepartementThis: 'BBBBBB',
          autredepartementZiguinchor: 'BBBBBB',
          commune: 'BBBBBB',
          nomQuartier: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Localite', () => {
      const patchObject = Object.assign(
        {
          region: 'BBBBBB',
          autreRegion: 'BBBBBB',
          departementDiourbel: 'BBBBBB',
          departementFatick: 'BBBBBB',
          departementKaffrine: 'BBBBBB',
          departementKaolack: 'BBBBBB',
          departementKedougou: 'BBBBBB',
          departementKolda: 'BBBBBB',
          departementThis: 'BBBBBB',
          departementZiguinchor: 'BBBBBB',
          autredepartementKaolack: 'BBBBBB',
          autredepartementKedougou: 'BBBBBB',
          autredepartementMatam: 'BBBBBB',
          autredepartementTambacounda: 'BBBBBB',
          autredepartementZiguinchor: 'BBBBBB',
          nomQuartier: 'BBBBBB',
        },
        new Localite()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Localite', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          region: 'BBBBBB',
          autreRegion: 'BBBBBB',
          departementDakar: 'BBBBBB',
          departementDiourbel: 'BBBBBB',
          departementFatick: 'BBBBBB',
          departementKaffrine: 'BBBBBB',
          departementKaolack: 'BBBBBB',
          departementKedougou: 'BBBBBB',
          departementKolda: 'BBBBBB',
          departementLouga: 'BBBBBB',
          departementMatam: 'BBBBBB',
          departementSaint: 'BBBBBB',
          departementSedhiou: 'BBBBBB',
          departementTambacounda: 'BBBBBB',
          departementThis: 'BBBBBB',
          departementZiguinchor: 'BBBBBB',
          autredepartementDakar: 'BBBBBB',
          autredepartementDiourbel: 'BBBBBB',
          autredepartementFatick: 'BBBBBB',
          autredepartementKaffrine: 'BBBBBB',
          autredepartementKaolack: 'BBBBBB',
          autredepartementKedougou: 'BBBBBB',
          autredepartementKolda: 'BBBBBB',
          autredepartementLouga: 'BBBBBB',
          autredepartementMatam: 'BBBBBB',
          autredepartementSaint: 'BBBBBB',
          autredepartementSedhiou: 'BBBBBB',
          autredepartementTambacounda: 'BBBBBB',
          autredepartementThis: 'BBBBBB',
          autredepartementZiguinchor: 'BBBBBB',
          commune: 'BBBBBB',
          nomQuartier: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Localite', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLocaliteToCollectionIfMissing', () => {
      it('should add a Localite to an empty array', () => {
        const localite: ILocalite = { id: 123 };
        expectedResult = service.addLocaliteToCollectionIfMissing([], localite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(localite);
      });

      it('should not add a Localite to an array that contains it', () => {
        const localite: ILocalite = { id: 123 };
        const localiteCollection: ILocalite[] = [
          {
            ...localite,
          },
          { id: 456 },
        ];
        expectedResult = service.addLocaliteToCollectionIfMissing(localiteCollection, localite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Localite to an array that doesn't contain it", () => {
        const localite: ILocalite = { id: 123 };
        const localiteCollection: ILocalite[] = [{ id: 456 }];
        expectedResult = service.addLocaliteToCollectionIfMissing(localiteCollection, localite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(localite);
      });

      it('should add only unique Localite to an array', () => {
        const localiteArray: ILocalite[] = [{ id: 123 }, { id: 456 }, { id: 11546 }];
        const localiteCollection: ILocalite[] = [{ id: 123 }];
        expectedResult = service.addLocaliteToCollectionIfMissing(localiteCollection, ...localiteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const localite: ILocalite = { id: 123 };
        const localite2: ILocalite = { id: 456 };
        expectedResult = service.addLocaliteToCollectionIfMissing([], localite, localite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(localite);
        expect(expectedResult).toContain(localite2);
      });

      it('should accept null and undefined values', () => {
        const localite: ILocalite = { id: 123 };
        expectedResult = service.addLocaliteToCollectionIfMissing([], null, localite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(localite);
      });

      it('should return initial array if no Localite is added', () => {
        const localiteCollection: ILocalite[] = [{ id: 123 }];
        expectedResult = service.addLocaliteToCollectionIfMissing(localiteCollection, undefined, null);
        expect(expectedResult).toEqual(localiteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
