import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOffDay } from '../off-day.model';

@Component({
  selector: 'jhi-off-day-detail',
  templateUrl: './off-day-detail.component.html',
  standalone: false,
})
export class OffDayDetailComponent implements OnInit {
  offDay: IOffDay | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offDay }) => {
      this.offDay = offDay;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
