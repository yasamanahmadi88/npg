import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPortabilityLog, PortabilityLog } from '../portability-log.model';

import { PortabilityLogService } from './portability-log.service';

describe('Service Tests', () => {
  describe('PortabilityLog Service', () => {
    let service: PortabilityLogService;
    let httpMock: HttpTestingController;
    let elemDefault: IPortabilityLog;
    let expectedResult: IPortabilityLog | IPortabilityLog[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PortabilityLogService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        porId: 0,
        porRequestId: 'AAAAAAA',
        porNumber: 'AAAAAAA',
        porLegalTerm: 0,
        porOpr: 'AAAAAAA',
        porAccType: 'AAAAAAA',
        porIdNumber: 'AAAAAAA',
        porContactNumber: 'AAAAAAA',
        porStatus: 'AAAAAAA',
        porPortedDate: currentDate,
        porRouting: 'AAAAAAA',
        porType: 'AAAAAAA',
        porOpOrg: 'AAAAAAA',
        porRspCode: 'AAAAAAA',
        porRspNote: 'AAAAAAA',
        porCancelNote: 'AAAAAAA',
        porMnpid: 'AAAAAAA',
        portationDate: currentDate,
        portaCode: 'AAAAAAA',
        mvno: 'AAAAAAA',
        context: 'AAAAAAA',
        porErrCode: 'AAAAAAA',
        porErrMessage: 'AAAAAAA',
        porOpd: 'AAAAAAA',
        porNumType: 'AAAAAAA',
        porNote: 'AAAAAAA',
        porDeadline: currentDate,
        porResponseTimestamp: 0,
        porEligible: 0,
        porBillingOk: 0,
        intermediaryActionState: 'AAAAAAA',
        porCrDate: currentDate,
        porUpdDate: currentDate,
        porTechStatus: 'AAAAAAA',
        porTechDeadline: currentDate,
        refPorId: 0,
        needManualRetry: 0,
        retryCount: 0,
        action: 'AAAAAAA',
        request: 'AAAAAAA',
        insertTimestamp: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            portationDate: currentDate.format(DATE_TIME_FORMAT),
            porDeadline: currentDate.format(DATE_TIME_FORMAT),
            porCrDate: currentDate.format(DATE_TIME_FORMAT),
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechDeadline: currentDate.format(DATE_TIME_FORMAT),
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PortabilityLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            portationDate: currentDate.format(DATE_TIME_FORMAT),
            porDeadline: currentDate.format(DATE_TIME_FORMAT),
            porCrDate: currentDate.format(DATE_TIME_FORMAT),
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechDeadline: currentDate.format(DATE_TIME_FORMAT),
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            porPortedDate: currentDate,
            portationDate: currentDate,
            porDeadline: currentDate,
            porCrDate: currentDate,
            porUpdDate: currentDate,
            porTechDeadline: currentDate,
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.create(new PortabilityLog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PortabilityLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            porId: 1,
            porRequestId: 'BBBBBB',
            porNumber: 'BBBBBB',
            porLegalTerm: 1,
            porOpr: 'BBBBBB',
            porAccType: 'BBBBBB',
            porIdNumber: 'BBBBBB',
            porContactNumber: 'BBBBBB',
            porStatus: 'BBBBBB',
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            porRouting: 'BBBBBB',
            porType: 'BBBBBB',
            porOpOrg: 'BBBBBB',
            porRspCode: 'BBBBBB',
            porRspNote: 'BBBBBB',
            porCancelNote: 'BBBBBB',
            porMnpid: 'BBBBBB',
            portationDate: currentDate.format(DATE_TIME_FORMAT),
            portaCode: 'BBBBBB',
            mvno: 'BBBBBB',
            context: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porErrMessage: 'BBBBBB',
            porOpd: 'BBBBBB',
            porNumType: 'BBBBBB',
            porNote: 'BBBBBB',
            porDeadline: currentDate.format(DATE_TIME_FORMAT),
            porResponseTimestamp: 1,
            porEligible: 1,
            porBillingOk: 1,
            intermediaryActionState: 'BBBBBB',
            porCrDate: currentDate.format(DATE_TIME_FORMAT),
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechStatus: 'BBBBBB',
            porTechDeadline: currentDate.format(DATE_TIME_FORMAT),
            refPorId: 1,
            needManualRetry: 1,
            retryCount: 1,
            action: 'BBBBBB',
            request: 'BBBBBB',
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            porPortedDate: currentDate,
            portationDate: currentDate,
            porDeadline: currentDate,
            porCrDate: currentDate,
            porUpdDate: currentDate,
            porTechDeadline: currentDate,
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PortabilityLog', () => {
        const patchObject = Object.assign(
          {
            porId: 1,
            porRequestId: 'BBBBBB',
            porIdNumber: 'BBBBBB',
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            porRouting: 'BBBBBB',
            porType: 'BBBBBB',
            porOpOrg: 'BBBBBB',
            porRspCode: 'BBBBBB',
            porMnpid: 'BBBBBB',
            portaCode: 'BBBBBB',
            mvno: 'BBBBBB',
            context: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porDeadline: currentDate.format(DATE_TIME_FORMAT),
            intermediaryActionState: 'BBBBBB',
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechStatus: 'BBBBBB',
            refPorId: 1,
            action: 'BBBBBB',
          },
          new PortabilityLog()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            porPortedDate: currentDate,
            portationDate: currentDate,
            porDeadline: currentDate,
            porCrDate: currentDate,
            porUpdDate: currentDate,
            porTechDeadline: currentDate,
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PortabilityLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            porId: 1,
            porRequestId: 'BBBBBB',
            porNumber: 'BBBBBB',
            porLegalTerm: 1,
            porOpr: 'BBBBBB',
            porAccType: 'BBBBBB',
            porIdNumber: 'BBBBBB',
            porContactNumber: 'BBBBBB',
            porStatus: 'BBBBBB',
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            porRouting: 'BBBBBB',
            porType: 'BBBBBB',
            porOpOrg: 'BBBBBB',
            porRspCode: 'BBBBBB',
            porRspNote: 'BBBBBB',
            porCancelNote: 'BBBBBB',
            porMnpid: 'BBBBBB',
            portationDate: currentDate.format(DATE_TIME_FORMAT),
            portaCode: 'BBBBBB',
            mvno: 'BBBBBB',
            context: 'BBBBBB',
            porErrCode: 'BBBBBB',
            porErrMessage: 'BBBBBB',
            porOpd: 'BBBBBB',
            porNumType: 'BBBBBB',
            porNote: 'BBBBBB',
            porDeadline: currentDate.format(DATE_TIME_FORMAT),
            porResponseTimestamp: 1,
            porEligible: 1,
            porBillingOk: 1,
            intermediaryActionState: 'BBBBBB',
            porCrDate: currentDate.format(DATE_TIME_FORMAT),
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechStatus: 'BBBBBB',
            porTechDeadline: currentDate.format(DATE_TIME_FORMAT),
            refPorId: 1,
            needManualRetry: 1,
            retryCount: 1,
            action: 'BBBBBB',
            request: 'BBBBBB',
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            porPortedDate: currentDate,
            portationDate: currentDate,
            porDeadline: currentDate,
            porCrDate: currentDate,
            porUpdDate: currentDate,
            porTechDeadline: currentDate,
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PortabilityLog', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPortabilityLogToCollectionIfMissing', () => {
        it('should add a PortabilityLog to an empty array', () => {
          const portabilityLog: IPortabilityLog = { id: 123 };
          expectedResult = service.addPortabilityLogToCollectionIfMissing([], portabilityLog);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(portabilityLog);
        });

        it('should not add a PortabilityLog to an array that contains it', () => {
          const portabilityLog: IPortabilityLog = { id: 123 };
          const portabilityLogCollection: IPortabilityLog[] = [
            {
              ...portabilityLog,
            },
            { id: 456 },
          ];
          expectedResult = service.addPortabilityLogToCollectionIfMissing(portabilityLogCollection, portabilityLog);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PortabilityLog to an array that doesn't contain it", () => {
          const portabilityLog: IPortabilityLog = { id: 123 };
          const portabilityLogCollection: IPortabilityLog[] = [{ id: 456 }];
          expectedResult = service.addPortabilityLogToCollectionIfMissing(portabilityLogCollection, portabilityLog);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(portabilityLog);
        });

        it('should add only unique PortabilityLog to an array', () => {
          const portabilityLogArray: IPortabilityLog[] = [{ id: 123 }, { id: 456 }, { id: 13329 }];
          const portabilityLogCollection: IPortabilityLog[] = [{ id: 123 }];
          expectedResult = service.addPortabilityLogToCollectionIfMissing(portabilityLogCollection, ...portabilityLogArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const portabilityLog: IPortabilityLog = { id: 123 };
          const portabilityLog2: IPortabilityLog = { id: 456 };
          expectedResult = service.addPortabilityLogToCollectionIfMissing([], portabilityLog, portabilityLog2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(portabilityLog);
          expect(expectedResult).toContain(portabilityLog2);
        });

        it('should accept null and undefined values', () => {
          const portabilityLog: IPortabilityLog = { id: 123 };
          expectedResult = service.addPortabilityLogToCollectionIfMissing([], null, portabilityLog, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(portabilityLog);
        });

        it('should return initial array if no PortabilityLog is added', () => {
          const portabilityLogCollection: IPortabilityLog[] = [{ id: 123 }];
          expectedResult = service.addPortabilityLogToCollectionIfMissing(portabilityLogCollection, undefined, null);
          expect(expectedResult).toEqual(portabilityLogCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
