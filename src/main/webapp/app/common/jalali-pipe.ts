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
    const format_ = args ? args[0] : 'YYYY-MM-DD';
    const MomentDate = jmoment(value, format_);
    return MomentDate.locale('fa').format(format_);
  }
}
