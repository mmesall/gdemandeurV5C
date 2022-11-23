import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IServices, Services } from '../services.model';
import { ServicesService } from '../service/services.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICommission } from 'app/entities/commission/commission.model';
import { CommissionService } from 'app/entities/commission/service/commission.service';

@Component({
  selector: 'jhi-services-update',
  templateUrl: './services-update.component.html',
})
export class ServicesUpdateComponent implements OnInit {
  isSaving = false;

  commissionsCollection: ICommission[] = [];

  editForm = this.fb.group({
    id: [],
    logo: [],
    logoContentType: [],
    nomService: [],
    chefService: [],
    description: [],
    isPilotage: [],
    commission: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected servicesService: ServicesService,
    protected commissionService: CommissionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ services }) => {
      this.updateForm(services);

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
    const services = this.createFromForm();
    if (services.id !== undefined) {
      this.subscribeToSaveResponse(this.servicesService.update(services));
    } else {
      this.subscribeToSaveResponse(this.servicesService.create(services));
    }
  }

  trackCommissionById(index: number, item: ICommission): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServices>>): void {
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

  protected updateForm(services: IServices): void {
    this.editForm.patchValue({
      id: services.id,
      logo: services.logo,
      logoContentType: services.logoContentType,
      nomService: services.nomService,
      chefService: services.chefService,
      description: services.description,
      isPilotage: services.isPilotage,
      commission: services.commission,
    });

    this.commissionsCollection = this.commissionService.addCommissionToCollectionIfMissing(this.commissionsCollection, services.commission);
  }

  protected loadRelationshipsOptions(): void {
    this.commissionService
      .query({ filter: 'services-is-null' })
      .pipe(map((res: HttpResponse<ICommission[]>) => res.body ?? []))
      .pipe(
        map((commissions: ICommission[]) =>
          this.commissionService.addCommissionToCollectionIfMissing(commissions, this.editForm.get('commission')!.value)
        )
      )
      .subscribe((commissions: ICommission[]) => (this.commissionsCollection = commissions));
  }

  protected createFromForm(): IServices {
    return {
      ...new Services(),
      id: this.editForm.get(['id'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      nomService: this.editForm.get(['nomService'])!.value,
      chefService: this.editForm.get(['chefService'])!.value,
      description: this.editForm.get(['description'])!.value,
      isPilotage: this.editForm.get(['isPilotage'])!.value,
      commission: this.editForm.get(['commission'])!.value,
    };
  }
}
