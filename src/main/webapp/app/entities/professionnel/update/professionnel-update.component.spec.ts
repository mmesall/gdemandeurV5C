import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProfessionnelService } from '../service/professionnel.service';
import { IProfessionnel, Professionnel } from '../professionnel.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';

import { ProfessionnelUpdateComponent } from './professionnel-update.component';

describe('Professionnel Management Update Component', () => {
  let comp: ProfessionnelUpdateComponent;
  let fixture: ComponentFixture<ProfessionnelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let professionnelService: ProfessionnelService;
  let userService: UserService;
  let dossierService: DossierService;
  let localiteService: LocaliteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProfessionnelUpdateComponent],
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
      .overrideTemplate(ProfessionnelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfessionnelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    professionnelService = TestBed.inject(ProfessionnelService);
    userService = TestBed.inject(UserService);
    dossierService = TestBed.inject(DossierService);
    localiteService = TestBed.inject(LocaliteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const professionnel: IProfessionnel = { id: 456 };
      const user: IUser = { id: '0db2dbda-46e6-4193-ad43-2ac5e7dcc0ee' };
      professionnel.user = user;

      const userCollection: IUser[] = [{ id: 'ac6b981c-825c-4282-a388-ed4807e94262' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call dossier query and add missing value', () => {
      const professionnel: IProfessionnel = { id: 456 };
      const dossier: IDossier = { id: 35310 };
      professionnel.dossier = dossier;

      const dossierCollection: IDossier[] = [{ id: 86545 }];
      jest.spyOn(dossierService, 'query').mockReturnValue(of(new HttpResponse({ body: dossierCollection })));
      const expectedCollection: IDossier[] = [dossier, ...dossierCollection];
      jest.spyOn(dossierService, 'addDossierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      expect(dossierService.query).toHaveBeenCalled();
      expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, dossier);
      expect(comp.dossiersCollection).toEqual(expectedCollection);
    });

    it('Should call Localite query and add missing value', () => {
      const professionnel: IProfessionnel = { id: 456 };
      const localite: ILocalite = { id: 12974 };
      professionnel.localite = localite;

      const localiteCollection: ILocalite[] = [{ id: 47743 }];
      jest.spyOn(localiteService, 'query').mockReturnValue(of(new HttpResponse({ body: localiteCollection })));
      const additionalLocalites = [localite];
      const expectedCollection: ILocalite[] = [...additionalLocalites, ...localiteCollection];
      jest.spyOn(localiteService, 'addLocaliteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      expect(localiteService.query).toHaveBeenCalled();
      expect(localiteService.addLocaliteToCollectionIfMissing).toHaveBeenCalledWith(localiteCollection, ...additionalLocalites);
      expect(comp.localitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const professionnel: IProfessionnel = { id: 456 };
      const user: IUser = { id: '831efb46-03d4-40e3-b74f-db430979dc1c' };
      professionnel.user = user;
      const dossier: IDossier = { id: 38038 };
      professionnel.dossier = dossier;
      const localite: ILocalite = { id: 67415 };
      professionnel.localite = localite;

      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(professionnel));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.dossiersCollection).toContain(dossier);
      expect(comp.localitesSharedCollection).toContain(localite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Professionnel>>();
      const professionnel = { id: 123 };
      jest.spyOn(professionnelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: professionnel }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(professionnelService.update).toHaveBeenCalledWith(professionnel);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Professionnel>>();
      const professionnel = new Professionnel();
      jest.spyOn(professionnelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: professionnel }));
      saveSubject.complete();

      // THEN
      expect(professionnelService.create).toHaveBeenCalledWith(professionnel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Professionnel>>();
      const professionnel = { id: 123 };
      jest.spyOn(professionnelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professionnel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(professionnelService.update).toHaveBeenCalledWith(professionnel);
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
