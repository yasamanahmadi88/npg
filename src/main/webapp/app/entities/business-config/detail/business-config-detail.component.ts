import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessConfig } from '../business-config.model';

@Component({
  selector: 'jhi-business-config-detail',
  templateUrl: './business-config-detail.component.html',
  standalone: false,
})
export class BusinessConfigDetailComponent implements OnInit {
  businessConfig: IBusinessConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessConfig }) => {
      this.businessConfig = businessConfig;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
