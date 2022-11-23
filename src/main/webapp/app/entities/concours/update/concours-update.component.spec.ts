import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConcoursService } from '../service/concours.service';
import { IConcours, Concours } from '../concours.model';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

import { ConcoursUpdateComponent } from './concours-update.component';

describe('Concours Management Update Component', () => {
  let comp: ConcoursUpdateComponent;
  let fixture: ComponentFixture<ConcoursUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let concoursService: ConcoursService;
  let formationService: FormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConcoursUpdateComponent],
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
      .overrideTemplate(ConcoursUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConcoursUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    concoursService = TestBed.inject(ConcoursService);
    formationService = TestBed.inject(FormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Formation query and add missing value', () => {
      const concours: IConcours = { id: 456 };
      const formation: IFormation = { id: 56278 };
      concours.formation = formation;

      const formationCollection: IFormation[] = [{ id: 78265 }];
      jest.spyOn(formationService, 'query').mockReturnValue(of(new HttpResponse({ body: formationCollection })));
      const additionalFormations = [formation];
      const expectedCollection: IFormation[] = [...additionalFormations, ...formationCollection];
      jest.spyOn(formationService, 'addFormationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      expect(formationService.query).toHaveBeenCalled();
      expect(formationService.addFormationToCollectionIfMissing).toHaveBeenCalledWith(formationCollection, ...additionalFormations);
      expect(comp.formationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const concours: IConcours = { id: 456 };
      const formation: IFormation = { id: 89522 };
      concours.formation = formation;

      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(concours));
      expect(comp.formationsSharedCollection).toContain(formation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Concours>>();
      const concours = { id: 123 };
      jest.spyOn(concoursService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concours }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(concoursService.update).toHaveBeenCalledWith(concours);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Concours>>();
      const concours = new Concours();
      jest.spyOn(concoursService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: concours }));
      saveSubject.complete();

      // THEN
      expect(concoursService.create).toHaveBeenCalledWith(concours);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Concours>>();
      const concours = { id: 123 };
      jest.spyOn(concoursService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ concours });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(concoursService.update).toHaveBeenCalledWith(concours);
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
  });
});
