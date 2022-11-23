import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISessionForm } from '../session-form.model';

@Component({
  selector: 'jhi-session-form-detail',
  templateUrl: './session-form-detail.component.html',
})
export class SessionFormDetailComponent implements OnInit {
  sessionForm: ISessionForm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sessionForm }) => {
      this.sessionForm = sessionForm;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
