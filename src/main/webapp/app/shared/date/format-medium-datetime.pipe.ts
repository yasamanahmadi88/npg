import { Pipe, PipeTransform } from '@angular/core';

import dayjs from 'dayjs';

@Pipe({
  name: 'formatMediumDatetime',
  standalone: false,
})
export class FormatMediumDatetimePipe implements PipeTransform {
  transform(day: dayjs.Dayjs | null | undefined): string {
    return day ? day.format('YYYY/MM/DD HH:mm:ss') : '';
  }
}
