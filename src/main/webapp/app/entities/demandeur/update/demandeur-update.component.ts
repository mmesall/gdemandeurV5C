import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandeur, Demandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Profil } from 'app/entities/enumerations/profil.model';

@Component({
  selector: 'jhi-demandeur-update',
  templateUrl: './demandeur-update.component.html',
})
export class DemandeurUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);
  profilValues = Object.keys(Profil);

  dossiersCollection: IDossier[] = [];
  usersSharedCollection: IUser[] = [];
  elevesCollection: IEleve[] = [];
  etudiantsCollection: IEtudiant[] = [];
  professionnelsCollection: IProfessionnel[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    dateNaiss: [],
    lieuNaiss: [],
    sexe: [],
    telephone: [],
    email: [],
    profil: [],
    dossier: [],
    user: [],
    eleve: [],
    etudiant: [],
    professionnel: [],
  });

  constructor(
    protected demandeurService: DemandeurService,
    protected dossierService: DossierService,
    protected userService: UserService,
    protected eleveService: EleveService,
    protected etudiantService: EtudiantService,
    protected professionnelService: ProfessionnelService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeur }) => {
      this.updateForm(demandeur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeur = this.createFromForm();
    if (demandeur.id !== undefined) {
      this.subscribeToSaveResponse(this.demandeurService.update(demandeur));
    } else {
      this.subscribeToSaveResponse(this.demandeurService.create(demandeur));
    }
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  trackUserById(index: number, item: IUser): string {
    return item.id!;
  }

  trackEleveById(index: number, item: IEleve): number {
    return item.id!;
  }

  trackEtudiantById(index: number, item: IEtudiant): number {
    return item.id!;
  }

  trackProfessionnelById(index: number, item: IProfessionnel): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeur>>): void {
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

  protected updateForm(demandeur: IDemandeur): void {
    this.editForm.patchValue({
      id: demandeur.id,
      nom: demandeur.nom,
      prenom: demandeur.prenom,
      dateNaiss: demandeur.dateNaiss,
      lieuNaiss: demandeur.lieuNaiss,
      sexe: demandeur.sexe,
      telephone: demandeur.telephone,
      email: demandeur.email,
      profil: demandeur.profil,
      dossier: demandeur.dossier,
      user: demandeur.user,
      eleve: demandeur.eleve,
      etudiant: demandeur.etudiant,
      professionnel: demandeur.professionnel,
    });

    this.dossiersCollection = this.dossierService.addDossierToCollectionIfMissing(this.dossiersCollection, demandeur.dossier);
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, demandeur.user);
    this.elevesCollection = this.eleveService.addEleveToCollectionIfMissing(this.elevesCollection, demandeur.eleve);
    this.etudiantsCollection = this.etudiantService.addEtudiantToCollectionIfMissing(this.etudiantsCollection, demandeur.etudiant);
    this.professionnelsCollection = this.professionnelService.addProfessionnelToCollectionIfMissing(
      this.professionnelsCollection,
      demandeur.professionnel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersCollection = dossiers));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.eleveService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing(eleves, this.editForm.get('eleve')!.value)))
      .subscribe((eleves: IEleve[]) => (this.elevesCollection = eleves));

    this.etudiantService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IEtudiant[]>) => res.body ?? []))
      .pipe(
        map((etudiants: IEtudiant[]) =>
          this.etudiantService.addEtudiantToCollectionIfMissing(etudiants, this.editForm.get('etudiant')!.value)
        )
      )
      .subscribe((etudiants: IEtudiant[]) => (this.etudiantsCollection = etudiants));

    this.professionnelService
      .query({ filter: 'demandeur-is-null' })
      .pipe(map((res: HttpResponse<IProfessionnel[]>) => res.body ?? []))
      .pipe(
        map((professionnels: IProfessionnel[]) =>
          this.professionnelService.addProfessionnelToCollectionIfMissing(professionnels, this.editForm.get('professionnel')!.value)
        )
      )
      .subscribe((professionnels: IProfessionnel[]) => (this.professionnelsCollection = professionnels));
  }

  protected createFromForm(): IDemandeur {
    return {
      ...new Demandeur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      dateNaiss: this.editForm.get(['dateNaiss'])!.value,
      lieuNaiss: this.editForm.get(['lieuNaiss'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value,
      profil: this.editForm.get(['profil'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
      user: this.editForm.get(['user'])!.value,
      eleve: this.editForm.get(['eleve'])!.value,
      etudiant: this.editForm.get(['etudiant'])!.value,
      professionnel: this.editForm.get(['professionnel'])!.value,
    };
  }
}
