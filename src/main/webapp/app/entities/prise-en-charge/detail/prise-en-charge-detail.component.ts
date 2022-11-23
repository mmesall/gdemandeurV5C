import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriseEnCharge } from '../prise-en-charge.model';

@Component({
  selector: 'jhi-prise-en-charge-detail',
  templateUrl: './prise-en-charge-detail.component.html',
})
export class PriseEnChargeDetailComponent implements OnInit {
  priseEnCharge: IPriseEnCharge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priseEnCharge }) => {
      this.priseEnCharge = priseEnCharge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
