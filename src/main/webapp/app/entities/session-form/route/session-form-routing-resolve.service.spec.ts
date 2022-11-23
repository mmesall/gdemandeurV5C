import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISessionForm, SessionForm } from '../session-form.model';
import { SessionFormService } from '../service/session-form.service';

import { SessionFormRoutingResolveService } from './session-form-routing-resolve.service';

describe('SessionForm routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SessionFormRoutingResolveService;
  let service: SessionFormService;
  let resultSessionForm: ISessionForm | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SessionFormRoutingResolveService);
    service = TestBed.inject(SessionFormService);
    resultSessionForm = undefined;
  });

  describe('resolve', () => {
    it('should return ISessionForm returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSessionForm = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSessionForm).toEqual({ id: 123 });
    });

    it('should return new ISessionForm if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSessionForm = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSessionForm).toEqual(new SessionForm());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SessionForm })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSessionForm = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSessionForm).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
