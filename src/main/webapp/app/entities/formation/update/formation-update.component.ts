import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFormation, Formation } from '../formation.model';
import { FormationService } from '../service/formation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { TypeFormation } from 'app/entities/enumerations/type-formation.model';
import { Admission } from 'app/entities/enumerations/admission.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { Secteur } from 'app/entities/enumerations/secteur.model';
import { NomDiplome } from 'app/entities/enumerations/nom-diplome.model';

@Component({
  selector: 'jhi-formation-update',
  templateUrl: './formation-update.component.html',
})
export class FormationUpdateComponent implements OnInit {
  isSaving = false;
  typeFormationValues = Object.keys(TypeFormation);
  admissionValues = Object.keys(Admission);
  diplomeRequisValues = Object.keys(DiplomeRequis);
  secteurValues = Object.keys(Secteur);
  nomDiplomeValues = Object.keys(NomDiplome);

  editForm = this.fb.group({
    id: [],
    nomFormation: [],
    typeFormation: [],
    duree: [],
    admission: [],
    diplomeRequis: [],
    autreDiplome: [],
    secteur: [],
    autreSecteur: [],
    ficheFormation: [],
    ficheFormationContentType: [],
    programmes: [],
    nomConcours: [],
    dateOuverture: [],
    dateCloture: [],
    dateConcours: [],
    libellePC: [],
    montantPriseEnCharge: [],
    detailPC: [],
    nomDiplome: [],
    nomDebouche: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected formationService: FormationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formation }) => {
      this.updateForm(formation);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('jhipsterbaseKeycloak2App.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formation = this.createFromForm();
    if (formation.id !== undefined) {
      this.subscribeToSaveResponse(this.formationService.update(formation));
    } else {
      this.subscribeToSaveResponse(this.formationService.create(formation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormation>>): void {
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

  protected updateForm(formation: IFormation): void {
    this.editForm.patchValue({
      id: formation.id,
      nomFormation: formation.nomFormation,
      typeFormation: formation.typeFormation,
      duree: formation.duree,
      admission: formation.admission,
      diplomeRequis: formation.diplomeRequis,
      autreDiplome: formation.autreDiplome,
      secteur: formation.secteur,
      autreSecteur: formation.autreSecteur,
      ficheFormation: formation.ficheFormation,
      ficheFormationContentType: formation.ficheFormationContentType,
      programmes: formation.programmes,
      nomConcours: formation.nomConcours,
      dateOuverture: formation.dateOuverture,
      dateCloture: formation.dateCloture,
      dateConcours: formation.dateConcours,
      libellePC: formation.libellePC,
      montantPriseEnCharge: formation.montantPriseEnCharge,
      detailPC: formation.detailPC,
      nomDiplome: formation.nomDiplome,
      nomDebouche: formation.nomDebouche,
    });
  }

  protected createFromForm(): IFormation {
    return {
      ...new Formation(),
      id: this.editForm.get(['id'])!.value,
      nomFormation: this.editForm.get(['nomFormation'])!.value,
      typeFormation: this.editForm.get(['typeFormation'])!.value,
      duree: this.editForm.get(['duree'])!.value,
      admission: this.editForm.get(['admission'])!.value,
      diplomeRequis: this.editForm.get(['diplomeRequis'])!.value,
      autreDiplome: this.editForm.get(['autreDiplome'])!.value,
      secteur: this.editForm.get(['secteur'])!.value,
      autreSecteur: this.editForm.get(['autreSecteur'])!.value,
      ficheFormationContentType: this.editForm.get(['ficheFormationContentType'])!.value,
      ficheFormation: this.editForm.get(['ficheFormation'])!.value,
      programmes: this.editForm.get(['programmes'])!.value,
      nomConcours: this.editForm.get(['nomConcours'])!.value,
      dateOuverture: this.editForm.get(['dateOuverture'])!.value,
      dateCloture: this.editForm.get(['dateCloture'])!.value,
      dateConcours: this.editForm.get(['dateConcours'])!.value,
      libellePC: this.editForm.get(['libellePC'])!.value,
      montantPriseEnCharge: this.editForm.get(['montantPriseEnCharge'])!.value,
      detailPC: this.editForm.get(['detailPC'])!.value,
      nomDiplome: this.editForm.get(['nomDiplome'])!.value,
      nomDebouche: this.editForm.get(['nomDebouche'])!.value,
    };
  }
}
