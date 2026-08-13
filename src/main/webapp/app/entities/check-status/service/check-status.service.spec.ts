import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckStatus, CheckStatus } from '../check-status.model';

import { CheckStatusService } from './check-status.service';

describe('Service Tests', () => {
  describe('CheckStatus Service', () => {
    let service: CheckStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckStatus;
    let expectedResult: ICheckStatus | ICheckStatus[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckStatusService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        porStatus: 'AAAAAAA',
        porTechStatus: 'AAAAAAA',
        porErrCode: 'AAAAAAA',
        porRspCode: 'AAAAAAA',
        statusMessageFa: 'AAAAAAA',
        statusMessageEn: 'AAAAAAA',
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

      it('should create a CheckStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CheckStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            porStatus: 'BBBBBB',
            porTechStatus: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porRspCode: 'BBBBBB',
            statusMessageFa: 'BBBBBB',
            statusMessageEn: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CheckStatus', () => {
        const patchObject = Object.assign(
          {
            porTechStatus: 'BBBBBB',
            porRspCode: 'BBBBBB',
            statusMessageFa: 'BBBBBB',
          },
          new CheckStatus()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CheckStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            porStatus: 'BBBBBB',
            porTechStatus: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porRspCode: 'BBBBBB',
            statusMessageFa: 'BBBBBB',
            statusMessageEn: 'BBBBBB',
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

      it('should delete a CheckStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckStatusToCollectionIfMissing', () => {
        it('should add a CheckStatus to an empty array', () => {
          const checkStatus: ICheckStatus = { id: 123 };
          expectedResult = service.addCheckStatusToCollectionIfMissing([], checkStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkStatus);
        });

        it('should not add a CheckStatus to an array that contains it', () => {
          const checkStatus: ICheckStatus = { id: 123 };
          const checkStatusCollection: ICheckStatus[] = [
            {
              ...checkStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckStatusToCollectionIfMissing(checkStatusCollection, checkStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CheckStatus to an array that doesn't contain it", () => {
          const checkStatus: ICheckStatus = { id: 123 };
          const checkStatusCollection: ICheckStatus[] = [{ id: 456 }];
          expectedResult = service.addCheckStatusToCollectionIfMissing(checkStatusCollection, checkStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkStatus);
        });

        it('should add only unique CheckStatus to an array', () => {
          const checkStatusArray: ICheckStatus[] = [{ id: 123 }, { id: 456 }, { id: 39620 }];
          const checkStatusCollection: ICheckStatus[] = [{ id: 123 }];
          expectedResult = service.addCheckStatusToCollectionIfMissing(checkStatusCollection, ...checkStatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkStatus: ICheckStatus = { id: 123 };
          const checkStatus2: ICheckStatus = { id: 456 };
          expectedResult = service.addCheckStatusToCollectionIfMissing([], checkStatus, checkStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkStatus);
          expect(expectedResult).toContain(checkStatus2);
        });

        it('should accept null and undefined values', () => {
          const checkStatus: ICheckStatus = { id: 123 };
          expectedResult = service.addCheckStatusToCollectionIfMissing([], null, checkStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkStatus);
        });

        it('should return initial array if no CheckStatus is added', () => {
          const checkStatusCollection: ICheckStatus[] = [{ id: 123 }];
          expectedResult = service.addCheckStatusToCollectionIfMissing(checkStatusCollection, undefined, null);
          expect(expectedResult).toEqual(checkStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
