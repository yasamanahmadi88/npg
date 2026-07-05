import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'notificationLanguage',
  standalone: false,
})
export class NotificationLanguagePipe implements PipeTransform {
  transform(value: any): any {
    const value_ = JSON.stringify(value);
    if (value_ === '1') {
      return 'FA';
    } else if (value_ === '2') {
      return 'EN';
    } else {
      return value_;
    }
  }
}
