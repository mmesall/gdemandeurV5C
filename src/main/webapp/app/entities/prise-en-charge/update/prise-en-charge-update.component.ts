import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPriseEnCharge, PriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeService } from '../service/prise-en-charge.service';
import { IBailleur } from 'app/entities/bailleur/bailleur.model';
import { BailleurService } from 'app/entities/bailleur/service/bailleur.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

@Component({
  selector: 'jhi-prise-en-charge-update',
  templateUrl: './prise-en-charge-update.component.html',
})
export class PriseEnChargeUpdateComponent implements OnInit {
  isSaving = false;

  bailleursSharedCollection: IBailleur[] = [];
  formationsSharedCollection: IFormation[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [],
    montant: [],
    nbrePC: [],
    details: [],
    bailleur: [],
    formation: [],
  });

  constructor(
    protected priseEnChargeService: PriseEnChargeService,
    protected bailleurService: BailleurService,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priseEnCharge }) => {
      this.updateForm(priseEnCharge);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priseEnCharge = this.createFromForm();
    if (priseEnCharge.id !== undefined) {
      this.subscribeToSaveResponse(this.priseEnChargeService.update(priseEnCharge));
    } else {
      this.subscribeToSaveResponse(this.priseEnChargeService.create(priseEnCharge));
    }
  }

  trackBailleurById(index: number, item: IBailleur): number {
    return item.id!;
  }

  trackFormationById(index: number, item: IFormation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriseEnCharge>>): void {
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

  protected updateForm(priseEnCharge: IPriseEnCharge): void {
    this.editForm.patchValue({
      id: priseEnCharge.id,
      libelle: priseEnCharge.libelle,
      montant: priseEnCharge.montant,
      nbrePC: priseEnCharge.nbrePC,
      details: priseEnCharge.details,
      bailleur: priseEnCharge.bailleur,
      formation: priseEnCharge.formation,
    });

    this.bailleursSharedCollection = this.bailleurService.addBailleurToCollectionIfMissing(
      this.bailleursSharedCollection,
      priseEnCharge.bailleur
    );
    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsSharedCollection,
      priseEnCharge.formation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bailleurService
      .query()
      .pipe(map((res: HttpResponse<IBailleur[]>) => res.body ?? []))
      .pipe(
        map((bailleurs: IBailleur[]) =>
          this.bailleurService.addBailleurToCollectionIfMissing(bailleurs, this.editForm.get('bailleur')!.value)
        )
      )
      .subscribe((bailleurs: IBailleur[]) => (this.bailleursSharedCollection = bailleurs));

    this.formationService
      .query()
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing(formations, this.editForm.get('formation')!.value)
        )
      )
      .subscribe((formations: IFormation[]) => (this.formationsSharedCollection = formations));
  }

  protected createFromForm(): IPriseEnCharge {
    return {
      ...new PriseEnCharge(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      nbrePC: this.editForm.get(['nbrePC'])!.value,
      details: this.editForm.get(['details'])!.value,
      bailleur: this.editForm.get(['bailleur'])!.value,
      formation: this.editForm.get(['formation'])!.value,
    };
  }
}
