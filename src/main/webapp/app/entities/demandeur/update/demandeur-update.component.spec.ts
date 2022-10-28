import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeurService } from '../service/demandeur.service';
import { IDemandeur, Demandeur } from '../demandeur.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';

import { DemandeurUpdateComponent } from './demandeur-update.component';

describe('Demandeur Management Update Component', () => {
  let comp: DemandeurUpdateComponent;
  let fixture: ComponentFixture<DemandeurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeurService: DemandeurService;
  let dossierService: DossierService;
  let userService: UserService;
  let eleveService: EleveService;
  let etudiantService: EtudiantService;
  let professionnelService: ProfessionnelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeurUpdateComponent],
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
      .overrideTemplate(DemandeurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeurService = TestBed.inject(DemandeurService);
    dossierService = TestBed.inject(DossierService);
    userService = TestBed.inject(UserService);
    eleveService = TestBed.inject(EleveService);
    etudiantService = TestBed.inject(EtudiantService);
    professionnelService = TestBed.inject(ProfessionnelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call dossier query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const dossier: IDossier = { id: 1840 };
      demandeur.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 82742 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const expectedCollection: IDossier[] = [dossier, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, dossier);
      expect(comp.dossiersCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const user: IUser = { id: 'd1e740e4-3249-4cda-98be-a7a9393c9068' };
      demandeur.user = user;

      const userCollection: IUser[] = [{ id: '45555443-edfd-45de-aeb0-e0461b35cf19' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call eleve query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const eleve: IEleve = { id: 39915 };
      demandeur.eleve = eleve;

      const eleveCollection: IEleve[] = [{ id: 34140 }];
      jest.spyOn(eleveService, 'query').mockReturnValue(of(new HttpResponse({ body: eleveCollection })));
      const expectedCollection: IEleve[] = [eleve, ...eleveCollection];
      jest.spyOn(eleveService, 'addEleveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(eleveService.query).toHaveBeenCalled();
      expect(eleveService.addEleveToCollectionIfMissing).toHaveBeenCalledWith(eleveCollection, eleve);
      expect(comp.elevesCollection).toEqual(expectedCollection);
    });

    it('Should call etudiant query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const etudiant: IEtudiant = { id: 68746 };
      demandeur.etudiant = etudiant;

      const etudiantCollection: IEtudiant[] = [{ id: 64991 }];
      jest.spyOn(etudiantService, 'query').mockReturnValue(of(new HttpResponse({ body: etudiantCollection })));
      const expectedCollection: IEtudiant[] = [etudiant, ...etudiantCollection];
      jest.spyOn(etudiantService, 'addEtudiantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(etudiantService.query).toHaveBeenCalled();
      expect(etudiantService.addEtudiantToCollectionIfMissing).toHaveBeenCalledWith(etudiantCollection, etudiant);
      expect(comp.etudiantsCollection).toEqual(expectedCollection);
    });

    it('Should call professionnel query and add missing value', () => {
      const demandeur: IDemandeur = { id: 456 };
      const professionnel: IProfessionnel = { id: 39361 };
      demandeur.professionnel = professionnel;

      const professionnelCollection: IProfessionnel[] = [{ id: 14158 }];
      jest.spyOn(professionnelService, 'query').mockReturnValue(of(new HttpResponse({ body: professionnelCollection })));
      const expectedCollection: IProfessionnel[] = [professionnel, ...professionnelCollection];
      jest.spyOn(professionnelService, 'addProfessionnelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(professionnelService.query).toHaveBeenCalled();
      expect(professionnelService.addProfessionnelToCollectionIfMissing).toHaveBeenCalledWith(professionnelCollection, professionnel);
      expect(comp.professionnelsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const demandeur: IDemandeur = { id: 456 };
      const dossier: IDossier = { id: 97110 };
      demandeur.dossier = dossier;
      const user: IUser = { id: 'a55e873e-9055-4bcf-9193-e8cc84b73d96' };
      demandeur.user = user;
      const eleve: IEleve = { id: 6076 };
      demandeur.eleve = eleve;
      const etudiant: IEtudiant = { id: 23719 };
      demandeur.etudiant = etudiant;
      const professionnel: IProfessionnel = { id: 14727 };
      demandeur.professionnel = professionnel;

      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(demandeur));
      expect(comp.dossiersCollection).toContain(dossier);
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.elevesCollection).toContain(eleve);
      expect(comp.etudiantsCollection).toContain(etudiant);
      expect(comp.professionnelsCollection).toContain(professionnel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Demandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeurService.update).toHaveBeenCalledWith(demandeur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Demandeur>>();
      const demandeur = new Demandeur();
      jest.spyOn(demandeurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeur }));
      saveSubject.complete();

      // THEN
      expect(demandeurService.create).toHaveBeenCalledWith(demandeur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Demandeur>>();
      const demandeur = { id: 123 };
      jest.spyOn(demandeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeurService.update).toHaveBeenCalledWith(demandeur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDossierById', () => {
      it('Should return tracked Dossier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDossierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 'ABC' };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEleveById', () => {
      it('Should return tracked Eleve primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEleveById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEtudiantById', () => {
      it('Should return tracked Etudiant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEtudiantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProfessionnelById', () => {
      it('Should return tracked Professionnel primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProfessionnelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
