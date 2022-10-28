import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MembreService } from '../service/membre.service';
import { IMembre, Membre } from '../membre.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';
import { ICommission } from 'app/entities/commission/commission.model';
import { CommissionService } from 'app/entities/commission/service/commission.service';

import { MembreUpdateComponent } from './membre-update.component';

describe('Membre Management Update Component', () => {
  let comp: MembreUpdateComponent;
  let fixture: ComponentFixture<MembreUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let membreService: MembreService;
  let userService: UserService;
  let agentService: AgentService;
  let commissionService: CommissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MembreUpdateComponent],
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
      .overrideTemplate(MembreUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembreUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    membreService = TestBed.inject(MembreService);
    userService = TestBed.inject(UserService);
    agentService = TestBed.inject(AgentService);
    commissionService = TestBed.inject(CommissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const membre: IMembre = { id: 456 };
      const user: IUser = { id: 'b0651ddc-a68d-45a8-b694-750afe198944' };
      membre.user = user;

      const userCollection: IUser[] = [{ id: '56044a1d-52c0-476c-a72e-967258d9d390' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call agent query and add missing value', () => {
      const membre: IMembre = { id: 456 };
      const agent: IAgent = { id: 31324 };
      membre.agent = agent;

      const agentCollection: IAgent[] = [{ id: 98315 }];
      jest.spyOn(agentService, 'query').mockReturnValue(of(new HttpResponse({ body: agentCollection })));
      const expectedCollection: IAgent[] = [agent, ...agentCollection];
      jest.spyOn(agentService, 'addAgentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      expect(agentService.query).toHaveBeenCalled();
      expect(agentService.addAgentToCollectionIfMissing).toHaveBeenCalledWith(agentCollection, agent);
      expect(comp.agentsCollection).toEqual(expectedCollection);
    });

    it('Should call Commission query and add missing value', () => {
      const membre: IMembre = { id: 456 };
      const commission: ICommission = { id: 54430 };
      membre.commission = commission;

      const commissionCollection: ICommission[] = [{ id: 51826 }];
      jest.spyOn(commissionService, 'query').mockReturnValue(of(new HttpResponse({ body: commissionCollection })));
      const additionalCommissions = [commission];
      const expectedCollection: ICommission[] = [...additionalCommissions, ...commissionCollection];
      jest.spyOn(commissionService, 'addCommissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      expect(commissionService.query).toHaveBeenCalled();
      expect(commissionService.addCommissionToCollectionIfMissing).toHaveBeenCalledWith(commissionCollection, ...additionalCommissions);
      expect(comp.commissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const membre: IMembre = { id: 456 };
      const user: IUser = { id: 'eec84e2a-8c69-4f30-884d-32cc6ede30c4' };
      membre.user = user;
      const agent: IAgent = { id: 39617 };
      membre.agent = agent;
      const commission: ICommission = { id: 7383 };
      membre.commission = commission;

      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(membre));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.agentsCollection).toContain(agent);
      expect(comp.commissionsSharedCollection).toContain(commission);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Membre>>();
      const membre = { id: 123 };
      jest.spyOn(membreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membre }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(membreService.update).toHaveBeenCalledWith(membre);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Membre>>();
      const membre = new Membre();
      jest.spyOn(membreService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membre }));
      saveSubject.complete();

      // THEN
      expect(membreService.create).toHaveBeenCalledWith(membre);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Membre>>();
      const membre = { id: 123 };
      jest.spyOn(membreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(membreService.update).toHaveBeenCalledWith(membre);
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

    describe('trackAgentById', () => {
      it('Should return tracked Agent primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAgentById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCommissionById', () => {
      it('Should return tracked Commission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
