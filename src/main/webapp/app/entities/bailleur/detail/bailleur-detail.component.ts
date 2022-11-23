import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBailleur } from '../bailleur.model';

@Component({
  selector: 'jhi-bailleur-detail',
  templateUrl: './bailleur-detail.component.html',
})
export class BailleurDetailComponent implements OnInit {
  bailleur: IBailleur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bailleur }) => {
      this.bailleur = bailleur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
