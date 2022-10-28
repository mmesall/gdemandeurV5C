import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypePiece } from 'app/entities/enumerations/type-piece.model';
import { IPieceJointe, PieceJointe } from '../piece-jointe.model';

import { PieceJointeService } from './piece-jointe.service';

describe('PieceJointe Service', () => {
  let service: PieceJointeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPieceJointe;
  let expectedResult: IPieceJointe | IPieceJointe[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PieceJointeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      typePiece: TypePiece.CV,
      nomPiece: 'AAAAAAA',
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

    it('should create a PieceJointe', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PieceJointe()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PieceJointe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typePiece: 'BBBBBB',
          nomPiece: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PieceJointe', () => {
      const patchObject = Object.assign({}, new PieceJointe());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PieceJointe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typePiece: 'BBBBBB',
          nomPiece: 'BBBBBB',
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

    it('should delete a PieceJointe', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPieceJointeToCollectionIfMissing', () => {
      it('should add a PieceJointe to an empty array', () => {
        const pieceJointe: IPieceJointe = { id: 123 };
        expectedResult = service.addPieceJointeToCollectionIfMissing([], pieceJointe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pieceJointe);
      });

      it('should not add a PieceJointe to an array that contains it', () => {
        const pieceJointe: IPieceJointe = { id: 123 };
        const pieceJointeCollection: IPieceJointe[] = [
          {
            ...pieceJointe,
          },
          { id: 456 },
        ];
        expectedResult = service.addPieceJointeToCollectionIfMissing(pieceJointeCollection, pieceJointe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PieceJointe to an array that doesn't contain it", () => {
        const pieceJointe: IPieceJointe = { id: 123 };
        const pieceJointeCollection: IPieceJointe[] = [{ id: 456 }];
        expectedResult = service.addPieceJointeToCollectionIfMissing(pieceJointeCollection, pieceJointe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pieceJointe);
      });

      it('should add only unique PieceJointe to an array', () => {
        const pieceJointeArray: IPieceJointe[] = [{ id: 123 }, { id: 456 }, { id: 41423 }];
        const pieceJointeCollection: IPieceJointe[] = [{ id: 123 }];
        expectedResult = service.addPieceJointeToCollectionIfMissing(pieceJointeCollection, ...pieceJointeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pieceJointe: IPieceJointe = { id: 123 };
        const pieceJointe2: IPieceJointe = { id: 456 };
        expectedResult = service.addPieceJointeToCollectionIfMissing([], pieceJointe, pieceJointe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pieceJointe);
        expect(expectedResult).toContain(pieceJointe2);
      });

      it('should accept null and undefined values', () => {
        const pieceJointe: IPieceJointe = { id: 123 };
        expectedResult = service.addPieceJointeToCollectionIfMissing([], null, pieceJointe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pieceJointe);
      });

      it('should return initial array if no PieceJointe is added', () => {
        const pieceJointeCollection: IPieceJointe[] = [{ id: 123 }];
        expectedResult = service.addPieceJointeToCollectionIfMissing(pieceJointeCollection, undefined, null);
        expect(expectedResult).toEqual(pieceJointeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
