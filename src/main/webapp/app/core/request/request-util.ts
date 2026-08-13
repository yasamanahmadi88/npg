// import { HttpParams } from '@angular/common/http';
//
// export const createRequestOption = (req?: any): HttpParams => {
//   let options: HttpParams = new HttpParams();
//
//   if (req) {
//     Object.keys(req).forEach(key => {
//       if (key !== 'sort') {
//         options = options.set(key, req[key]);
//       }
//     });
//
//     if (req.sort) {
//       req.sort.forEach((val: string) => {
//         options = options.append('sort', val);
//       });
//     }
//   }
//
//   return options;
// };

import { HttpParams } from '@angular/common/http';

/**
 * Build query params for JHipster list APIs.
 * Skips blank/null/invalid values so empty search fields never become
 * `reportName.equals=` (which would match zero rows).
 */
export const createRequestOption = (req?: any): HttpParams => {
  let options: HttpParams = new HttpParams();

  if (req) {
    Object.keys(req).forEach(key => {
      if (key === 'sort') {
        return;
      }
      const raw = req[key];
      if (!isUsableQueryValue(raw)) {
        return;
      }
      options = options.set(key, String(raw));
    });

    if (Array.isArray(req.sort)) {
      req.sort.forEach((val: string) => {
        if (isUsableQueryValue(val)) {
          options = options.append('sort', String(val));
        }
      });
    }
  }

  return options;
};

function isUsableQueryValue(value: unknown): boolean {
  if (value === null || value === undefined) {
    return false;
  }
  if (typeof value === 'string') {
    const trimmed = value.trim();
    return trimmed.length > 0 && trimmed.toLowerCase() !== 'invalid date';
  }
  if (typeof value === 'number') {
    return !Number.isNaN(value);
  }
  if (typeof value === 'boolean') {
    return true;
  }
  // Reject plain objects (e.g. broken datepicker values) that would serialize as [object Object]
  if (typeof value === 'object') {
    return false;
  }
  return true;
}
