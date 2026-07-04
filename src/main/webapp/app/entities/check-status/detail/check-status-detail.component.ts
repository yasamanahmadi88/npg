import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckStatus } from '../check-status.model';

@Component({
  selector: 'jhi-check-status-detail',
  templateUrl: './check-status-detail.component.html',
  standalone: false,
})
export class CheckStatusDetailComponent implements OnInit {
  checkStatus: ICheckStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkStatus }) => {
      this.checkStatus = checkStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
