import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ServicesService } from '../service/services.service';
import { IServices, Services } from '../services.model';
import { ICommission } from 'app/entities/commission/commission.model';
import { CommissionService } from 'app/entities/commission/service/commission.service';

import { ServicesUpdateComponent } from './services-update.component';

describe('Services Management Update Component', () => {
  let comp: ServicesUpdateComponent;
  let fixture: ComponentFixture<ServicesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicesService: ServicesService;
  let commissionService: CommissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ServicesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ServicesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicesService = TestBed.inject(ServicesService);
    commissionService = TestBed.inject(CommissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call commission query and add missing value', () => {
      const services: IServices = { id: 456 };
      const commission: ICommission = { id: 14271 };
      services.commission = commission;

      const commissionCollection: ICommission[] = [{ id: 50220 }];
      jest.spyOn(commissionService, 'query').mockReturnValue(of(new HttpResponse({ body: commissionCollection })));
      const expectedCollection: ICommission[] = [commission, ...commissionCollection];
      jest.spyOn(commissionService, 'addCommissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ services });
      comp.ngOnInit();

      expect(commissionService.query).toHaveBeenCalled();
      expect(commissionService.addCommissionToCollectionIfMissing).toHaveBeenCalledWith(commissionCollection, commission);
      expect(comp.commissionsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const services: IServices = { id: 456 };
      const commission: ICommission = { id: 99563 };
      services.commission = commission;

      activatedRoute.data = of({ services });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(services));
      expect(comp.commissionsCollection).toContain(commission);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Services>>();
      const services = { id: 123 };
      jest.spyOn(servicesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ services });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: services }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicesService.update).toHaveBeenCalledWith(services);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Services>>();
      const services = new Services();
      jest.spyOn(servicesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ services });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: services }));
      saveSubject.complete();

      // THEN
      expect(servicesService.create).toHaveBeenCalledWith(services);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Services>>();
      const services = { id: 123 };
      jest.spyOn(servicesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ services });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicesService.update).toHaveBeenCalledWith(services);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCommissionById', () => {
      it('Should return tracked Commission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
