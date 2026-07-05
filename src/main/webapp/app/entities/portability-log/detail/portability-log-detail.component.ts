import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPortabilityLog } from '../portability-log.model';

@Component({
  selector: 'jhi-portability-log-detail',
  templateUrl: './portability-log-detail.component.html',
  standalone: false,
})
export class PortabilityLogDetailComponent implements OnInit {
  portabilityLog: IPortabilityLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ portabilityLog }) => {
      this.portabilityLog = portabilityLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
