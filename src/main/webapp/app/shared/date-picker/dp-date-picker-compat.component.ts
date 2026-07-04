import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { IDatePickerConfig } from './dp-date-picker-compat.types';

@Component({
  selector: 'dp-date-picker',
  template: `
    <input
      class="form-control"
      type="text"
      [attr.placeholder]="placeholder"
      [disabled]="disabled"
      [ngModel]="value"
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

  private propagateChange: (value: unknown) => void = () => undefined;
  onTouched: () => void = () => undefined;

  writeValue(value: unknown): void {
    this.value = value;
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

  onInputChange(value: unknown): void {
    this.value = value;
    this.propagateChange(value);
    this.ngModelChange.emit(value);
  }
}
