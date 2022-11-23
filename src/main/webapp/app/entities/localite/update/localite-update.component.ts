import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILocalite, Localite } from '../localite.model';
import { LocaliteService } from '../service/localite.service';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { DAKAR } from 'app/entities/enumerations/dakar.model';
import { DIOURBEL } from 'app/entities/enumerations/diourbel.model';
import { FATICK } from 'app/entities/enumerations/fatick.model';
import { KAFFRINE } from 'app/entities/enumerations/kaffrine.model';
import { KAOLACK } from 'app/entities/enumerations/kaolack.model';
import { KEDOUGOU } from 'app/entities/enumerations/kedougou.model';
import { KOLDA } from 'app/entities/enumerations/kolda.model';
import { LOUGA } from 'app/entities/enumerations/louga.model';
import { MATAM } from 'app/entities/enumerations/matam.model';
import { SAINTLOUIS } from 'app/entities/enumerations/saintlouis.model';
import { SEDHIOU } from 'app/entities/enumerations/sedhiou.model';
import { TAMBACOUNDA } from 'app/entities/enumerations/tambacounda.model';
import { THIES } from 'app/entities/enumerations/thies.model';
import { ZIGUINCHOR } from 'app/entities/enumerations/ziguinchor.model';

@Component({
  selector: 'jhi-localite-update',
  templateUrl: './localite-update.component.html',
})
export class LocaliteUpdateComponent implements OnInit {
  isSaving = false;
  nomRegionValues = Object.keys(NomRegion);
  dAKARValues = Object.keys(DAKAR);
  dIOURBELValues = Object.keys(DIOURBEL);
  fATICKValues = Object.keys(FATICK);
  kAFFRINEValues = Object.keys(KAFFRINE);
  kAOLACKValues = Object.keys(KAOLACK);
  kEDOUGOUValues = Object.keys(KEDOUGOU);
  kOLDAValues = Object.keys(KOLDA);
  lOUGAValues = Object.keys(LOUGA);
  mATAMValues = Object.keys(MATAM);
  sAINTLOUISValues = Object.keys(SAINTLOUIS);
  sEDHIOUValues = Object.keys(SEDHIOU);
  tAMBACOUNDAValues = Object.keys(TAMBACOUNDA);
  tHIESValues = Object.keys(THIES);
  zIGUINCHORValues = Object.keys(ZIGUINCHOR);

  editForm = this.fb.group({
    id: [],
    region: [null, [Validators.required]],
    autreRegion: [],
    departementDakar: [],
    departementDiourbel: [],
    departementFatick: [],
    departementKaffrine: [],
    departementKaolack: [],
    departementKedougou: [],
    departementKolda: [],
    departementLouga: [],
    departementMatam: [],
    departementSaint: [],
    departementSedhiou: [],
    departementTambacounda: [],
    departementThis: [],
    departementZiguinchor: [],
    autredepartementDakar: [],
    autredepartementDiourbel: [],
    autredepartementFatick: [],
    autredepartementKaffrine: [],
    autredepartementKaolack: [],
    autredepartementKedougou: [],
    autredepartementKolda: [],
    autredepartementLouga: [],
    autredepartementMatam: [],
    autredepartementSaint: [],
    autredepartementSedhiou: [],
    autredepartementTambacounda: [],
    autredepartementThis: [],
    autredepartementZiguinchor: [],
    commune: [null, [Validators.required]],
    nomQuartier: [],
  });

  constructor(protected localiteService: LocaliteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localite }) => {
      this.updateForm(localite);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localite = this.createFromForm();
    if (localite.id !== undefined) {
      this.subscribeToSaveResponse(this.localiteService.update(localite));
    } else {
      this.subscribeToSaveResponse(this.localiteService.create(localite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalite>>): void {
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

  protected updateForm(localite: ILocalite): void {
    this.editForm.patchValue({
      id: localite.id,
      region: localite.region,
      autreRegion: localite.autreRegion,
      departementDakar: localite.departementDakar,
      departementDiourbel: localite.departementDiourbel,
      departementFatick: localite.departementFatick,
      departementKaffrine: localite.departementKaffrine,
      departementKaolack: localite.departementKaolack,
      departementKedougou: localite.departementKedougou,
      departementKolda: localite.departementKolda,
      departementLouga: localite.departementLouga,
      departementMatam: localite.departementMatam,
      departementSaint: localite.departementSaint,
      departementSedhiou: localite.departementSedhiou,
      departementTambacounda: localite.departementTambacounda,
      departementThis: localite.departementThis,
      departementZiguinchor: localite.departementZiguinchor,
      autredepartementDakar: localite.autredepartementDakar,
      autredepartementDiourbel: localite.autredepartementDiourbel,
      autredepartementFatick: localite.autredepartementFatick,
      autredepartementKaffrine: localite.autredepartementKaffrine,
      autredepartementKaolack: localite.autredepartementKaolack,
      autredepartementKedougou: localite.autredepartementKedougou,
      autredepartementKolda: localite.autredepartementKolda,
      autredepartementLouga: localite.autredepartementLouga,
      autredepartementMatam: localite.autredepartementMatam,
      autredepartementSaint: localite.autredepartementSaint,
      autredepartementSedhiou: localite.autredepartementSedhiou,
      autredepartementTambacounda: localite.autredepartementTambacounda,
      autredepartementThis: localite.autredepartementThis,
      autredepartementZiguinchor: localite.autredepartementZiguinchor,
      commune: localite.commune,
      nomQuartier: localite.nomQuartier,
    });
  }

  protected createFromForm(): ILocalite {
    return {
      ...new Localite(),
      id: this.editForm.get(['id'])!.value,
      region: this.editForm.get(['region'])!.value,
      autreRegion: this.editForm.get(['autreRegion'])!.value,
      departementDakar: this.editForm.get(['departementDakar'])!.value,
      departementDiourbel: this.editForm.get(['departementDiourbel'])!.value,
      departementFatick: this.editForm.get(['departementFatick'])!.value,
      departementKaffrine: this.editForm.get(['departementKaffrine'])!.value,
      departementKaolack: this.editForm.get(['departementKaolack'])!.value,
      departementKedougou: this.editForm.get(['departementKedougou'])!.value,
      departementKolda: this.editForm.get(['departementKolda'])!.value,
      departementLouga: this.editForm.get(['departementLouga'])!.value,
      departementMatam: this.editForm.get(['departementMatam'])!.value,
      departementSaint: this.editForm.get(['departementSaint'])!.value,
      departementSedhiou: this.editForm.get(['departementSedhiou'])!.value,
      departementTambacounda: this.editForm.get(['departementTambacounda'])!.value,
      departementThis: this.editForm.get(['departementThis'])!.value,
      departementZiguinchor: this.editForm.get(['departementZiguinchor'])!.value,
      autredepartementDakar: this.editForm.get(['autredepartementDakar'])!.value,
      autredepartementDiourbel: this.editForm.get(['autredepartementDiourbel'])!.value,
      autredepartementFatick: this.editForm.get(['autredepartementFatick'])!.value,
      autredepartementKaffrine: this.editForm.get(['autredepartementKaffrine'])!.value,
      autredepartementKaolack: this.editForm.get(['autredepartementKaolack'])!.value,
      autredepartementKedougou: this.editForm.get(['autredepartementKedougou'])!.value,
      autredepartementKolda: this.editForm.get(['autredepartementKolda'])!.value,
      autredepartementLouga: this.editForm.get(['autredepartementLouga'])!.value,
      autredepartementMatam: this.editForm.get(['autredepartementMatam'])!.value,
      autredepartementSaint: this.editForm.get(['autredepartementSaint'])!.value,
      autredepartementSedhiou: this.editForm.get(['autredepartementSedhiou'])!.value,
      autredepartementTambacounda: this.editForm.get(['autredepartementTambacounda'])!.value,
      autredepartementThis: this.editForm.get(['autredepartementThis'])!.value,
      autredepartementZiguinchor: this.editForm.get(['autredepartementZiguinchor'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      nomQuartier: this.editForm.get(['nomQuartier'])!.value,
    };
  }
}
