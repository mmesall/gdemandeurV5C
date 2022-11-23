import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommission } from '../commission.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-commission-detail',
  templateUrl: './commission-detail.component.html',
})
export class CommissionDetailComponent implements OnInit {
  commission: ICommission | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commission }) => {
      this.commission = commission;
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
