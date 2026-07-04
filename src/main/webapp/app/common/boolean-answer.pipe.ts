import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'booleanAnswer',
  standalone: false,
})
export class BooleanAnswerPipe implements PipeTransform {
  transform(value: any): any {
    const value_ = JSON.stringify(value);
    if (value_ === 'true' || value_ === '1') {
      return 'بلی';
    } else {
      return 'خیر';
    }
  }
}
