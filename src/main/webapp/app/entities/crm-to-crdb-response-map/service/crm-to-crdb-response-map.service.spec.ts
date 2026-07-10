import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICrmToCrdbResponseMap, CrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';

import { CrmToCrdbResponseMapService } from './crm-to-crdb-response-map.service';

describe('Service Tests', () => {
  describe('CrmToCrdbResponseMap Service', () => {
    let service: CrmToCrdbResponseMapService;
    let httpMock: HttpTestingController;
    let elemDefault: ICrmToCrdbResponseMap;
    let expectedResult: ICrmToCrdbResponseMap | ICrmToCrdbResponseMap[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CrmToCrdbResponseMapService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        description: 'AAAAAAA',
        crmInterface: 'AAAAAAA',
        rspCode: 'AAAAAAA',
        rspNote: 'AAAAAAA',
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

      it('should create a CrmToCrdbResponseMap', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CrmToCrdbResponseMap()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CrmToCrdbResponseMap', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            description: 'BBBBBB',
            crmInterface: 'BBBBBB',
            rspCode: 'BBBBBB',
            rspNote: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CrmToCrdbResponseMap', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            rspNote: 'BBBBBB',
          },
          new CrmToCrdbResponseMap()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CrmToCrdbResponseMap', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            description: 'BBBBBB',
            crmInterface: 'BBBBBB',
            rspCode: 'BBBBBB',
            rspNote: 'BBBBBB',
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

      it('should delete a CrmToCrdbResponseMap', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCrmToCrdbResponseMapToCollectionIfMissing', () => {
        it('should add a CrmToCrdbResponseMap to an empty array', () => {
          const crmToCrdbResponseMap: ICrmToCrdbResponseMap = { id: 123 };
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing([], crmToCrdbResponseMap);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(crmToCrdbResponseMap);
        });

        it('should not add a CrmToCrdbResponseMap to an array that contains it', () => {
          const crmToCrdbResponseMap: ICrmToCrdbResponseMap = { id: 123 };
          const crmToCrdbResponseMapCollection: ICrmToCrdbResponseMap[] = [
            {
              ...crmToCrdbResponseMap,
            },
            { id: 456 },
          ];
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing(crmToCrdbResponseMapCollection, crmToCrdbResponseMap);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CrmToCrdbResponseMap to an array that doesn't contain it", () => {
          const crmToCrdbResponseMap: ICrmToCrdbResponseMap = { id: 123 };
          const crmToCrdbResponseMapCollection: ICrmToCrdbResponseMap[] = [{ id: 456 }];
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing(crmToCrdbResponseMapCollection, crmToCrdbResponseMap);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(crmToCrdbResponseMap);
        });

        it('should add only unique CrmToCrdbResponseMap to an array', () => {
          const crmToCrdbResponseMapArray: ICrmToCrdbResponseMap[] = [{ id: 123 }, { id: 456 }, { id: 75948 }];
          const crmToCrdbResponseMapCollection: ICrmToCrdbResponseMap[] = [{ id: 123 }];
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing(
            crmToCrdbResponseMapCollection,
            ...crmToCrdbResponseMapArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const crmToCrdbResponseMap: ICrmToCrdbResponseMap = { id: 123 };
          const crmToCrdbResponseMap2: ICrmToCrdbResponseMap = { id: 456 };
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing([], crmToCrdbResponseMap, crmToCrdbResponseMap2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(crmToCrdbResponseMap);
          expect(expectedResult).toContain(crmToCrdbResponseMap2);
        });

        it('should accept null and undefined values', () => {
          const crmToCrdbResponseMap: ICrmToCrdbResponseMap = { id: 123 };
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing([], null, crmToCrdbResponseMap, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(crmToCrdbResponseMap);
        });

        it('should return initial array if no CrmToCrdbResponseMap is added', () => {
          const crmToCrdbResponseMapCollection: ICrmToCrdbResponseMap[] = [{ id: 123 }];
          expectedResult = service.addCrmToCrdbResponseMapToCollectionIfMissing(crmToCrdbResponseMapCollection, undefined, null);
          expect(expectedResult).toEqual(crmToCrdbResponseMapCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
