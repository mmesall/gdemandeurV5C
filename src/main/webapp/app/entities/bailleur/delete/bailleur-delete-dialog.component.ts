import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBailleur } from '../bailleur.model';
import { BailleurService } from '../service/bailleur.service';

@Component({
  templateUrl: './bailleur-delete-dialog.component.html',
})
export class BailleurDeleteDialogComponent {
  bailleur?: IBailleur;

  constructor(protected bailleurService: BailleurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bailleurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
