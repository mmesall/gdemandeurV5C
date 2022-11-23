import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IConcours, Concours } from '../concours.model';
import { ConcoursService } from '../service/concours.service';
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';

@Component({
  selector: 'jhi-concours-update',
  templateUrl: './concours-update.component.html',
})
export class ConcoursUpdateComponent implements OnInit {
  isSaving = false;

  formationsSharedCollection: IFormation[] = [];

  editForm = this.fb.group({
    id: [],
    nomConcours: [],
    dateOuverture: [],
    dateCloture: [],
    dateConcours: [],
    formation: [],
  });

  constructor(
    protected concoursService: ConcoursService,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concours }) => {
      this.updateForm(concours);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concours = this.createFromForm();
    if (concours.id !== undefined) {
      this.subscribeToSaveResponse(this.concoursService.update(concours));
    } else {
      this.subscribeToSaveResponse(this.concoursService.create(concours));
    }
  }

  trackFormationById(index: number, item: IFormation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcours>>): void {
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

  protected updateForm(concours: IConcours): void {
    this.editForm.patchValue({
      id: concours.id,
      nomConcours: concours.nomConcours,
      dateOuverture: concours.dateOuverture,
      dateCloture: concours.dateCloture,
      dateConcours: concours.dateConcours,
      formation: concours.formation,
    });

    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing(
      this.formationsSharedCollection,
      concours.formation
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
  }

  protected createFromForm(): IConcours {
    return {
      ...new Concours(),
      id: this.editForm.get(['id'])!.value,
      nomConcours: this.editForm.get(['nomConcours'])!.value,
      dateOuverture: this.editForm.get(['dateOuverture'])!.value,
      dateCloture: this.editForm.get(['dateCloture'])!.value,
      dateConcours: this.editForm.get(['dateConcours'])!.value,
      formation: this.editForm.get(['formation'])!.value,
    };
  }
}
