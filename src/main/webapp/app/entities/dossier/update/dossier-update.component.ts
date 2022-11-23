import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDossier, Dossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { TypeDemandeur } from 'app/entities/enumerations/type-demandeur.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { DiplomeRequis } from 'app/entities/enumerations/diplome-requis.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';

@Component({
  selector: 'jhi-dossier-update',
  templateUrl: './dossier-update.component.html',
})
export class DossierUpdateComponent implements OnInit {
  isSaving = false;
  typeDemandeurValues = Object.keys(TypeDemandeur);
  nomRegionValues = Object.keys(NomRegion);
  diplomeRequisValues = Object.keys(DiplomeRequis);
  niveauEtudeValues = Object.keys(NiveauEtude);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    numDossier: [],
    typeDemandeur: [],
    nom: [],
    prenom: [],
    adresse: [],
    region: [],
    telephone: [],
    email: [],
    photo: [],
    photoContentType: [],
    cv: [],
    cvContentType: [],
    lettreMotivation: [],
    diplomeRequis: [],
    niveauEtude: [],
    profession: [],
    user: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected dossierService: DossierService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dossier }) => {
      this.updateForm(dossier);

      this.loadRelationshipsOptions();
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dossier = this.createFromForm();
    if (dossier.id !== undefined) {
      this.subscribeToSaveResponse(this.dossierService.update(dossier));
    } else {
      this.subscribeToSaveResponse(this.dossierService.create(dossier));
    }
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDossier>>): void {
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

  protected updateForm(dossier: IDossier): void {
    this.editForm.patchValue({
      id: dossier.id,
      numDossier: dossier.numDossier,
      typeDemandeur: dossier.typeDemandeur,
      nom: dossier.nom,
      prenom: dossier.prenom,
      adresse: dossier.adresse,
      region: dossier.region,
      telephone: dossier.telephone,
      email: dossier.email,
      photo: dossier.photo,
      photoContentType: dossier.photoContentType,
      cv: dossier.cv,
      cvContentType: dossier.cvContentType,
      lettreMotivation: dossier.lettreMotivation,
      diplomeRequis: dossier.diplomeRequis,
      niveauEtude: dossier.niveauEtude,
      profession: dossier.profession,
      user: dossier.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, dossier.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IDossier {
    return {
      ...new Dossier(),
      id: this.editForm.get(['id'])!.value,
      numDossier: this.editForm.get(['numDossier'])!.value,
      typeDemandeur: this.editForm.get(['typeDemandeur'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      region: this.editForm.get(['region'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      cvContentType: this.editForm.get(['cvContentType'])!.value,
      cv: this.editForm.get(['cv'])!.value,
      lettreMotivation: this.editForm.get(['lettreMotivation'])!.value,
      diplomeRequis: this.editForm.get(['diplomeRequis'])!.value,
      niveauEtude: this.editForm.get(['niveauEtude'])!.value,
      profession: this.editForm.get(['profession'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
