import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDayOfWeekTimeFrame } from '../day-of-week-time-frame.model';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-day-of-week-time-frame-detail',
  templateUrl: './day-of-week-time-frame-detail.component.html',
  standalone: false,
})
export class DayOfWeekTimeFrameDetailComponent implements OnInit {
  dayOfWeekTimeFrame: IDayOfWeekTimeFrame | null = null;

  constructor(protected translateService: TranslateService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dayOfWeekTimeFrame }) => {
      this.dayOfWeekTimeFrame = dayOfWeekTimeFrame;
    });
  }

  previousState(): void {
    window.history.back();
  }
  detectLang(val: number): any {
    if (this.translateService.currentLang === 'fa') {
      if (val === 1) {
        return 'شنبه';
      } else if (val === 2) {
        return 'یک شنبه';
      } else if (val === 3) {
        return 'دو شنبه';
      } else if (val === 4) {
        return 'سه شنبه';
      } else if (val === 5) {
        return 'چهار شنبه';
      } else if (val === 6) {
        return 'پنج شنبه';
      } else if (val === 7) {
        return 'جمعه';
      }
    } else if (this.translateService.currentLang === 'en') {
      if (val === 1) {
        return 'Saturday';
      } else if (val === 2) {
        return 'Sunday';
      } else if (val === 3) {
        return 'Monday';
      } else if (val === 4) {
        return 'Tuesday';
      } else if (val === 5) {
        return 'Wednesday';
      } else if (val === 6) {
        return 'Thursday';
      } else if (val === 7) {
        return 'Friday';
      }
    }
  }
}
