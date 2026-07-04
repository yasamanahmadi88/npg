import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOffDay, OffDay } from '../off-day.model';

import { OffDayService } from './off-day.service';

describe('Service Tests', () => {
  describe('OffDay Service', () => {
    let service: OffDayService;
    let httpMock: HttpTestingController;
    let elemDefault: IOffDay;
    let expectedResult: IOffDay | IOffDay[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(OffDayService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        offDate: currentDate,
        fullOff: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            offDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OffDay', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            offDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            offDate: currentDate,
          },
          returnedFromService
        );

        service.create(new OffDay()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OffDay', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            offDate: currentDate.format(DATE_TIME_FORMAT),
            fullOff: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            offDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a OffDay', () => {
        const patchObject = Object.assign(
          {
            offDate: currentDate.format(DATE_TIME_FORMAT),
          },
          new OffDay()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            offDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OffDay', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            offDate: currentDate.format(DATE_TIME_FORMAT),
            fullOff: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            offDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OffDay', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addOffDayToCollectionIfMissing', () => {
        it('should add a OffDay to an empty array', () => {
          const offDay: IOffDay = { id: 123 };
          expectedResult = service.addOffDayToCollectionIfMissing([], offDay);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(offDay);
        });

        it('should not add a OffDay to an array that contains it', () => {
          const offDay: IOffDay = { id: 123 };
          const offDayCollection: IOffDay[] = [
            {
              ...offDay,
            },
            { id: 456 },
          ];
          expectedResult = service.addOffDayToCollectionIfMissing(offDayCollection, offDay);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a OffDay to an array that doesn't contain it", () => {
          const offDay: IOffDay = { id: 123 };
          const offDayCollection: IOffDay[] = [{ id: 456 }];
          expectedResult = service.addOffDayToCollectionIfMissing(offDayCollection, offDay);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(offDay);
        });

        it('should add only unique OffDay to an array', () => {
          const offDayArray: IOffDay[] = [{ id: 123 }, { id: 456 }, { id: 95246 }];
          const offDayCollection: IOffDay[] = [{ id: 123 }];
          expectedResult = service.addOffDayToCollectionIfMissing(offDayCollection, ...offDayArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const offDay: IOffDay = { id: 123 };
          const offDay2: IOffDay = { id: 456 };
          expectedResult = service.addOffDayToCollectionIfMissing([], offDay, offDay2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(offDay);
          expect(expectedResult).toContain(offDay2);
        });

        it('should accept null and undefined values', () => {
          const offDay: IOffDay = { id: 123 };
          expectedResult = service.addOffDayToCollectionIfMissing([], null, offDay, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(offDay);
        });

        it('should return initial array if no OffDay is added', () => {
          const offDayCollection: IOffDay[] = [{ id: 123 }];
          expectedResult = service.addOffDayToCollectionIfMissing(offDayCollection, undefined, null);
          expect(expectedResult).toEqual(offDayCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
