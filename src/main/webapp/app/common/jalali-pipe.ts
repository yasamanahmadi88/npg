import { Pipe, PipeTransform } from '@angular/core';
import jmoment from 'jalali-moment';

@Pipe({
  name: 'jalaliFormatter',
  standalone: false,
})
export class JalaliFormatterPipe implements PipeTransform {
  transform(value: any, args?: any): any {
    if (!value) {
      return '';
    }
    const format = args ? args[0] : 'YYYY-MM-DD';
    const parsed = jmoment(value);
    if (!parsed.isValid()) {
      return '';
    }
    return parsed.locale('fa').format(format);
  }
}
