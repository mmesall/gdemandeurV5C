import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IServices } from '../services.model';
import { ServicesService } from '../service/services.service';

@Component({
  templateUrl: './services-delete-dialog.component.html',
})
export class ServicesDeleteDialogComponent {
  services?: IServices;

  constructor(protected servicesService: ServicesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
