import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtudiant, Etudiant } from '../etudiant.model';
import { EtudiantService } from '../service/etudiant.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-etudiant-update',
  templateUrl: './etudiant-update.component.html',
})
export class EtudiantUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  usersSharedCollection: IUser[] = [];
  dossiersCollection: IDossier[] = [];
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
    user: [],
    dossier: [],
    localite: [],
  });

  constructor(
    protected etudiantService: EtudiantService,
    protected userService: UserService,
    protected dossierService: DossierService,
    protected localiteService: LocaliteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etudiant }) => {
      this.updateForm(etudiant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etudiant = this.createFromForm();
    if (etudiant.id !== undefined) {
      this.subscribeToSaveResponse(this.etudiantService.update(etudiant));
    } else {
      this.subscribeToSaveResponse(this.etudiantService.create(etudiant));
    }
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  trackLocaliteById(index: number, item: ILocalite): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtudiant>>): void {
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

  protected updateForm(etudiant: IEtudiant): void {
    this.editForm.patchValue({
      id: etudiant.id,
      nom: etudiant.nom,
      prenom: etudiant.prenom,
      dateNaiss: etudiant.dateNaiss,
      lieuNaiss: etudiant.lieuNaiss,
      sexe: etudiant.sexe,
      telephone: etudiant.telephone,
      adressePhysique: etudiant.adressePhysique,
      email: etudiant.email,
      cni: etudiant.cni,
      user: etudiant.user,
      dossier: etudiant.dossier,
      localite: etudiant.localite,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, etudiant.user);
    this.dossiersCollection = this.dossierService.addDossierToCollectionIfMissing(this.dossiersCollection, etudiant.dossier);
    this.localitesSharedCollection = this.localiteService.addLocaliteToCollectionIfMissing(
      this.localitesSharedCollection,
      etudiant.localite
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.dossierService
      .query({ filter: 'etudiant-is-null' })
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersCollection = dossiers));

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

  protected createFromForm(): IEtudiant {
    return {
      ...new Etudiant(),
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
      user: this.editForm.get(['user'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
      localite: this.editForm.get(['localite'])!.value,
    };
  }
}
