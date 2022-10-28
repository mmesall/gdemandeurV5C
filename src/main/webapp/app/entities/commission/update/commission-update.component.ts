import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICommission, Commission } from '../commission.model';
import { CommissionService } from '../service/commission.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-commission-update',
  templateUrl: './commission-update.component.html',
})
export class CommissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nomCommission: [],
    mission: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected commissionService: CommissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commission }) => {
      this.updateForm(commission);
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
    const commission = this.createFromForm();
    if (commission.id !== undefined) {
      this.subscribeToSaveResponse(this.commissionService.update(commission));
    } else {
      this.subscribeToSaveResponse(this.commissionService.create(commission));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommission>>): void {
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

  protected updateForm(commission: ICommission): void {
    this.editForm.patchValue({
      id: commission.id,
      nomCommission: commission.nomCommission,
      mission: commission.mission,
    });
  }

  protected createFromForm(): ICommission {
    return {
      ...new Commission(),
      id: this.editForm.get(['id'])!.value,
      nomCommission: this.editForm.get(['nomCommission'])!.value,
      mission: this.editForm.get(['mission'])!.value,
    };
  }
}
