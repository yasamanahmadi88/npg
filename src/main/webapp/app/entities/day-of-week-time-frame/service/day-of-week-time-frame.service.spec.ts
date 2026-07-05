import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDayOfWeekTimeFrame, DayOfWeekTimeFrame } from '../day-of-week-time-frame.model';

import { DayOfWeekTimeFrameService } from './day-of-week-time-frame.service';

describe('Service Tests', () => {
  describe('DayOfWeekTimeFrame Service', () => {
    let service: DayOfWeekTimeFrameService;
    let httpMock: HttpTestingController;
    let elemDefault: IDayOfWeekTimeFrame;
    let expectedResult: IDayOfWeekTimeFrame | IDayOfWeekTimeFrame[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DayOfWeekTimeFrameService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        day: 0,
        begin: 0,
        end: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DayOfWeekTimeFrame', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DayOfWeekTimeFrame()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DayOfWeekTimeFrame', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            day: 1,
            begin: 1,
            end: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DayOfWeekTimeFrame', () => {
        const patchObject = Object.assign({}, new DayOfWeekTimeFrame());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DayOfWeekTimeFrame', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            day: 1,
            begin: 1,
            end: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DayOfWeekTimeFrame', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDayOfWeekTimeFrameToCollectionIfMissing', () => {
        it('should add a DayOfWeekTimeFrame to an empty array', () => {
          const dayOfWeekTimeFrame: IDayOfWeekTimeFrame = { id: 123 };
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing([], dayOfWeekTimeFrame);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dayOfWeekTimeFrame);
        });

        it('should not add a DayOfWeekTimeFrame to an array that contains it', () => {
          const dayOfWeekTimeFrame: IDayOfWeekTimeFrame = { id: 123 };
          const dayOfWeekTimeFrameCollection: IDayOfWeekTimeFrame[] = [
            {
              ...dayOfWeekTimeFrame,
            },
            { id: 456 },
          ];
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing(dayOfWeekTimeFrameCollection, dayOfWeekTimeFrame);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DayOfWeekTimeFrame to an array that doesn't contain it", () => {
          const dayOfWeekTimeFrame: IDayOfWeekTimeFrame = { id: 123 };
          const dayOfWeekTimeFrameCollection: IDayOfWeekTimeFrame[] = [{ id: 456 }];
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing(dayOfWeekTimeFrameCollection, dayOfWeekTimeFrame);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dayOfWeekTimeFrame);
        });

        it('should add only unique DayOfWeekTimeFrame to an array', () => {
          const dayOfWeekTimeFrameArray: IDayOfWeekTimeFrame[] = [{ id: 123 }, { id: 456 }, { id: 19700 }];
          const dayOfWeekTimeFrameCollection: IDayOfWeekTimeFrame[] = [{ id: 123 }];
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing(dayOfWeekTimeFrameCollection, ...dayOfWeekTimeFrameArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dayOfWeekTimeFrame: IDayOfWeekTimeFrame = { id: 123 };
          const dayOfWeekTimeFrame2: IDayOfWeekTimeFrame = { id: 456 };
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing([], dayOfWeekTimeFrame, dayOfWeekTimeFrame2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dayOfWeekTimeFrame);
          expect(expectedResult).toContain(dayOfWeekTimeFrame2);
        });

        it('should accept null and undefined values', () => {
          const dayOfWeekTimeFrame: IDayOfWeekTimeFrame = { id: 123 };
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing([], null, dayOfWeekTimeFrame, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dayOfWeekTimeFrame);
        });

        it('should return initial array if no DayOfWeekTimeFrame is added', () => {
          const dayOfWeekTimeFrameCollection: IDayOfWeekTimeFrame[] = [{ id: 123 }];
          expectedResult = service.addDayOfWeekTimeFrameToCollectionIfMissing(dayOfWeekTimeFrameCollection, undefined, null);
          expect(expectedResult).toEqual(dayOfWeekTimeFrameCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
