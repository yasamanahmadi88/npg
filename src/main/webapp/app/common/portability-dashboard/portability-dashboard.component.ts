import { Color, ScaleType } from '@swimlane/ngx-charts';
import { Component, OnInit } from '@angular/core';
import { CommonService } from 'app/common/common.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder } from '@angular/forms';
import { ECalendarValue, IDatePickerConfig } from 'app/shared/date-picker/dp-date-picker-compat.types';
import { DATE_FORMAT, DATE_FORMAT2, DATE_FORMAT3 } from '../../config/input.constants';
import { TranslateService } from '@ngx-translate/core';
import moment from 'moment';
import jmoment from 'jalali-moment';
import { AlertService } from '../../core/util/alert.service';

@Component({
  selector: 'jhi-portability-dashboard',
  templateUrl: './portability-dashboard.component.html',
  styleUrls: ['./portability-dashboard.component.scss'],
  standalone: false,
})
export class PortabilityDashboardComponent implements OnInit {
  pieChartData: any[] | null = [];
  barChartData: any[] | undefined = [];
  title = this.translateService.instant('global.menu.entities.portabilityDashboard');
  searchForm = this.fb.group({
    startDate: [],
    endDate: [],
  });

  dpConfig: IDatePickerConfig = {
    disableKeypress: false,
    showMultipleYearsNavigation: true,
    format: DATE_FORMAT,
    multipleYearsNavigateBy: 3,
    monthBtnFormat: 'MMMM',
    showWeekNumbers: false,
    showTwentyFourHours: true,
    showSeconds: true,
    locale: this.translateService.currentLang,
    returnedValueType: ECalendarValue.Moment,
    showGoToCurrent: true,
  };

  colorScheme: Color = {
    name: 'npgColorScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#9370DB', '#87CEFA', '#FA8072', '#FF7F50', '#90EE90', '#9370DB'],
  };

  view: any[] = [700, 300];
  animations = true;
  invalidDate = false;
  startEndRequire = false;

  constructor(
    protected alertService: AlertService,
    protected translateService: TranslateService,
    protected commonService: CommonService,
    protected fb: UntypedFormBuilder
  ) {
    console.warn('Dashboard constructor');
  }

  ngOnInit(): void {
    console.warn('Dashboard ngOnInit');
    this.commonService.fetchPortabilityPortType('', '').subscribe(
      (res: HttpResponse<any[]>) => {
        this.pieChartData = res.body;
      },
      (err: HttpErrorResponse) => {
        console.error('ERROR-'.concat(err.error));
      }
    );
    this.commonService.fetchPortabilityPortDate('', '').subscribe(
      (res: HttpResponse<any[] | any>) => {
        this.barChartData = res.body?.sort((a: any, b: any) => moment(a.name).diff(moment(b.name)));
        this.barChartData?.forEach(x => {
          if (this.translateService.currentLang === 'fa') {
            const originDate = moment(Date.parse(x.name)).format(DATE_FORMAT2);
            const momentDate = jmoment(originDate, DATE_FORMAT3);
            const jalaliDateStr = momentDate.locale('fa').format(DATE_FORMAT);
            x.name = jalaliDateStr;
          }
        });
      },
      (err: HttpErrorResponse) => {
        console.error('ERROR-'.concat(err.error));
      }
    );
  }

  onSelect(event: any): void {
    console.warn('================'.concat(JSON.stringify(event)));
  }

  search(): void {
    const start = this.searchForm.get(['startDate'])!.value ? moment(this.searchForm.get(['startDate'])!.value).format(DATE_FORMAT) : '';
    const end = this.searchForm.get(['endDate'])!.value ? moment(this.searchForm.get(['endDate'])!.value).format(DATE_FORMAT) : '';
    const durationMs = moment(end).diff(moment(start));
    const durationDay = Number(durationMs / (60 * 60 * 24 * 1000));
    if (start && end) {
      this.startEndRequire = false;

      if (durationDay > 90 || durationDay < 0) {
        this.invalidDate = true;
      } else {
        this.commonService.fetchPortabilityPortType(start, end).subscribe(
          (res: HttpResponse<any[]>) => {
            this.pieChartData = res.body;
          },
          (err: HttpErrorResponse) => {
            console.error('ERROR-'.concat(err.error));
          }
        );

        this.commonService.fetchPortabilityPortDate(start, end).subscribe(
          (res: HttpResponse<any[] | any>) => {
            this.barChartData = res.body?.sort((a: any, b: any) => moment(a.name).diff(moment(b.name)));
            this.barChartData?.forEach((x: any) => {
              if (this.translateService.currentLang === 'fa') {
                const originDate = moment(Date.parse(x.name)).format(DATE_FORMAT2);
                const momentDate = jmoment(originDate, DATE_FORMAT3);
                const jalaliDateStr = momentDate.locale('fa').format(DATE_FORMAT);
                x.name = jalaliDateStr;
              }
            });

            this.invalidDate = false;
          },
          (err: HttpErrorResponse) => {
            console.error('ERROR-'.concat(err.error));
          }
        );
      }
    } else {
      this.startEndRequire = true;
    }
  }
}
