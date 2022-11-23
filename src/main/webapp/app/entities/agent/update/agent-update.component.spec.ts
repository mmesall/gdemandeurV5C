import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AgentService } from '../service/agent.service';
import { IAgent, Agent } from '../agent.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IServices } from 'app/entities/services/services.model';
import { ServicesService } from 'app/entities/services/service/services.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';

import { AgentUpdateComponent } from './agent-update.component';

describe('Agent Management Update Component', () => {
  let comp: AgentUpdateComponent;
  let fixture: ComponentFixture<AgentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agentService: AgentService;
  let userService: UserService;
  let servicesService: ServicesService;
  let localiteService: LocaliteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AgentUpdateComponent],
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
      .overrideTemplate(AgentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agentService = TestBed.inject(AgentService);
    userService = TestBed.inject(UserService);
    servicesService = TestBed.inject(ServicesService);
    localiteService = TestBed.inject(LocaliteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const agent: IAgent = { id: 456 };
      const user: IUser = { id: 'a1e8c268-c950-41fc-83d1-a3cc8622acd3' };
      agent.user = user;

      const userCollection: IUser[] = [{ id: '593ec2b7-0871-46b7-a0ae-884d7d7ae395' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Services query and add missing value', () => {
      const agent: IAgent = { id: 456 };
      const services: IServices = { id: 91391 };
      agent.services = services;

      const servicesCollection: IServices[] = [{ id: 45172 }];
      jest.spyOn(servicesService, 'query').mockReturnValue(of(new HttpResponse({ body: servicesCollection })));
      const additionalServices = [services];
      const expectedCollection: IServices[] = [...additionalServices, ...servicesCollection];
      jest.spyOn(servicesService, 'addServicesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(servicesService.query).toHaveBeenCalled();
      expect(servicesService.addServicesToCollectionIfMissing).toHaveBeenCalledWith(servicesCollection, ...additionalServices);
      expect(comp.servicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Localite query and add missing value', () => {
      const agent: IAgent = { id: 456 };
      const localite: ILocalite = { id: 31989 };
      agent.localite = localite;

      const localiteCollection: ILocalite[] = [{ id: 38341 }];
      jest.spyOn(localiteService, 'query').mockReturnValue(of(new HttpResponse({ body: localiteCollection })));
      const additionalLocalites = [localite];
      const expectedCollection: ILocalite[] = [...additionalLocalites, ...localiteCollection];
      jest.spyOn(localiteService, 'addLocaliteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(localiteService.query).toHaveBeenCalled();
      expect(localiteService.addLocaliteToCollectionIfMissing).toHaveBeenCalledWith(localiteCollection, ...additionalLocalites);
      expect(comp.localitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agent: IAgent = { id: 456 };
      const user: IUser = { id: '3a67d926-4965-4445-8d06-0cff3f5778e2' };
      agent.user = user;
      const services: IServices = { id: 66923 };
      agent.services = services;
      const localite: ILocalite = { id: 43724 };
      agent.localite = localite;

      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(agent));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.servicesSharedCollection).toContain(services);
      expect(comp.localitesSharedCollection).toContain(localite);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Agent>>();
      const agent = { id: 123 };
      jest.spyOn(agentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agent }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(agentService.update).toHaveBeenCalledWith(agent);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Agent>>();
      const agent = new Agent();
      jest.spyOn(agentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agent }));
      saveSubject.complete();

      // THEN
      expect(agentService.create).toHaveBeenCalledWith(agent);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Agent>>();
      const agent = { id: 123 };
      jest.spyOn(agentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agentService.update).toHaveBeenCalledWith(agent);
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

    describe('trackServicesById', () => {
      it('Should return tracked Services primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackServicesById(0, entity);
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
