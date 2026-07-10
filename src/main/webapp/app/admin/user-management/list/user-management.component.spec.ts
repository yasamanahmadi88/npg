import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { UserManagementService } from '../service/user-management.service';
import { User } from '../user-management.model';

import { UserManagementComponent } from './user-management.component';

describe('Component Tests', () => {
  describe('User Management Component', () => {
    let comp: UserManagementComponent;
    let fixture: ComponentFixture<UserManagementComponent>;
    let service: UserManagementService;

    const data = of({
      defaultSort: 'id,asc',
    });

    const queryParamMap = of(
      convertToParamMap({
        page: '1',
        size: '1',
        sort: 'id,desc',
      })
    );

    const mockAccountService = {
      identity: jest.fn(() => of(null)),
    };

    const mockRouter = {
      navigate: jest.fn(),
    };

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          declarations: [UserManagementComponent],
          providers: [
            { provide: Router, useValue: mockRouter },
            { provide: ActivatedRoute, useValue: { data, queryParamMap } },
            { provide: AccountService, useValue: mockAccountService },
          ],
        })
          .overrideTemplate(UserManagementComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(UserManagementService);
      mockRouter.navigate.mockClear();
      mockAccountService.identity.mockClear();
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [new User(123)],
              headers,
            })
          )
        );

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.users?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });

    describe('setActive', () => {
      it('Should update user and call load all', () => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        const user = new User(123);
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [user],
              headers,
            })
          )
        );
        jest.spyOn(service, 'update').mockReturnValue(of(user));

        // WHEN
        comp.setActive(user, true);

        // THEN
        expect(service.update).toHaveBeenCalledWith({ ...user, activated: true });
        expect(service.query).toHaveBeenCalled();
        expect(comp.users?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});