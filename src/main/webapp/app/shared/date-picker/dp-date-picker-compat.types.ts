export enum ECalendarValue {
  Shamsi = 'shamsi',
  Miladi = 'miladi',
  Moment = 'moment',
}

export interface IDatePickerConfig {
  format?: string;
  drops?: string;
  disableKeypress?: boolean;
  showTwentyFourHours?: boolean;
  showNearMonthDays?: boolean;
  showMultipleYearsNavigation?: boolean;
  locale?: string;
  firstDayOfWeek?: string;
  monthFormat?: string;

  // Used by old ng2-jalali-date-picker configs in this project
  returnedValueType?: ECalendarValue | string;

  [key: string]: unknown;
}
