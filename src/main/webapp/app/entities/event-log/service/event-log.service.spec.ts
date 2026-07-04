import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEventLog, EventLog } from '../event-log.model';

import { EventLogService } from './event-log.service';

describe('Service Tests', () => {
  describe('EventLog Service', () => {
    let service: EventLogService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventLog;
    let expectedResult: IEventLog | IEventLog[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EventLogService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        conversationId: 'AAAAAAA',
        sender: 'AAAAAAA',
        receiver: 'AAAAAAA',
        message: 'AAAAAAA',
        requestBody: 'AAAAAAA',
        responseBody: 'AAAAAAA',
        flg0Ordinary1Exception: 0,
        eventSource: 'AAAAAAA',
        exceptionBody: 'AAAAAAA',
        httpStatus: 'AAAAAAA',
        insertTimestamp: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EventLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.create(new EventLog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EventLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            conversationId: 'BBBBBB',
            sender: 'BBBBBB',
            receiver: 'BBBBBB',
            message: 'BBBBBB',
            requestBody: 'BBBBBB',
            responseBody: 'BBBBBB',
            flg0Ordinary1Exception: 1,
            eventSource: 'BBBBBB',
            exceptionBody: 'BBBBBB',
            httpStatus: 'BBBBBB',
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EventLog', () => {
        const patchObject = Object.assign(
          {
            receiver: 'BBBBBB',
            requestBody: 'BBBBBB',
            responseBody: 'BBBBBB',
            flg0Ordinary1Exception: 1,
            exceptionBody: 'BBBBBB',
            httpStatus: 'BBBBBB',
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          new EventLog()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            insertTimestamp: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EventLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            conversationId: 'BBBBBB',
            sender: 'BBBBBB',
            receiver: 'BBBBBB',
            message: 'BBBBBB',
            requestBody: 'BBBBBB',
            responseBody: 'BBBBBB',
            flg0Ordinary1Exception: 1,
            eventSource: 'BBBBBB',
            exceptionBody: 'BBBBBB',
            httpStatus: 'BBBBBB',
            insertTimestamp: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a EventLog', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEventLogToCollectionIfMissing', () => {
        it('should add a EventLog to an empty array', () => {
          const eventLog: IEventLog = { id: 123 };
          expectedResult = service.addEventLogToCollectionIfMissing([], eventLog);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eventLog);
        });

        it('should not add a EventLog to an array that contains it', () => {
          const eventLog: IEventLog = { id: 123 };
          const eventLogCollection: IEventLog[] = [
            {
              ...eventLog,
            },
            { id: 456 },
          ];
          expectedResult = service.addEventLogToCollectionIfMissing(eventLogCollection, eventLog);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EventLog to an array that doesn't contain it", () => {
          const eventLog: IEventLog = { id: 123 };
          const eventLogCollection: IEventLog[] = [{ id: 456 }];
          expectedResult = service.addEventLogToCollectionIfMissing(eventLogCollection, eventLog);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eventLog);
        });

        it('should add only unique EventLog to an array', () => {
          const eventLogArray: IEventLog[] = [{ id: 123 }, { id: 456 }, { id: 42956 }];
          const eventLogCollection: IEventLog[] = [{ id: 123 }];
          expectedResult = service.addEventLogToCollectionIfMissing(eventLogCollection, ...eventLogArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const eventLog: IEventLog = { id: 123 };
          const eventLog2: IEventLog = { id: 456 };
          expectedResult = service.addEventLogToCollectionIfMissing([], eventLog, eventLog2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eventLog);
          expect(expectedResult).toContain(eventLog2);
        });

        it('should accept null and undefined values', () => {
          const eventLog: IEventLog = { id: 123 };
          expectedResult = service.addEventLogToCollectionIfMissing([], null, eventLog, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eventLog);
        });

        it('should return initial array if no EventLog is added', () => {
          const eventLogCollection: IEventLog[] = [{ id: 123 }];
          expectedResult = service.addEventLogToCollectionIfMissing(eventLogCollection, undefined, null);
          expect(expectedResult).toEqual(eventLogCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
