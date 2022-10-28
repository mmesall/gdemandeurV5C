import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMembre, Membre } from '../membre.model';
import { MembreService } from '../service/membre.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';
import { ICommission } from 'app/entities/commission/commission.model';
import { CommissionService } from 'app/entities/commission/service/commission.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-membre-update',
  templateUrl: './membre-update.component.html',
})
export class MembreUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  usersSharedCollection: IUser[] = [];
  agentsCollection: IAgent[] = [];
  commissionsSharedCollection: ICommission[] = [];

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
    agent: [],
    commission: [],
  });

  constructor(
    protected membreService: MembreService,
    protected userService: UserService,
    protected agentService: AgentService,
    protected commissionService: CommissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membre }) => {
      this.updateForm(membre);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membre = this.createFromForm();
    if (membre.id !== undefined) {
      this.subscribeToSaveResponse(this.membreService.update(membre));
    } else {
      this.subscribeToSaveResponse(this.membreService.create(membre));
    }
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  trackAgentById(index: number, item: IAgent): number {
    return item.id!;
  }

  trackCommissionById(index: number, item: ICommission): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembre>>): void {
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

  protected updateForm(membre: IMembre): void {
    this.editForm.patchValue({
      id: membre.id,
      nom: membre.nom,
      prenom: membre.prenom,
      dateNaiss: membre.dateNaiss,
      lieuNaiss: membre.lieuNaiss,
      sexe: membre.sexe,
      telephone: membre.telephone,
      adressePhysique: membre.adressePhysique,
      email: membre.email,
      cni: membre.cni,
      matricule: membre.matricule,
      user: membre.user,
      agent: membre.agent,
      commission: membre.commission,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, membre.user);
    this.agentsCollection = this.agentService.addAgentToCollectionIfMissing(this.agentsCollection, membre.agent);
    this.commissionsSharedCollection = this.commissionService.addCommissionToCollectionIfMissing(
      this.commissionsSharedCollection,
      membre.commission
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.agentService
      .query({ filter: 'membre-is-null' })
      .pipe(map((res: HttpResponse<IAgent[]>) => res.body ?? []))
      .pipe(map((agents: IAgent[]) => this.agentService.addAgentToCollectionIfMissing(agents, this.editForm.get('agent')!.value)))
      .subscribe((agents: IAgent[]) => (this.agentsCollection = agents));

    this.commissionService
      .query()
      .pipe(map((res: HttpResponse<ICommission[]>) => res.body ?? []))
      .pipe(
        map((commissions: ICommission[]) =>
          this.commissionService.addCommissionToCollectionIfMissing(commissions, this.editForm.get('commission')!.value)
        )
      )
      .subscribe((commissions: ICommission[]) => (this.commissionsSharedCollection = commissions));
  }

  protected createFromForm(): IMembre {
    return {
      ...new Membre(),
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
      agent: this.editForm.get(['agent'])!.value,
      commission: this.editForm.get(['commission'])!.value,
    };
  }
}
