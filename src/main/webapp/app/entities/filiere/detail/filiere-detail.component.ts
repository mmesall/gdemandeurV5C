import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFiliere } from '../filiere.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-filiere-detail',
  templateUrl: './filiere-detail.component.html',
})
export class FiliereDetailComponent implements OnInit {
  filiere: IFiliere | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ filiere }) => {
      this.filiere = filiere;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
