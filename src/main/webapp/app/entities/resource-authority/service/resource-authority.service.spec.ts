import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Verb } from 'app/entities/enumerations/verb.model';
import { IResourceAuthority, ResourceAuthority } from '../resource-authority.model';

import { ResourceAuthorityService } from './resource-authority.service';

describe('Service Tests', () => {
  describe('ResourceAuthority Service', () => {
    let service: ResourceAuthorityService;
    let httpMock: HttpTestingController;
    let elemDefault: IResourceAuthority;
    let expectedResult: IResourceAuthority | IResourceAuthority[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ResourceAuthorityService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        verb: Verb.NO_GRANT,
        authorityId: 0,
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

      it('should create a ResourceAuthority', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ResourceAuthority()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResourceAuthority', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            verb: 'BBBBBB',
            authorityId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ResourceAuthority', () => {
        const patchObject = Object.assign(
          {
            verb: 'BBBBBB',
          },
          new ResourceAuthority()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ResourceAuthority', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            verb: 'BBBBBB',
            authorityId: 1,
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

      it('should delete a ResourceAuthority', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addResourceAuthorityToCollectionIfMissing', () => {
        it('should add a ResourceAuthority to an empty array', () => {
          const resourceAuthority: IResourceAuthority = { id: 123 };
          expectedResult = service.addResourceAuthorityToCollectionIfMissing([], resourceAuthority);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(resourceAuthority);
        });

        it('should not add a ResourceAuthority to an array that contains it', () => {
          const resourceAuthority: IResourceAuthority = { id: 123 };
          const resourceAuthorityCollection: IResourceAuthority[] = [
            {
              ...resourceAuthority,
            },
            { id: 456 },
          ];
          expectedResult = service.addResourceAuthorityToCollectionIfMissing(resourceAuthorityCollection, resourceAuthority);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ResourceAuthority to an array that doesn't contain it", () => {
          const resourceAuthority: IResourceAuthority = { id: 123 };
          const resourceAuthorityCollection: IResourceAuthority[] = [{ id: 456 }];
          expectedResult = service.addResourceAuthorityToCollectionIfMissing(resourceAuthorityCollection, resourceAuthority);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(resourceAuthority);
        });

        it('should add only unique ResourceAuthority to an array', () => {
          const resourceAuthorityArray: IResourceAuthority[] = [{ id: 123 }, { id: 456 }, { id: 71421 }];
          const resourceAuthorityCollection: IResourceAuthority[] = [{ id: 123 }];
          expectedResult = service.addResourceAuthorityToCollectionIfMissing(resourceAuthorityCollection, ...resourceAuthorityArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const resourceAuthority: IResourceAuthority = { id: 123 };
          const resourceAuthority2: IResourceAuthority = { id: 456 };
          expectedResult = service.addResourceAuthorityToCollectionIfMissing([], resourceAuthority, resourceAuthority2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(resourceAuthority);
          expect(expectedResult).toContain(resourceAuthority2);
        });

        it('should accept null and undefined values', () => {
          const resourceAuthority: IResourceAuthority = { id: 123 };
          expectedResult = service.addResourceAuthorityToCollectionIfMissing([], null, resourceAuthority, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(resourceAuthority);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
