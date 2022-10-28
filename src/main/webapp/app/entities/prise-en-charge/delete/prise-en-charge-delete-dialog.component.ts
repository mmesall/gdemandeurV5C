import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeService } from '../service/prise-en-charge.service';

@Component({
  templateUrl: './prise-en-charge-delete-dialog.component.html',
})
export class PriseEnChargeDeleteDialogComponent {
  priseEnCharge?: IPriseEnCharge;

  constructor(protected priseEnChargeService: PriseEnChargeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.priseEnChargeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
