import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeFrame } from '../time-frame.model';

@Component({
  selector: 'jhi-time-frame-detail',
  templateUrl: './time-frame-detail.component.html',
  standalone: false,
})
export class TimeFrameDetailComponent implements OnInit {
  timeFrame: ITimeFrame | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeFrame }) => {
      this.timeFrame = timeFrame;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
