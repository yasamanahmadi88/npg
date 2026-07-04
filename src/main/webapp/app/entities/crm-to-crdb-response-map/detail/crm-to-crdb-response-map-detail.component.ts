import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';

@Component({
  selector: 'jhi-crm-to-crdb-response-map-detail',
  templateUrl: './crm-to-crdb-response-map-detail.component.html',
  standalone: false,
})
export class CrmToCrdbResponseMapDetailComponent implements OnInit {
  crmToCrdbResponseMap: ICrmToCrdbResponseMap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crmToCrdbResponseMap }) => {
      this.crmToCrdbResponseMap = crmToCrdbResponseMap;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
