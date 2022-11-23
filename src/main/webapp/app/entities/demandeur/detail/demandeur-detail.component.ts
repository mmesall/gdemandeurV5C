import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeur } from '../demandeur.model';

@Component({
  selector: 'jhi-demandeur-detail',
  templateUrl: './demandeur-detail.component.html',
})
export class DemandeurDetailComponent implements OnInit {
  demandeur: IDemandeur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeur }) => {
      this.demandeur = demandeur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
