import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { IDayOfWeekTimeFrame, DayOfWeekTimeFrame } from '../day-of-week-time-frame.model';
import { DayOfWeekTimeFrameService } from '../service/day-of-week-time-frame.service';
import moment from 'moment';
import { ECalendarValue, IDatePickerConfig } from 'app/shared/date-picker/dp-date-picker-compat.types';
import { DATE_TIME_FORMAT3 } from '../../../config/input.constants';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-day-of-week-time-frame-update',
  templateUrl: './day-of-week-time-frame-update.component.html',
  styleUrls: ['./day-of-week-time-frame-update.component.scss'],
  standalone: false,
})
export class DayOfWeekTimeFrameUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    day: [],
    begin: [],
    end: [],
  });
  dpConfig: IDatePickerConfig = {
    disableKeypress: false,
    showMultipleYearsNavigation: true,
    format: DATE_TIME_FORMAT3,
    multipleYearsNavigateBy: 3,
    monthBtnFormat: 'MMMM',
    showWeekNumbers: true,
    showTwentyFourHours: true,
    showSeconds: true,
    locale: this.translateService.currentLang,
    returnedValueType: ECalendarValue.Moment,
    showGoToCurrent: true,
  };

  constructor(
    protected dayOfWeekTimeFrameService: DayOfWeekTimeFrameService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder,
    protected translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dayOfWeekTimeFrame }) => {
      this.updateForm(dayOfWeekTimeFrame);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dayOfWeekTimeFrame = this.createFromForm();
    if (dayOfWeekTimeFrame.id !== undefined) {
      this.subscribeToSaveResponse(this.dayOfWeekTimeFrameService.update(dayOfWeekTimeFrame));
    } else {
      this.subscribeToSaveResponse(this.dayOfWeekTimeFrameService.create(dayOfWeekTimeFrame));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDayOfWeekTimeFrame>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(dayOfWeekTimeFrame: IDayOfWeekTimeFrame): void {
    const loadedBegin = dayOfWeekTimeFrame.begin ? moment(new Date(dayOfWeekTimeFrame.begin)).utc(false) : null;
    const loadedEnd = dayOfWeekTimeFrame.end ? moment(new Date(dayOfWeekTimeFrame.end)).utc(false) : null;
    this.editForm.patchValue({
      id: dayOfWeekTimeFrame.id,
      day: Number(dayOfWeekTimeFrame.day),
      begin: loadedBegin,
      end: loadedEnd,
    });
  }

  protected createFromForm(): IDayOfWeekTimeFrame {
    const startDayH = moment(this.editForm.get(['begin'])!.value).hours() * 3600 * 1000;
    const startDayM = moment(this.editForm.get(['begin'])!.value).minutes() * 60 * 1000;
    const startDayS = moment(this.editForm.get(['begin'])!.value).hours() * 1000;
    const startDay = Number(startDayH) + Number(startDayM) + Number(startDayS);
    const endDayH = moment(this.editForm.get(['end'])!.value).hours() * 3600 * 1000;
    const endDayM = moment(this.editForm.get(['end'])!.value).minutes() * 60 * 1000;
    const endDayS = moment(this.editForm.get(['end'])!.value).hours() * 1000;
    const endDay = Number(endDayH) + Number(endDayM) + Number(endDayS);
    return {
      ...new DayOfWeekTimeFrame(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      begin: startDay,
      end: endDay,
    };
  }
}
