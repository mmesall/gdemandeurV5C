import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommission } from '../commission.model';
import { CommissionService } from '../service/commission.service';

@Component({
  templateUrl: './commission-delete-dialog.component.html',
})
export class CommissionDeleteDialogComponent {
  commission?: ICommission;

  constructor(protected commissionService: CommissionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commissionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
