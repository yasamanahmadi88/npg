import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import moment from 'moment';
import { ECalendarValue, IDatePickerConfig } from './dp-date-picker-compat.types';

@Component({
  selector: 'dp-date-picker',
  template: `
    <input
      class="form-control"
      [type]="inputType"
      [attr.placeholder]="placeholder"
      [disabled]="disabled"
      [ngModel]="displayValue"
      (ngModelChange)="onInputChange($event)"
      (blur)="onTouched()"
    />
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DpDatePickerCompatComponent),
      multi: true,
    },
  ],
  standalone: false,
})
export class DpDatePickerCompatComponent implements ControlValueAccessor {
  @Input() config?: IDatePickerConfig;
  @Input() mode?: string;
  @Input() theme?: string;
  @Input() placeholder?: string;
  @Input() disabled = false;
  @Input() displayDate?: unknown;
  @Input() minDate?: unknown;
  @Input() maxDate?: unknown;
  @Input() required = false;
  @Input() name?: string;
  @Input() id?: string;
  @Input() inputClass?: string;
  @Input() calendarValue?: unknown;

  @Output() ngModelChange = new EventEmitter<unknown>();

  value: unknown;
  displayValue = '';

  private propagateChange: (value: unknown) => void = () => undefined;
  onTouched: () => void = () => undefined;

  get inputType(): string {
    return this.config?.showSeconds || this.config?.showTwentyFourHours ? 'datetime-local' : 'date';
  }

  writeValue(value: unknown): void {
    this.value = value;
    this.displayValue = this.toInputValue(value);
  }

  registerOnChange(fn: (value: unknown) => void): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onInputChange(value: string): void {
    this.displayValue = value;
    const parsed = this.fromInputValue(value);
    this.value = parsed;
    this.propagateChange(parsed);
    this.ngModelChange.emit(parsed);
  }

  private toInputValue(value: unknown): string {
    if (!value) {
      return '';
    }
    const m = moment.isMoment(value) ? value : moment(value as string | Date);
    if (!m.isValid()) {
      return String(value);
    }
    return this.inputType === 'datetime-local' ? m.format('YYYY-MM-DDTHH:mm:ss') : m.format('YYYY-MM-DD');
  }

  private fromInputValue(value: string): unknown {
    if (!value) {
      return null;
    }
    const m = moment(value, this.inputType === 'datetime-local' ? 'YYYY-MM-DDTHH:mm:ss' : 'YYYY-MM-DD', true);
    if (!m.isValid()) {
      return value;
    }
    if (this.config?.returnedValueType === ECalendarValue.Moment) {
      return m;
    }
    return m.toDate();
  }
}
