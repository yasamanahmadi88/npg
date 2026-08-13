import { TestBed } from '@angular/core/testing';
import dayjs from 'dayjs';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFileReportGenerationLog, FileReportGenerationLog } from '../file-report-generation-log.model';

import { FileReportGenerationLogService } from './file-report-generation-log.service';

describe('Service Tests', () => {
  describe('FileReportGenerationLog Service', () => {
    let service: FileReportGenerationLogService;
    let httpMock: HttpTestingController;
    let elemDefault: IFileReportGenerationLog;
    let expectedResult: IFileReportGenerationLog | IFileReportGenerationLog[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FileReportGenerationLogService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        reportName: 'AAAAAAA',
        reportDate: dayjs('2020-01-01'),
        fileName: 'AAAAAAA',
        rowNumber: 0,
        porNumber: 'AAAAAAA',
        content: 'AAAAAAA',
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

      it('should create a FileReportGenerationLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FileReportGenerationLog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FileReportGenerationLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            reportName: 'BBBBBB',
            reportDate: 1,
            fileName: 'BBBBBB',
            rowNumber: 1,
            porNumber: 'BBBBBB',
            content: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FileReportGenerationLog', () => {
        const patchObject = Object.assign(
          {
            reportDate: 1,
            fileName: 'BBBBBB',
            rowNumber: 1,
            porNumber: 'BBBBBB',
          },
          new FileReportGenerationLog()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FileReportGenerationLog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            reportName: 'BBBBBB',
            reportDate: 1,
            fileName: 'BBBBBB',
            rowNumber: 1,
            porNumber: 'BBBBBB',
            content: 'BBBBBB',
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

      it('should delete a FileReportGenerationLog', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFileReportGenerationLogToCollectionIfMissing', () => {
        it('should add a FileReportGenerationLog to an empty array', () => {
          const fileReportGenerationLog: IFileReportGenerationLog = { id: 123 };
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing([], fileReportGenerationLog);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fileReportGenerationLog);
        });

        it('should not add a FileReportGenerationLog to an array that contains it', () => {
          const fileReportGenerationLog: IFileReportGenerationLog = { id: 123 };
          const fileReportGenerationLogCollection: IFileReportGenerationLog[] = [
            {
              ...fileReportGenerationLog,
            },
            { id: 456 },
          ];
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing(
            fileReportGenerationLogCollection,
            fileReportGenerationLog
          );
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FileReportGenerationLog to an array that doesn't contain it", () => {
          const fileReportGenerationLog: IFileReportGenerationLog = { id: 123 };
          const fileReportGenerationLogCollection: IFileReportGenerationLog[] = [{ id: 456 }];
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing(
            fileReportGenerationLogCollection,
            fileReportGenerationLog
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fileReportGenerationLog);
        });

        it('should add only unique FileReportGenerationLog to an array', () => {
          const fileReportGenerationLogArray: IFileReportGenerationLog[] = [{ id: 123 }, { id: 456 }, { id: 22387 }];
          const fileReportGenerationLogCollection: IFileReportGenerationLog[] = [{ id: 123 }];
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing(
            fileReportGenerationLogCollection,
            ...fileReportGenerationLogArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fileReportGenerationLog: IFileReportGenerationLog = { id: 123 };
          const fileReportGenerationLog2: IFileReportGenerationLog = { id: 456 };
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing([], fileReportGenerationLog, fileReportGenerationLog2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fileReportGenerationLog);
          expect(expectedResult).toContain(fileReportGenerationLog2);
        });

        it('should accept null and undefined values', () => {
          const fileReportGenerationLog: IFileReportGenerationLog = { id: 123 };
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing([], null, fileReportGenerationLog, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fileReportGenerationLog);
        });

        it('should return initial array if no FileReportGenerationLog is added', () => {
          const fileReportGenerationLogCollection: IFileReportGenerationLog[] = [{ id: 123 }];
          expectedResult = service.addFileReportGenerationLogToCollectionIfMissing(fileReportGenerationLogCollection, undefined, null);
          expect(expectedResult).toEqual(fileReportGenerationLogCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});