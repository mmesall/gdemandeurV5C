import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPriseEnCharge, PriseEnCharge } from '../prise-en-charge.model';

import { PriseEnChargeService } from './prise-en-charge.service';

describe('PriseEnCharge Service', () => {
  let service: PriseEnChargeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPriseEnCharge;
  let expectedResult: IPriseEnCharge | IPriseEnCharge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PriseEnChargeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      libelle: 'AAAAAAA',
      montant: 0,
      nbrePC: 0,
      details: 'AAAAAAA',
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

    it('should create a PriseEnCharge', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PriseEnCharge()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PriseEnCharge', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          libelle: 'BBBBBB',
          montant: 1,
          nbrePC: 1,
          details: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PriseEnCharge', () => {
      const patchObject = Object.assign(
        {
          libelle: 'BBBBBB',
          montant: 1,
          details: 'BBBBBB',
        },
        new PriseEnCharge()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PriseEnCharge', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          libelle: 'BBBBBB',
          montant: 1,
          nbrePC: 1,
          details: 'BBBBBB',
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

    it('should delete a PriseEnCharge', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPriseEnChargeToCollectionIfMissing', () => {
      it('should add a PriseEnCharge to an empty array', () => {
        const priseEnCharge: IPriseEnCharge = { id: 123 };
        expectedResult = service.addPriseEnChargeToCollectionIfMissing([], priseEnCharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(priseEnCharge);
      });

      it('should not add a PriseEnCharge to an array that contains it', () => {
        const priseEnCharge: IPriseEnCharge = { id: 123 };
        const priseEnChargeCollection: IPriseEnCharge[] = [
          {
            ...priseEnCharge,
          },
          { id: 456 },
        ];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, priseEnCharge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PriseEnCharge to an array that doesn't contain it", () => {
        const priseEnCharge: IPriseEnCharge = { id: 123 };
        const priseEnChargeCollection: IPriseEnCharge[] = [{ id: 456 }];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, priseEnCharge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(priseEnCharge);
      });

      it('should add only unique PriseEnCharge to an array', () => {
        const priseEnChargeArray: IPriseEnCharge[] = [{ id: 123 }, { id: 456 }, { id: 33668 }];
        const priseEnChargeCollection: IPriseEnCharge[] = [{ id: 123 }];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, ...priseEnChargeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const priseEnCharge: IPriseEnCharge = { id: 123 };
        const priseEnCharge2: IPriseEnCharge = { id: 456 };
        expectedResult = service.addPriseEnChargeToCollectionIfMissing([], priseEnCharge, priseEnCharge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(priseEnCharge);
        expect(expectedResult).toContain(priseEnCharge2);
      });

      it('should accept null and undefined values', () => {
        const priseEnCharge: IPriseEnCharge = { id: 123 };
        expectedResult = service.addPriseEnChargeToCollectionIfMissing([], null, priseEnCharge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(priseEnCharge);
      });

      it('should return initial array if no PriseEnCharge is added', () => {
        const priseEnChargeCollection: IPriseEnCharge[] = [{ id: 123 }];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, undefined, null);
        expect(expectedResult).toEqual(priseEnChargeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
