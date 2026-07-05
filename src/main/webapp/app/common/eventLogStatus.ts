import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'eventLogStatusPipe',
  standalone: false,
})
export class EventLogStatusPipe implements PipeTransform {
  transform(value: any): any {
    const value_ = JSON.stringify(value);

    if (value_ === '0') {
      return 'ORDINARY';
    } else if (value_ === '1') {
      return 'EXCEPTION';
    } else {
      return value_;
    }
  }
}
