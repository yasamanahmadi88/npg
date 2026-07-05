import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { DpDatePickerCompatComponent } from './dp-date-picker-compat.component';

@NgModule({
  imports: [CommonModule, FormsModule],
  declarations: [DpDatePickerCompatComponent],
  exports: [DpDatePickerCompatComponent],
})
export class DpDatePickerCompatModule {}
