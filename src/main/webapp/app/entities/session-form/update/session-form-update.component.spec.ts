import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SessionFormService } from '../service/session-form.service';
import { ISessionForm, SessionForm } from '../session-form.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';

import { SessionFormUpdateComponent } from './session-form-update.component';

describe('SessionForm Management Update Component', () => {
  let comp: SessionFormUpdateComponent;
  let fixture: ComponentFixture<SessionFormUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sessionFormService: SessionFormService;
  let formationService: FormationService;
  let etablissementService: EtablissementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SessionFormUpdateComponent],
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
      .overrideTemplate(SessionFormUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SessionFormUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sessionFormService = TestBed.inject(SessionFormService);
    formationService = TestBed.inject(FormationService);
    etablissementService = TestBed.inject(EtablissementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Formation query and add missing value', () => {
      const sessionForm: ISessionForm = { id: 456 };
      const formation: IFormation = { id: 98227 };
      sessionForm.formation = formation;

      const formationCollection: IFormation[] = [{ id: 35786 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const additionalFormations = [formation];
      const expectedCollection: IFormation[] = [...additionalFormations, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sessionForm });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, ...additionalFormations);
      expect(comp.formationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Etablissement query and add missing value', () => {
      const sessionForm: ISessionForm = { id: 456 };
      const etablissement: IEtablissement = { id: 88758 };
      sessionForm.etablissement = etablissement;

      const etablissementCollection: IEtablissement[] = [{ id: 19685 }];
      jest.spyOn(etablissementService, 'query').mockReturnValue(of(new HttpResponse({ body: etablissementCollection })));
      const additionalEtablissements = [etablissement];
      const expectedCollection: IEtablissement[] = [...additionalEtablissements, ...etablissementCollection];
      jest.spyOn(etablissementService, 'addEtablissementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sessionForm });
      comp.ngOnInit();

      expect(etablissementService.query).toHaveBeenCalled();
      expect(etablissementService.addEtablissementToCollectionIfMissing).toHaveBeenCalledWith(
        etablissementCollection,
        ...additionalEtablissements
      );
      expect(comp.etablissementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sessionForm: ISessionForm = { id: 456 };
      const formation: IFormation = { id: 23645 };
      sessionForm.formation = formation;
      const etablissement: IEtablissement = { id: 15585 };
      sessionForm.etablissement = etablissement;

      activatedRoute.data = of({ sessionForm });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sessionForm));
      expect(comp.formationsSharedCollection).toContain(formation);
      expect(comp.etablissementsSharedCollection).toContain(etablissement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SessionForm>>();
      const sessionForm = { id: 123 };
      jest.spyOn(sessionFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sessionForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sessionForm }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sessionFormService.update).toHaveBeenCalledWith(sessionForm);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SessionForm>>();
      const sessionForm = new SessionForm();
      jest.spyOn(sessionFormService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sessionForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sessionForm }));
      saveSubject.complete();

      // THEN
      expect(sessionFormService.create).toHaveBeenCalledWith(sessionForm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SessionForm>>();
      const sessionForm = { id: 123 };
      jest.spyOn(sessionFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sessionForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sessionFormService.update).toHaveBeenCalledWith(sessionForm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFormationById', () => {
      it('Should return tracked Formation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFormationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEtablissementById', () => {
      it('Should return tracked Etablissement primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtablissementById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
