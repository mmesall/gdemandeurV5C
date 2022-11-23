import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBailleur, Bailleur } from '../bailleur.model';

import { BailleurService } from './bailleur.service';

describe('Bailleur Service', () => {
  let service: BailleurService;
  let httpMock: HttpTestingController;
  let elemDefault: IBailleur;
  let expectedResult: IBailleur | IBailleur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BailleurService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nomProjet: 'AAAAAAA',
      budgetPrevu: 0,
      budgetDepense: 0,
      budgetRestant: 0,
      nbrePC: 0,
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

    it('should create a Bailleur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Bailleur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bailleur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomProjet: 'BBBBBB',
          budgetPrevu: 1,
          budgetDepense: 1,
          budgetRestant: 1,
          nbrePC: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bailleur', () => {
      const patchObject = Object.assign(
        {
          budgetPrevu: 1,
          budgetRestant: 1,
        },
        new Bailleur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bailleur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomProjet: 'BBBBBB',
          budgetPrevu: 1,
          budgetDepense: 1,
          budgetRestant: 1,
          nbrePC: 1,
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

    it('should delete a Bailleur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBailleurToCollectionIfMissing', () => {
      it('should add a Bailleur to an empty array', () => {
        const bailleur: IBailleur = { id: 123 };
        expectedResult = service.addBailleurToCollectionIfMissing([], bailleur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bailleur);
      });

      it('should not add a Bailleur to an array that contains it', () => {
        const bailleur: IBailleur = { id: 123 };
        const bailleurCollection: IBailleur[] = [
          {
            ...bailleur,
          },
          { id: 456 },
        ];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, bailleur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bailleur to an array that doesn't contain it", () => {
        const bailleur: IBailleur = { id: 123 };
        const bailleurCollection: IBailleur[] = [{ id: 456 }];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, bailleur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bailleur);
      });

      it('should add only unique Bailleur to an array', () => {
        const bailleurArray: IBailleur[] = [{ id: 123 }, { id: 456 }, { id: 61374 }];
        const bailleurCollection: IBailleur[] = [{ id: 123 }];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, ...bailleurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bailleur: IBailleur = { id: 123 };
        const bailleur2: IBailleur = { id: 456 };
        expectedResult = service.addBailleurToCollectionIfMissing([], bailleur, bailleur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bailleur);
        expect(expectedResult).toContain(bailleur2);
      });

      it('should accept null and undefined values', () => {
        const bailleur: IBailleur = { id: 123 };
        expectedResult = service.addBailleurToCollectionIfMissing([], null, bailleur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bailleur);
      });

      it('should return initial array if no Bailleur is added', () => {
        const bailleurCollection: IBailleur[] = [{ id: 123 }];
        expectedResult = service.addBailleurToCollectionIfMissing(bailleurCollection, undefined, null);
        expect(expectedResult).toEqual(bailleurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
