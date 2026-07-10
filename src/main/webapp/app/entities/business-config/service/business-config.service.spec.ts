import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBusinessConfig, BusinessConfig } from '../business-config.model';

import { BusinessConfigService } from './business-config.service';

describe('Service Tests', () => {
  describe('BusinessConfig Service', () => {
    let service: BusinessConfigService;
    let httpMock: HttpTestingController;
    let elemDefault: IBusinessConfig;
    let expectedResult: IBusinessConfig | IBusinessConfig[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BusinessConfigService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: '0',
        value: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BusinessConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: '0',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BusinessConfig()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BusinessConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            value: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a BusinessConfig', () => {
        const patchObject = Object.assign({}, new BusinessConfig());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BusinessConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            value: 'BBBBBB',
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

      it('should delete a BusinessConfig', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBusinessConfigToCollectionIfMissing', () => {
        it('should add a BusinessConfig to an empty array', () => {
          const businessConfig: IBusinessConfig = { id: '123' };
          expectedResult = service.addBusinessConfigToCollectionIfMissing([], businessConfig);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(businessConfig);
        });

        it('should not add a BusinessConfig to an array that contains it', () => {
          const businessConfig: IBusinessConfig = { id: '123' };
          const businessConfigCollection: IBusinessConfig[] = [
            {
              ...businessConfig,
            },
            { id: '456' },
          ];
          expectedResult = service.addBusinessConfigToCollectionIfMissing(businessConfigCollection, businessConfig);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a BusinessConfig to an array that doesn't contain it", () => {
          const businessConfig: IBusinessConfig = { id: '123' };
          const businessConfigCollection: IBusinessConfig[] = [{ id: '456' }];
          expectedResult = service.addBusinessConfigToCollectionIfMissing(businessConfigCollection, businessConfig);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(businessConfig);
        });

        it('should add only unique BusinessConfig to an array', () => {
          const businessConfigArray: IBusinessConfig[] = [{ id: '123' }, { id: '456' }, { id: '72129' }];
          const businessConfigCollection: IBusinessConfig[] = [{ id: '123' }];
          expectedResult = service.addBusinessConfigToCollectionIfMissing(businessConfigCollection, ...businessConfigArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const businessConfig: IBusinessConfig = { id: '123' };
          const businessConfig2: IBusinessConfig = { id: '456' };
          expectedResult = service.addBusinessConfigToCollectionIfMissing([], businessConfig, businessConfig2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(businessConfig);
          expect(expectedResult).toContain(businessConfig2);
        });

        it('should accept null and undefined values', () => {
          const businessConfig: IBusinessConfig = { id: '123' };
          expectedResult = service.addBusinessConfigToCollectionIfMissing([], null, businessConfig, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(businessConfig);
        });

        it('should return initial array if no BusinessConfig is added', () => {
          const businessConfigCollection: IBusinessConfig[] = [{ id: '123' }];
          expectedResult = service.addBusinessConfigToCollectionIfMissing(businessConfigCollection, undefined, null);
          expect(expectedResult).toEqual(businessConfigCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
