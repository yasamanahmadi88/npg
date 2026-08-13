import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUndifinedStatus, UndifinedStatus } from '../undifined-status.model';

import { UndifinedStatusService } from './undifined-status.service';

describe('Service Tests', () => {
  describe('UndifinedStatus Service', () => {
    let service: UndifinedStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IUndifinedStatus;
    let expectedResult: IUndifinedStatus | IUndifinedStatus[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(UndifinedStatusService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        porStatus: 'AAAAAAA',
        porTechStatus: 'AAAAAAA',
        porErrCode: 'AAAAAAA',
        porRspCode: 'AAAAAAA',
        porRequestId: 'AAAAAAA',
        insertDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            insertDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UndifinedStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            insertDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertDate: currentDate,
          },
          returnedFromService
        );

        service.create(new UndifinedStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UndifinedStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            porStatus: 'BBBBBB',
            porTechStatus: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porRspCode: 'BBBBBB',
            porRequestId: 'BBBBBB',
            insertDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a UndifinedStatus', () => {
        const patchObject = Object.assign(
          {
            porStatus: 'BBBBBB',
            porTechStatus: 'BBBBBB',
            porErrCode: 'BBBBBB',
          },
          new UndifinedStatus()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            insertDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UndifinedStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            porStatus: 'BBBBBB',
            porTechStatus: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porRspCode: 'BBBBBB',
            porRequestId: 'BBBBBB',
            insertDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UndifinedStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addUndifinedStatusToCollectionIfMissing', () => {
        it('should add a UndifinedStatus to an empty array', () => {
          const undifinedStatus: IUndifinedStatus = { id: 123 };
          expectedResult = service.addUndifinedStatusToCollectionIfMissing([], undifinedStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(undifinedStatus);
        });

        it('should not add a UndifinedStatus to an array that contains it', () => {
          const undifinedStatus: IUndifinedStatus = { id: 123 };
          const undifinedStatusCollection: IUndifinedStatus[] = [
            {
              ...undifinedStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addUndifinedStatusToCollectionIfMissing(undifinedStatusCollection, undifinedStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a UndifinedStatus to an array that doesn't contain it", () => {
          const undifinedStatus: IUndifinedStatus = { id: 123 };
          const undifinedStatusCollection: IUndifinedStatus[] = [{ id: 456 }];
          expectedResult = service.addUndifinedStatusToCollectionIfMissing(undifinedStatusCollection, undifinedStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(undifinedStatus);
        });

        it('should add only unique UndifinedStatus to an array', () => {
          const undifinedStatusArray: IUndifinedStatus[] = [{ id: 123 }, { id: 456 }, { id: 46601 }];
          const undifinedStatusCollection: IUndifinedStatus[] = [{ id: 123 }];
          expectedResult = service.addUndifinedStatusToCollectionIfMissing(undifinedStatusCollection, ...undifinedStatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const undifinedStatus: IUndifinedStatus = { id: 123 };
          const undifinedStatus2: IUndifinedStatus = { id: 456 };
          expectedResult = service.addUndifinedStatusToCollectionIfMissing([], undifinedStatus, undifinedStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(undifinedStatus);
          expect(expectedResult).toContain(undifinedStatus2);
        });

        it('should accept null and undefined values', () => {
          const undifinedStatus: IUndifinedStatus = { id: 123 };
          expectedResult = service.addUndifinedStatusToCollectionIfMissing([], null, undifinedStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(undifinedStatus);
        });

        it('should return initial array if no UndifinedStatus is added', () => {
          const undifinedStatusCollection: IUndifinedStatus[] = [{ id: 123 }];
          expectedResult = service.addUndifinedStatusToCollectionIfMissing(undifinedStatusCollection, undefined, null);
          expect(expectedResult).toEqual(undifinedStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
