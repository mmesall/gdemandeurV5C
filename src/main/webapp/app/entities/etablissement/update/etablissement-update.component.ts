import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtablissement, Etablissement } from '../etablissement.model';
import { EtablissementService } from '../service/etablissement.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILocalite } from 'app/entities/localite/localite.model';
import { LocaliteService } from 'app/entities/localite/service/localite.service';
import { TypeEtablissement } from 'app/entities/enumerations/type-etablissement.model';

@Component({
  selector: 'jhi-etablissement-update',
  templateUrl: './etablissement-update.component.html',
})
export class EtablissementUpdateComponent implements OnInit {
  isSaving = false;
  typeEtablissementValues = Object.keys(TypeEtablissement);

  localitesSharedCollection: ILocalite[] = [];

  editForm = this.fb.group({
    id: [],
    nomEtablissement: [],
    photo: [],
    photoContentType: [],
    adresse: [],
    email: [],
    telephone: [],
    typeEtablissement: [],
    localite: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected etablissementService: EtablissementService,
    protected localiteService: LocaliteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablissement }) => {
      this.updateForm(etablissement);

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
    const etablissement = this.createFromForm();
    if (etablissement.id !== undefined) {
      this.subscribeToSaveResponse(this.etablissementService.update(etablissement));
    } else {
      this.subscribeToSaveResponse(this.etablissementService.create(etablissement));
    }
  }

  trackLocaliteById(index: number, item: ILocalite): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablissement>>): void {
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

  protected updateForm(etablissement: IEtablissement): void {
    this.editForm.patchValue({
      id: etablissement.id,
      nomEtablissement: etablissement.nomEtablissement,
      photo: etablissement.photo,
      photoContentType: etablissement.photoContentType,
      adresse: etablissement.adresse,
      email: etablissement.email,
      telephone: etablissement.telephone,
      typeEtablissement: etablissement.typeEtablissement,
      localite: etablissement.localite,
    });

    this.localitesSharedCollection = this.localiteService.addLocaliteToCollectionIfMissing(
      this.localitesSharedCollection,
      etablissement.localite
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IEtablissement {
    return {
      ...new Etablissement(),
      id: this.editForm.get(['id'])!.value,
      nomEtablissement: this.editForm.get(['nomEtablissement'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      typeEtablissement: this.editForm.get(['typeEtablissement'])!.value,
      localite: this.editForm.get(['localite'])!.value,
    };
  }
}
