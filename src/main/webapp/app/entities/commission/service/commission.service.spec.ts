import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICommission, Commission } from '../commission.model';

import { CommissionService } from './commission.service';

describe('Commission Service', () => {
  let service: CommissionService;
  let httpMock: HttpTestingController;
  let elemDefault: ICommission;
  let expectedResult: ICommission | ICommission[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommissionService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nomCommission: 'AAAAAAA',
      mission: 'AAAAAAA',
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

    it('should create a Commission', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Commission()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Commission', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomCommission: 'BBBBBB',
          mission: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Commission', () => {
      const patchObject = Object.assign(
        {
          nomCommission: 'BBBBBB',
        },
        new Commission()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Commission', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomCommission: 'BBBBBB',
          mission: 'BBBBBB',
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

    it('should delete a Commission', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCommissionToCollectionIfMissing', () => {
      it('should add a Commission to an empty array', () => {
        const commission: ICommission = { id: 123 };
        expectedResult = service.addCommissionToCollectionIfMissing([], commission);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commission);
      });

      it('should not add a Commission to an array that contains it', () => {
        const commission: ICommission = { id: 123 };
        const commissionCollection: ICommission[] = [
          {
            ...commission,
          },
          { id: 456 },
        ];
        expectedResult = service.addCommissionToCollectionIfMissing(commissionCollection, commission);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Commission to an array that doesn't contain it", () => {
        const commission: ICommission = { id: 123 };
        const commissionCollection: ICommission[] = [{ id: 456 }];
        expectedResult = service.addCommissionToCollectionIfMissing(commissionCollection, commission);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commission);
      });

      it('should add only unique Commission to an array', () => {
        const commissionArray: ICommission[] = [{ id: 123 }, { id: 456 }, { id: 41229 }];
        const commissionCollection: ICommission[] = [{ id: 123 }];
        expectedResult = service.addCommissionToCollectionIfMissing(commissionCollection, ...commissionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commission: ICommission = { id: 123 };
        const commission2: ICommission = { id: 456 };
        expectedResult = service.addCommissionToCollectionIfMissing([], commission, commission2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commission);
        expect(expectedResult).toContain(commission2);
      });

      it('should accept null and undefined values', () => {
        const commission: ICommission = { id: 123 };
        expectedResult = service.addCommissionToCollectionIfMissing([], null, commission, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commission);
      });

      it('should return initial array if no Commission is added', () => {
        const commissionCollection: ICommission[] = [{ id: 123 }];
        expectedResult = service.addCommissionToCollectionIfMissing(commissionCollection, undefined, null);
        expect(expectedResult).toEqual(commissionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
