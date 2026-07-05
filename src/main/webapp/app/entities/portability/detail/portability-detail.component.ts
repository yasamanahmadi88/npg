import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPortability } from '../portability.model';

@Component({
  selector: 'jhi-portability-detail',
  templateUrl: './portability-detail.component.html',
  standalone: false,
})
export class PortabilityDetailComponent implements OnInit {
  portability: IPortability | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ portability }) => {
      this.portability = portability[0];
    });
  }

  previousState(): void {
    window.history.back();
  }
}
