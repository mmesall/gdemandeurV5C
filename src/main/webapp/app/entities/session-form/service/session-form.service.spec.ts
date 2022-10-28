import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISessionForm, SessionForm } from '../session-form.model';

import { SessionFormService } from './session-form.service';

describe('SessionForm Service', () => {
  let service: SessionFormService;
  let httpMock: HttpTestingController;
  let elemDefault: ISessionForm;
  let expectedResult: ISessionForm | ISessionForm[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SessionFormService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nomSession: 'AAAAAAA',
      annee: 'AAAAAAA',
      dateDebutSess: currentDate,
      dateFinSess: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateDebutSess: currentDate.format(DATE_FORMAT),
          dateFinSess: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SessionForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateDebutSess: currentDate.format(DATE_FORMAT),
          dateFinSess: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebutSess: currentDate,
          dateFinSess: currentDate,
        },
        returnedFromService
      );

      service.create(new SessionForm()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SessionForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomSession: 'BBBBBB',
          annee: 'BBBBBB',
          dateDebutSess: currentDate.format(DATE_FORMAT),
          dateFinSess: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebutSess: currentDate,
          dateFinSess: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SessionForm', () => {
      const patchObject = Object.assign({}, new SessionForm());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateDebutSess: currentDate,
          dateFinSess: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SessionForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomSession: 'BBBBBB',
          annee: 'BBBBBB',
          dateDebutSess: currentDate.format(DATE_FORMAT),
          dateFinSess: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDebutSess: currentDate,
          dateFinSess: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SessionForm', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSessionFormToCollectionIfMissing', () => {
      it('should add a SessionForm to an empty array', () => {
        const sessionForm: ISessionForm = { id: 123 };
        expectedResult = service.addSessionFormToCollectionIfMissing([], sessionForm);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sessionForm);
      });

      it('should not add a SessionForm to an array that contains it', () => {
        const sessionForm: ISessionForm = { id: 123 };
        const sessionFormCollection: ISessionForm[] = [
          {
            ...sessionForm,
          },
          { id: 456 },
        ];
        expectedResult = service.addSessionFormToCollectionIfMissing(sessionFormCollection, sessionForm);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SessionForm to an array that doesn't contain it", () => {
        const sessionForm: ISessionForm = { id: 123 };
        const sessionFormCollection: ISessionForm[] = [{ id: 456 }];
        expectedResult = service.addSessionFormToCollectionIfMissing(sessionFormCollection, sessionForm);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sessionForm);
      });

      it('should add only unique SessionForm to an array', () => {
        const sessionFormArray: ISessionForm[] = [{ id: 123 }, { id: 456 }, { id: 46110 }];
        const sessionFormCollection: ISessionForm[] = [{ id: 123 }];
        expectedResult = service.addSessionFormToCollectionIfMissing(sessionFormCollection, ...sessionFormArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sessionForm: ISessionForm = { id: 123 };
        const sessionForm2: ISessionForm = { id: 456 };
        expectedResult = service.addSessionFormToCollectionIfMissing([], sessionForm, sessionForm2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sessionForm);
        expect(expectedResult).toContain(sessionForm2);
      });

      it('should accept null and undefined values', () => {
        const sessionForm: ISessionForm = { id: 123 };
        expectedResult = service.addSessionFormToCollectionIfMissing([], null, sessionForm, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sessionForm);
      });

      it('should return initial array if no SessionForm is added', () => {
        const sessionFormCollection: ISessionForm[] = [{ id: 123 }];
        expectedResult = service.addSessionFormToCollectionIfMissing(sessionFormCollection, undefined, null);
        expect(expectedResult).toEqual(sessionFormCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
