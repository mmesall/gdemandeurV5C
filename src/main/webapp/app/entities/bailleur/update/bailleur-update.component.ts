import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBailleur, Bailleur } from '../bailleur.model';
import { BailleurService } from '../service/bailleur.service';
import { IAgent } from 'app/entities/agent/agent.model';
import { AgentService } from 'app/entities/agent/service/agent.service';

@Component({
  selector: 'jhi-bailleur-update',
  templateUrl: './bailleur-update.component.html',
})
export class BailleurUpdateComponent implements OnInit {
  isSaving = false;

  agentsCollection: IAgent[] = [];

  editForm = this.fb.group({
    id: [],
    nomProjet: [],
    budgetPrevu: [],
    budgetDepense: [],
    budgetRestant: [],
    nbrePC: [],
    agent: [],
  });

  constructor(
    protected bailleurService: BailleurService,
    protected agentService: AgentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bailleur }) => {
      this.updateForm(bailleur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bailleur = this.createFromForm();
    if (bailleur.id !== undefined) {
      this.subscribeToSaveResponse(this.bailleurService.update(bailleur));
    } else {
      this.subscribeToSaveResponse(this.bailleurService.create(bailleur));
    }
  }

  trackAgentById(index: number, item: IAgent): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBailleur>>): void {
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

  protected updateForm(bailleur: IBailleur): void {
    this.editForm.patchValue({
      id: bailleur.id,
      nomProjet: bailleur.nomProjet,
      budgetPrevu: bailleur.budgetPrevu,
      budgetDepense: bailleur.budgetDepense,
      budgetRestant: bailleur.budgetRestant,
      nbrePC: bailleur.nbrePC,
      agent: bailleur.agent,
    });

    this.agentsCollection = this.agentService.addAgentToCollectionIfMissing(this.agentsCollection, bailleur.agent);
  }

  protected loadRelationshipsOptions(): void {
    this.agentService
      .query({ filter: 'bailleur-is-null' })
      .pipe(map((res: HttpResponse<IAgent[]>) => res.body ?? []))
      .pipe(map((agents: IAgent[]) => this.agentService.addAgentToCollectionIfMissing(agents, this.editForm.get('agent')!.value)))
      .subscribe((agents: IAgent[]) => (this.agentsCollection = agents));
  }

  protected createFromForm(): IBailleur {
    return {
      ...new Bailleur(),
      id: this.editForm.get(['id'])!.value,
      nomProjet: this.editForm.get(['nomProjet'])!.value,
      budgetPrevu: this.editForm.get(['budgetPrevu'])!.value,
      budgetDepense: this.editForm.get(['budgetDepense'])!.value,
      budgetRestant: this.editForm.get(['budgetRestant'])!.value,
      nbrePC: this.editForm.get(['nbrePC'])!.value,
      agent: this.editForm.get(['agent'])!.value,
    };
  }
}
