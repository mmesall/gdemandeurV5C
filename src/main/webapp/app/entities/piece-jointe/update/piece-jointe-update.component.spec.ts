import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PieceJointeService } from '../service/piece-jointe.service';
import { IPieceJointe, PieceJointe } from '../piece-jointe.model';
import { IDemande } from 'app/entities/demande/demande.model';
import { DemandeService } from 'app/entities/demande/service/demande.service';

import { PieceJointeUpdateComponent } from './piece-jointe-update.component';

describe('PieceJointe Management Update Component', () => {
  let comp: PieceJointeUpdateComponent;
  let fixture: ComponentFixture<PieceJointeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pieceJointeService: PieceJointeService;
  let demandeService: DemandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PieceJointeUpdateComponent],
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
      .overrideTemplate(PieceJointeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PieceJointeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pieceJointeService = TestBed.inject(PieceJointeService);
    demandeService = TestBed.inject(DemandeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Demande query and add missing value', () => {
      const pieceJointe: IPieceJointe = { id: 456 };
      const demande: IDemande = { id: 74877 };
      pieceJointe.demande = demande;

      const demandeCollection: IDemande[] = [{ id: 61527 }];
      jest.spyOn(demandeService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeCollection })));
      const additionalDemandes = [demande];
      const expectedCollection: IDemande[] = [...additionalDemandes, ...demandeCollection];
      jest.spyOn(demandeService, 'addDemandeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      expect(demandeService.query).toHaveBeenCalled();
      expect(demandeService.addDemandeToCollectionIfMissing).toHaveBeenCalledWith(demandeCollection, ...additionalDemandes);
      expect(comp.demandesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pieceJointe: IPieceJointe = { id: 456 };
      const demande: IDemande = { id: 83746 };
      pieceJointe.demande = demande;

      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pieceJointe));
      expect(comp.demandesSharedCollection).toContain(demande);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PieceJointe>>();
      const pieceJointe = { id: 123 };
      jest.spyOn(pieceJointeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pieceJointe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pieceJointeService.update).toHaveBeenCalledWith(pieceJointe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PieceJointe>>();
      const pieceJointe = new PieceJointe();
      jest.spyOn(pieceJointeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pieceJointe }));
      saveSubject.complete();

      // THEN
      expect(pieceJointeService.create).toHaveBeenCalledWith(pieceJointe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PieceJointe>>();
      const pieceJointe = { id: 123 };
      jest.spyOn(pieceJointeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pieceJointeService.update).toHaveBeenCalledWith(pieceJointe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDemandeById', () => {
      it('Should return tracked Demande primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDemandeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
