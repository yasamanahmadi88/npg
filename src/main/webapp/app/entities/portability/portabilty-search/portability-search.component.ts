import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';
import { ECalendarValue, IDatePickerConfig } from 'app/shared/date-picker/dp-date-picker-compat.types';
import { PortabilityService } from '../service/portability.service';
import { DATE_TIME_FORMAT2 } from '../../../config/input.constants';
import moment from 'moment';

@Component({
  selector: 'jhi-portability-search',
  templateUrl: './portability-search.component.html',
  styleUrls: ['./portability-search.component.scss'],
  standalone: false,
})
export class PortabilitySearchComponent implements OnInit, OnChanges {
  @Output() doClean = new EventEmitter<any>();
  @Input() cleanMode!: boolean;
  @Input() searchMode!: string;
  @Output() resultSearch = new EventEmitter<any>();
  @Output() query = new EventEmitter<any>();

  dpConfig: IDatePickerConfig = {
    disableKeypress: false,
    showMultipleYearsNavigation: true,
    format: DATE_TIME_FORMAT2,
    multipleYearsNavigateBy: 3,
    monthBtnFormat: 'MMMM',
    showWeekNumbers: true,
    showTwentyFourHours: true,
    showSeconds: true,
    locale: this.translateService.currentLang,
    returnedValueType: ECalendarValue.Moment,
  };
  expanded = false;
  editForm = this.fb.group({
    porRequestId: [],
    porNumber: [],
    porCrDate: [],
    porIdNumber: [],
    porMnpid: [],
    porReceiver: [],
    porRouting: [],
    porStatus: [],
    porPortedDate: [],
    porDeadline: [],
    porUpdDate: [],
    porErr: [],
    porAccType: [],
    porType: [],
  });

  constructor(
    protected translateService: TranslateService,
    protected portabilityService: PortabilityService,
    protected fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.translateService.onLangChange.subscribe((d: LangChangeEvent) => {
      this.dpConfig.locale = d.lang;
      this.editForm.get('creationDate')?.setValue(null);
    });
  }

  checkExpanded(): void {
    this.expanded = !this.expanded;
  }

  search(): void {
    const query: any = {};
    if (this.editForm.get(['porRequestId'])?.value) {
      query['porRequestId.equals'] = this.editForm.get(['porRequestId'])?.value;
    }
    if (this.editForm.get(['porNumber'])?.value) {
      query['porNumber.equals'] = this.editForm.get(['porNumber'])?.value;
    }
    if (this.editForm.get(['porCrDate'])?.value) {
      query['porCrDatePlain.equals'] = this.editForm.get(['porCrDate'])?.value
        ? moment(this.editForm.get(['porCrDate'])?.value).format(DATE_TIME_FORMAT2)
        : null;
    }
    if (this.editForm.get(['porIdNumber'])?.value) {
      query['porIdNumber.equals'] = this.editForm.get(['porIdNumber'])?.value;
    }
    if (this.editForm.get(['porMnpid'])?.value) {
      query['porMnpid.equals'] = this.editForm.get(['porMnpid'])?.value;
    }

    if (this.editForm.get(['porReceiver'])?.value) {
      query['porOpr.equals'] = this.editForm.get(['porReceiver'])?.value;
    }
    if (this.editForm.get(['porRouting'])?.value) {
      query['porRouting.equals'] = this.editForm.get(['porRouting'])?.value;
    }
    if (this.editForm.get(['porStatus'])?.value) {
      query['porStatus.equals'] = this.editForm.get(['porStatus'])?.value;
    }
    if (this.editForm.get(['porPortedDate'])?.value) {
      query['porPortedDatePlain.equals'] = this.editForm.get(['porPortedDate'])?.value
        ? moment(this.editForm.get(['porPortedDate'])?.value).format(DATE_TIME_FORMAT2)
        : null;
    }
    if (this.editForm.get(['porUpdDate'])?.value) {
      query['porUpdDatePlain.equals'] = this.editForm.get(['porUpdDate'])?.value
        ? moment(this.editForm.get(['porUpdDate'])?.value).format(DATE_TIME_FORMAT2)
        : null;
    }

    if (this.editForm.get(['porDeadline'])?.value) {
      query['porDeadlinePlain.equals'] = this.editForm.get(['porDeadline'])?.value
        ? moment(this.editForm.get(['porDeadline'])?.value).format(DATE_TIME_FORMAT2)
        : null;
    }
    if (this.editForm.get(['porErr'])?.value) {
      query['porErr.equals'] = this.editForm.get(['porErr'])?.value;
    }
    if (this.editForm.get(['porAccType'])?.value) {
      query['porAccType.equals'] = this.editForm.get(['porAccType'])?.value;
    }
    if (this.editForm.get(['porType'])?.value) {
      query['porType.equals'] = this.editForm.get(['porType'])?.value;
    }
    this.portabilityService.query(query).subscribe(res => {
      this.resultSearch.emit(res.body);
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.cleanMode) {
      this.clear();
    }
    this.cleanMode = false;
  }

  clear(): void {
    this.editForm.reset();
    this.doClean.emit(true);
  }
}
