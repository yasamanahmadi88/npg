import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { ITimeFrame, TimeFrame } from '../time-frame.model';
import { TimeFrameService } from '../service/time-frame.service';
import { OffDayService } from '../../off-day/service/off-day.service';
import { IOffDay } from '../../off-day/off-day.model';
import { ECalendarValue, IDatePickerConfig } from 'app/shared/date-picker/dp-date-picker-compat.types';
import { DATE_TIME_FORMAT3 } from '../../../config/input.constants';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';
import moment from 'moment';

@Component({
  selector: 'jhi-time-frame-update',
  templateUrl: './time-frame-update.component.html',
  styleUrls: ['./time-frame-update.component.scss'],
  standalone: false,
})
export class TimeFrameUpdateComponent implements OnInit {
  isSaving = false;
  offDays?: IOffDay[] | null;
  today = moment(new Date().getTime());
  editForm = this.fb.group({
    id: [],
    begin: [],
    end: [],
    offDayId: [],
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
    locale: this.translateService.currentLang ? this.translateService.currentLang : 'en',
    returnedValueType: ECalendarValue.Moment,
    showGoToCurrent: true,
  };

  constructor(
    protected translateService: TranslateService,
    protected timeFrameService: TimeFrameService,
    protected activatedRoute: ActivatedRoute,
    protected fb: UntypedFormBuilder,
    protected offDayService: OffDayService
  ) {
    this.editForm.get('begin')?.setValue(null);
    this.editForm.get('end')?.setValue(null);
  }

  ngOnInit(): void {
    this.editForm.markAsPristine();
    this.activatedRoute.data.subscribe(({ timeFrame }) => {
      this.updateForm(timeFrame);
    });
    this.offDayService.queryAll().subscribe((res: HttpResponse<IOffDay[]>) => {
      this.offDays = res.body;
    });
    this.translateService.onLangChange.subscribe((d: LangChangeEvent) => {
      this.dpConfig.locale = d.lang;
      this.editForm.get('begin')?.setValue(null);
      this.editForm.get('end')?.setValue(null);
      this.editForm.markAsPristine();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeFrame = this.createFromForm();
    if (timeFrame.id !== undefined) {
      this.subscribeToSaveResponse(this.timeFrameService.update(timeFrame));
    } else {
      this.subscribeToSaveResponse(this.timeFrameService.create(timeFrame));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeFrame>>): void {
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

  protected updateForm(timeFrame: ITimeFrame): void {
    const loadedBegin = timeFrame.begin ? moment(new Date(timeFrame.begin)).utc(false) : null;
    const loadedEnd = timeFrame.end ? moment(new Date(timeFrame.end)).utc(false) : null;
    this.editForm.patchValue({
      id: timeFrame.id,
      begin: loadedBegin,
      end: loadedEnd,
      offDayId: timeFrame.offDayId,
    });
  }

  protected createFromForm(): ITimeFrame {
    const startDayH = moment(this.editForm.get(['begin'])!.value).hours() * 3600 * 1000;
    const startDayM = moment(this.editForm.get(['begin'])!.value).minutes() * 60 * 1000;
    const startDayS = moment(this.editForm.get(['begin'])!.value).hours() * 1000;
    const startDay = Number(startDayH) + Number(startDayM) + Number(startDayS);
    const endDayH = moment(this.editForm.get(['end'])!.value).hours() * 3600 * 1000;
    const endDayM = moment(this.editForm.get(['end'])!.value).minutes() * 60 * 1000;
    const endDayS = moment(this.editForm.get(['end'])!.value).hours() * 1000;
    const endDay = Number(endDayH) + Number(endDayM) + Number(endDayS);
    return {
      ...new TimeFrame(),
      id: this.editForm.get(['id'])!.value,
      begin: startDay ? startDay : null,
      end: endDay ? endDay : null,
      offDayId: this.editForm.get(['offDayId'])!.value,
    };
  }
}
