import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAgent, Agent } from '../agent.model';
import { AgentService } from '../service/agent.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IServices } from 'app/entities/services/services.model';
import { ServicesService } from 'app/entities/services/service/services.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-agent-update',
  templateUrl: './agent-update.component.html',
})
export class AgentUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  usersSharedCollection: IUser[] = [];
  servicesSharedCollection: IServices[] = [];
  localitesSharedCollection: ILocalite[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    dateNaiss: [],
    lieuNaiss: [],
    sexe: [],
    telephone: [],
    adressePhysique: [],
    email: [],
    cni: [],
    matricule: [],
    user: [],
    services: [],
    localite: [],
  });

  constructor(
    protected agentService: AgentService,
    protected userService: UserService,
    protected servicesService: ServicesService,
    protected localiteService: LocaliteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agent }) => {
      this.updateForm(agent);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agent = this.createFromForm();
    if (agent.id !== undefined) {
      this.subscribeToSaveResponse(this.agentService.update(agent));
    } else {
      this.subscribeToSaveResponse(this.agentService.create(agent));
    }
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  trackServicesById(index: number, item: IServices): number {
    return item.id!;
  }

  trackLocaliteById(index: number, item: ILocalite): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(agent: IAgent): void {
    this.editForm.patchValue({
      id: agent.id,
      nom: agent.nom,
      prenom: agent.prenom,
      dateNaiss: agent.dateNaiss,
      lieuNaiss: agent.lieuNaiss,
      sexe: agent.sexe,
      telephone: agent.telephone,
      adressePhysique: agent.adressePhysique,
      email: agent.email,
      cni: agent.cni,
      matricule: agent.matricule,
      user: agent.user,
      services: agent.services,
      localite: agent.localite,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, agent.user);
    this.servicesSharedCollection = this.servicesService.addServicesToCollectionIfMissing(this.servicesSharedCollection, agent.services);
    this.localitesSharedCollection = this.localiteService.addLocaliteToCollectionIfMissing(this.localitesSharedCollection, agent.localite);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.servicesService
      .query()
      .pipe(map((res: HttpResponse<IServices[]>) => res.body ?? []))
      .pipe(
        map((services: IServices[]) =>
          this.servicesService.addServicesToCollectionIfMissing(services, this.editForm.get('services')!.value)
        )
      )
      .subscribe((services: IServices[]) => (this.servicesSharedCollection = services));

    this.localiteService
      .query()
      .pipe(map((res: HttpResponse<ILocalite[]>) => res.body ?? []))
      .pipe(
        map((localites: ILocalite[]) =>
          this.localiteService.addLocaliteToCollectionIfMissing(localites, this.editForm.get('localite')!.value)
        )
      )
      .subscribe((localites: ILocalite[]) => (this.localitesSharedCollection = localites));
  }

  protected createFromForm(): IAgent {
    return {
      ...new Agent(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      dateNaiss: this.editForm.get(['dateNaiss'])!.value,
      lieuNaiss: this.editForm.get(['lieuNaiss'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      adressePhysique: this.editForm.get(['adressePhysique'])!.value,
      email: this.editForm.get(['email'])!.value,
      cni: this.editForm.get(['cni'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      user: this.editForm.get(['user'])!.value,
      services: this.editForm.get(['services'])!.value,
      localite: this.editForm.get(['localite'])!.value,
    };
  }
}
