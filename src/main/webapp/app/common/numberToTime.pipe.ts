import { Pipe, PipeTransform } from '@angular/core';
import moment from 'moment';

@Pipe({
  name: 'num2time',
  standalone: false,
})
export class NumberToTimePipe implements PipeTransform {
  transform(value: number | any): any {
    if (!value) {
      return '';
    }
    return moment(new Date(value)).utc(false).format('HH:mm:ss');
  }
}
