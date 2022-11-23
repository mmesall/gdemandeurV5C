import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PriseEnChargeService } from '../service/prise-en-charge.service';
import { IPriseEnCharge, PriseEnCharge } from '../prise-en-charge.model';
import { IBailleur } from 'app/entities/bailleur/bailleur.model';
import { BailleurService } from 'app/entities/bailleur/service/bailleur.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

import { PriseEnChargeUpdateComponent } from './prise-en-charge-update.component';

describe('PriseEnCharge Management Update Component', () => {
  let comp: PriseEnChargeUpdateComponent;
  let fixture: ComponentFixture<PriseEnChargeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let priseEnChargeService: PriseEnChargeService;
  let bailleurService: BailleurService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PriseEnChargeUpdateComponent],
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
      .overrideTemplate(PriseEnChargeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PriseEnChargeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    priseEnChargeService = TestBed.inject(PriseEnChargeService);
    bailleurService = TestBed.inject(BailleurService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Bailleur query and add missing value', () => {
      const priseEnCharge: IPriseEnCharge = { id: 456 };
      const bailleur: IBailleur = { id: 72209 };
      priseEnCharge.bailleur = bailleur;

      const bailleurCollection: IBailleur[] = [{ id: 19667 }];
      jest.spyOn(bailleurService, 'query').mockReturnValue(of(new HttpResponse({ body: bailleurCollection })));
      const additionalBailleurs = [bailleur];
      const expectedCollection: IBailleur[] = [...additionalBailleurs, ...bailleurCollection];
      jest.spyOn(bailleurService, 'addBailleurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      expect(bailleurService.query).toHaveBeenCalled();
      expect(bailleurService.addBailleurToCollectionIfMissing).toHaveBeenCalledWith(bailleurCollection, ...additionalBailleurs);
      expect(comp.bailleursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Formation query and add missing value', () => {
      const priseEnCharge: IPriseEnCharge = { id: 456 };
      const formation: IFormation = { id: 76478 };
      priseEnCharge.formation = formation;

      const formationCollection: IFormation[] = [{ id: 78283 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const additionalFormations = [formation];
      const expectedCollection: IFormation[] = [...additionalFormations, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, ...additionalFormations);
      expect(comp.formationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const priseEnCharge: IPriseEnCharge = { id: 456 };
      const bailleur: IBailleur = { id: 28198 };
      priseEnCharge.bailleur = bailleur;
      const formation: IFormation = { id: 43251 };
      priseEnCharge.formation = formation;

      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(priseEnCharge));
      expect(comp.bailleursSharedCollection).toContain(bailleur);
      expect(comp.formationsSharedCollection).toContain(formation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PriseEnCharge>>();
      const priseEnCharge = { id: 123 };
      jest.spyOn(priseEnChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: priseEnCharge }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(priseEnChargeService.update).toHaveBeenCalledWith(priseEnCharge);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PriseEnCharge>>();
      const priseEnCharge = new PriseEnCharge();
      jest.spyOn(priseEnChargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: priseEnCharge }));
      saveSubject.complete();

      // THEN
      expect(priseEnChargeService.create).toHaveBeenCalledWith(priseEnCharge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PriseEnCharge>>();
      const priseEnCharge = { id: 123 };
      jest.spyOn(priseEnChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ priseEnCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(priseEnChargeService.update).toHaveBeenCalledWith(priseEnCharge);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBailleurById', () => {
      it('Should return tracked Bailleur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBailleurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFormationById', () => {
      it('Should return tracked Formation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFormationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
