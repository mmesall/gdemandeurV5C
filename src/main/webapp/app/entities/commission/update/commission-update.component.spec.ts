import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommissionService } from '../service/commission.service';
import { ICommission, Commission } from '../commission.model';

import { CommissionUpdateComponent } from './commission-update.component';

describe('Commission Management Update Component', () => {
  let comp: CommissionUpdateComponent;
  let fixture: ComponentFixture<CommissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commissionService: CommissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommissionUpdateComponent],
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
      .overrideTemplate(CommissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commissionService = TestBed.inject(CommissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const commission: ICommission = { id: 456 };

      activatedRoute.data = of({ commission });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(commission));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Commission>>();
      const commission = { id: 123 };
      jest.spyOn(commissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commission }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(commissionService.update).toHaveBeenCalledWith(commission);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Commission>>();
      const commission = new Commission();
      jest.spyOn(commissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commission }));
      saveSubject.complete();

      // THEN
      expect(commissionService.create).toHaveBeenCalledWith(commission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Commission>>();
      const commission = { id: 123 };
      jest.spyOn(commissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commissionService.update).toHaveBeenCalledWith(commission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
