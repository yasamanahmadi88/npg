import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUndifinedStatus } from '../undifined-status.model';

@Component({
  selector: 'jhi-undifined-status-detail',
  templateUrl: './undifined-status-detail.component.html',
  standalone: false,
})
export class UndifinedStatusDetailComponent implements OnInit {
  undifinedStatus: IUndifinedStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ undifinedStatus }) => {
      this.undifinedStatus = undifinedStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
