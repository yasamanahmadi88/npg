import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'actionType',
  standalone: false,
})
export class ActionTypePipe implements PipeTransform {
  transform(value: any): any {
    if (value === 0) {
      return 'همزمان';
    } else if (value === 1) {
      return 'غیرهمزمان';
    } else if (value === 2) {
      return 'اطلاع رسانی';
    }
  }
}
