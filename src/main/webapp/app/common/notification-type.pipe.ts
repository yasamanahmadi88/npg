import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'notificationType',
  standalone: false,
})
export class NotificationTypePipe implements PipeTransform {
  transform(value: any): any {
    const value_ = JSON.stringify(value);
    if (value_ === '1') {
      return 'SMS';
    } else if (value_ === '2') {
      return 'EMAIL';
    } else if (value_ === '3') {
      return 'PUSH';
    } else {
      return value_;
    }
  }
}
