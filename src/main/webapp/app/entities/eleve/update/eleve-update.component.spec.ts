import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EleveService } from '../service/eleve.service';
import { IEleve, Eleve } from '../eleve.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';

import { EleveUpdateComponent } from './eleve-update.component';

describe('Eleve Management Update Component', () => {
  let comp: EleveUpdateComponent;
  let fixture: ComponentFixture<EleveUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eleveService: EleveService;
  let userService: UserService;
  let dossierService: DossierService;
  let localiteService: LocaliteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EleveUpdateComponent],
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
      .overrideTemplate(EleveUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EleveUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eleveService = TestBed.inject(EleveService);
    userService = TestBed.inject(UserService);
    dossierService = TestBed.inject(DossierService);
    localiteService = TestBed.inject(LocaliteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const user: IUser = { id: '8c83be82-4271-45f2-b929-424131401370' };
      eleve.user = user;

      const userCollection: IUser[] = [{ id: '800d5de9-8f76-4f84-a046-06aba3513a3f' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call dossier query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const dossier: IDossier = { id: 18481 };
      eleve.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 71053 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const expectedCollection: IDossier[] = [dossier, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, dossier);
      expect(comp.dossiersCollection).toEqual(expectedCollection);
    });

    it('Should call Localite query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const localite: ILocalite = { id: 50674 };
      eleve.localite = localite;

      const localiteCollection: ILocalite[] = [{ id: 68430 }];
      jest.spyOn(localiteService, 'query').mockReturnValue(of(new HttpResponse({ body: localiteCollection })));
      const additionalLocalites = [localite];
      const expectedCollection: ILocalite[] = [...additionalLocalites, ...localiteCollection];
      jest.spyOn(localiteService, 'addLocaliteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(localiteService.query).toHaveBeenCalled();
      expect(localiteService.addLocaliteToCollectionIfMissing).toHaveBeenCalledWith(localiteCollection, ...additionalLocalites);
      expect(comp.localitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eleve: IEleve = { id: 456 };
      const user: IUser = { id: 'e126e9a0-5b1d-4a46-b4ab-26b60d893740' };
      eleve.user = user;
      const dossier: IDossier = { id: 1972 };
      eleve.dossier = dossier;
      const localite: ILocalite = { id: 77645 };
      eleve.localite = localite;

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(eleve));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.dossiersCollection).toContain(dossier);
      expect(comp.localitesSharedCollection).toContain(localite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eleve }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(eleveService.update).toHaveBeenCalledWith(eleve);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eleve>>();
      const eleve = new Eleve();
      jest.spyOn(eleveService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eleve }));
      saveSubject.complete();

      // THEN
      expect(eleveService.create).toHaveBeenCalledWith(eleve);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Eleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eleveService.update).toHaveBeenCalledWith(eleve);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 'ABC' };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDossierById', () => {
      it('Should return tracked Dossier primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDossierById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLocaliteById', () => {
      it('Should return tracked Localite primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLocaliteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
