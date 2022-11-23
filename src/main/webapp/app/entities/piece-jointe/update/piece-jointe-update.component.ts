import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPieceJointe, PieceJointe } from '../piece-jointe.model';
import { PieceJointeService } from '../service/piece-jointe.service';
import { IDemande } from 'app/entities/demande/demande.model';
import { DemandeService } from 'app/entities/demande/service/demande.service';
import { TypePiece } from 'app/entities/enumerations/type-piece.model';

@Component({
  selector: 'jhi-piece-jointe-update',
  templateUrl: './piece-jointe-update.component.html',
})
export class PieceJointeUpdateComponent implements OnInit {
  isSaving = false;
  typePieceValues = Object.keys(TypePiece);

  demandesSharedCollection: IDemande[] = [];

  editForm = this.fb.group({
    id: [],
    typePiece: [],
    nomPiece: [],
    demande: [],
  });

  constructor(
    protected pieceJointeService: PieceJointeService,
    protected demandeService: DemandeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pieceJointe }) => {
      this.updateForm(pieceJointe);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pieceJointe = this.createFromForm();
    if (pieceJointe.id !== undefined) {
      this.subscribeToSaveResponse(this.pieceJointeService.update(pieceJointe));
    } else {
      this.subscribeToSaveResponse(this.pieceJointeService.create(pieceJointe));
    }
  }

  trackDemandeById(index: number, item: IDemande): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPieceJointe>>): void {
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

  protected updateForm(pieceJointe: IPieceJointe): void {
    this.editForm.patchValue({
      id: pieceJointe.id,
      typePiece: pieceJointe.typePiece,
      nomPiece: pieceJointe.nomPiece,
      demande: pieceJointe.demande,
    });

    this.demandesSharedCollection = this.demandeService.addDemandeToCollectionIfMissing(this.demandesSharedCollection, pieceJointe.demande);
  }

  protected loadRelationshipsOptions(): void {
    this.demandeService
      .query()
      .pipe(map((res: HttpResponse<IDemande[]>) => res.body ?? []))
      .pipe(
        map((demandes: IDemande[]) => this.demandeService.addDemandeToCollectionIfMissing(demandes, this.editForm.get('demande')!.value))
      )
      .subscribe((demandes: IDemande[]) => (this.demandesSharedCollection = demandes));
  }

  protected createFromForm(): IPieceJointe {
    return {
      ...new PieceJointe(),
      id: this.editForm.get(['id'])!.value,
      typePiece: this.editForm.get(['typePiece'])!.value,
      nomPiece: this.editForm.get(['nomPiece'])!.value,
      demande: this.editForm.get(['demande'])!.value,
    };
  }
}
