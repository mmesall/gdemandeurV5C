import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISessionForm, SessionForm } from '../session-form.model';
import { SessionFormService } from '../service/session-form.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';

@Component({
  selector: 'jhi-session-form-update',
  templateUrl: './session-form-update.component.html',
})
export class SessionFormUpdateComponent implements OnInit {
  isSaving = false;

  formationsSharedCollection: IFormation[] = [];
  etablissementsSharedCollection: IEtablissement[] = [];

  editForm = this.fb.group({
    id: [],
    nomSession: [],
    annee: [],
    dateDebutSess: [],
    dateFinSess: [],
    formation: [],
    etablissement: [],
  });

  constructor(
    protected sessionFormService: SessionFormService,
    protected formationService: FormationService,
    protected etablissementService: EtablissementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sessionForm }) => {
      this.updateForm(sessionForm);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sessionForm = this.createFromForm();
    if (sessionForm.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionFormService.update(sessionForm));
    } else {
      this.subscribeToSaveResponse(this.sessionFormService.create(sessionForm));
    }
  }

  trackFormationById(index: number, item: IFormation): number {
    return item.id!;
  }

  trackEtablissementById(index: number, item: IEtablissement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISessionForm>>): void {
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

  protected updateForm(sessionForm: ISessionForm): void {
    this.editForm.patchValue({
      id: sessionForm.id,
      nomSession: sessionForm.nomSession,
      annee: sessionForm.annee,
      dateDebutSess: sessionForm.dateDebutSess,
      dateFinSess: sessionForm.dateFinSess,
      formation: sessionForm.formation,
      etablissement: sessionForm.etablissement,
    });

    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsSharedCollection,
      sessionForm.formation
    );
    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing(
      this.etablissementsSharedCollection,
      sessionForm.etablissement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formationService
      .query()
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing(formations, this.editForm.get('formation')!.value)
        )
      )
      .subscribe((formations: IFormation[]) => (this.formationsSharedCollection = formations));

    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing(etablissements, this.editForm.get('etablissement')!.value)
        )
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));
  }

  protected createFromForm(): ISessionForm {
    return {
      ...new SessionForm(),
      id: this.editForm.get(['id'])!.value,
      nomSession: this.editForm.get(['nomSession'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      dateDebutSess: this.editForm.get(['dateDebutSess'])!.value,
      dateFinSess: this.editForm.get(['dateFinSess'])!.value,
      formation: this.editForm.get(['formation'])!.value,
      etablissement: this.editForm.get(['etablissement'])!.value,
    };
  }
}
