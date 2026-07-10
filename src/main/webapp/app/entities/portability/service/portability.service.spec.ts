import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPortability, Portability } from '../portability.model';

import { PortabilityService } from './portability.service';

describe('Service Tests', () => {
  describe('Portability Service', () => {
    let service: PortabilityService;
    let httpMock: HttpTestingController;
    let elemDefault: IPortability;
    let expectedResult: IPortability | IPortability[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PortabilityService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
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
        needManualRetry: 0,
        refPorId: 0,
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
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Portability', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            portationDate: currentDate.format(DATE_TIME_FORMAT),
            porDeadline: currentDate.format(DATE_TIME_FORMAT),
            porCrDate: currentDate.format(DATE_TIME_FORMAT),
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechDeadline: currentDate.format(DATE_TIME_FORMAT),
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
          },
          returnedFromService
        );

        service.create(new Portability()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Portability', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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
            needManualRetry: 1,
            refPorId: 1,
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
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Portability', () => {
        const patchObject = Object.assign(
          {
            porRequestId: 'BBBBBB',
            porNumber: 'BBBBBB',
            porLegalTerm: 1,
            porOpr: 'BBBBBB',
            porAccType: 'BBBBBB',
            porStatus: 'BBBBBB',
            porPortedDate: currentDate.format(DATE_TIME_FORMAT),
            porRouting: 'BBBBBB',
            porType: 'BBBBBB',
            porRspCode: 'BBBBBB',
            porRspNote: 'BBBBBB',
            portationDate: currentDate.format(DATE_TIME_FORMAT),
            mvno: 'BBBBBB',
            context: 'BBBBBB',
            porOpd: 'BBBBBB',
            porNumType: 'BBBBBB',
            porBillingOk: 1,
            intermediaryActionState: 'BBBBBB',
            porUpdDate: currentDate.format(DATE_TIME_FORMAT),
            porTechStatus: 'BBBBBB',
            refPorId: 1,
          },
          new Portability()
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
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Portability', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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
            needManualRetry: 1,
            refPorId: 1,
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
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Portability', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPortabilityToCollectionIfMissing', () => {
        it('should add a Portability to an empty array', () => {
          const portability: IPortability = { id: 123 };
          expectedResult = service.addPortabilityToCollectionIfMissing([], portability);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(portability);
        });

        it('should not add a Portability to an array that contains it', () => {
          const portability: IPortability = { id: 123 };
          const portabilityCollection: IPortability[] = [
            {
              ...portability,
            },
            { id: 456 },
          ];
          expectedResult = service.addPortabilityToCollectionIfMissing(portabilityCollection, portability);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Portability to an array that doesn't contain it", () => {
          const portability: IPortability = { id: 123 };
          const portabilityCollection: IPortability[] = [{ id: 456 }];
          expectedResult = service.addPortabilityToCollectionIfMissing(portabilityCollection, portability);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(portability);
        });

        it('should add only unique Portability to an array', () => {
          const portabilityArray: IPortability[] = [{ id: 123 }, { id: 456 }, { id: 54431 }];
          const portabilityCollection: IPortability[] = [{ id: 123 }];
          expectedResult = service.addPortabilityToCollectionIfMissing(portabilityCollection, ...portabilityArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const portability: IPortability = { id: 123 };
          const portability2: IPortability = { id: 456 };
          expectedResult = service.addPortabilityToCollectionIfMissing([], portability, portability2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(portability);
          expect(expectedResult).toContain(portability2);
        });

        it('should accept null and undefined values', () => {
          const portability: IPortability = { id: 123 };
          expectedResult = service.addPortabilityToCollectionIfMissing([], null, portability, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(portability);
        });

        it('should return initial array if no Portability is added', () => {
          const portabilityCollection: IPortability[] = [{ id: 123 }];
          expectedResult = service.addPortabilityToCollectionIfMissing(portabilityCollection, undefined, null);
          expect(expectedResult).toEqual(portabilityCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
