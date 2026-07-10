import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITimeFrame, TimeFrame } from '../time-frame.model';

import { TimeFrameService } from './time-frame.service';

describe('Service Tests', () => {
  describe('TimeFrame Service', () => {
    let service: TimeFrameService;
    let httpMock: HttpTestingController;
    let elemDefault: ITimeFrame;
    let expectedResult: ITimeFrame | ITimeFrame[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TimeFrameService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        begin: 0,
        end: 0,
        offDayId: 0,
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

      it('should create a TimeFrame', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TimeFrame()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TimeFrame', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begin: 1,
            end: 1,
            offDayId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TimeFrame', () => {
        const patchObject = Object.assign(
          {
            begin: 1,
            end: 1,
          },
          new TimeFrame()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TimeFrame', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begin: 1,
            end: 1,
            offDayId: 1,
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

      it('should delete a TimeFrame', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTimeFrameToCollectionIfMissing', () => {
        it('should add a TimeFrame to an empty array', () => {
          const timeFrame: ITimeFrame = { id: 123 };
          expectedResult = service.addTimeFrameToCollectionIfMissing([], timeFrame);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(timeFrame);
        });

        it('should not add a TimeFrame to an array that contains it', () => {
          const timeFrame: ITimeFrame = { id: 123 };
          const timeFrameCollection: ITimeFrame[] = [
            {
              ...timeFrame,
            },
            { id: 456 },
          ];
          expectedResult = service.addTimeFrameToCollectionIfMissing(timeFrameCollection, timeFrame);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TimeFrame to an array that doesn't contain it", () => {
          const timeFrame: ITimeFrame = { id: 123 };
          const timeFrameCollection: ITimeFrame[] = [{ id: 456 }];
          expectedResult = service.addTimeFrameToCollectionIfMissing(timeFrameCollection, timeFrame);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(timeFrame);
        });

        it('should add only unique TimeFrame to an array', () => {
          const timeFrameArray: ITimeFrame[] = [{ id: 123 }, { id: 456 }, { id: 10857 }];
          const timeFrameCollection: ITimeFrame[] = [{ id: 123 }];
          expectedResult = service.addTimeFrameToCollectionIfMissing(timeFrameCollection, ...timeFrameArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const timeFrame: ITimeFrame = { id: 123 };
          const timeFrame2: ITimeFrame = { id: 456 };
          expectedResult = service.addTimeFrameToCollectionIfMissing([], timeFrame, timeFrame2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(timeFrame);
          expect(expectedResult).toContain(timeFrame2);
        });

        it('should accept null and undefined values', () => {
          const timeFrame: ITimeFrame = { id: 123 };
          expectedResult = service.addTimeFrameToCollectionIfMissing([], null, timeFrame, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(timeFrame);
        });

        it('should return initial array if no TimeFrame is added', () => {
          const timeFrameCollection: ITimeFrame[] = [{ id: 123 }];
          expectedResult = service.addTimeFrameToCollectionIfMissing(timeFrameCollection, undefined, null);
          expect(expectedResult).toEqual(timeFrameCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
