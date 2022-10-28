import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISessionForm } from '../session-form.model';
import { SessionFormService } from '../service/session-form.service';

@Component({
  templateUrl: './session-form-delete-dialog.component.html',
})
export class SessionFormDeleteDialogComponent {
  sessionForm?: ISessionForm;

  constructor(protected sessionFormService: SessionFormService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sessionFormService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
