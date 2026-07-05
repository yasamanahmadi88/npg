import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { UserManagementService } from '../service/user-management.service';

import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';

describe('Component Tests', () => {
  describe('User Management Delete Component', () => {
    let comp: UserManagementDeleteDialogComponent;
    let fixture: ComponentFixture<UserManagementDeleteDialogComponent>;
    let service: UserManagementService;

    const mockActiveModal = {
      close: jest.fn(),
      dismiss: jest.fn(),
    };

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          declarations: [UserManagementDeleteDialogComponent],
          providers: [{ provide: NgbActiveModal, useValue: mockActiveModal }],
        })
          .overrideTemplate(UserManagementDeleteDialogComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(UserManagementService);
      mockActiveModal.close.mockClear();
      mockActiveModal.dismiss.mockClear();
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', () => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of({}));

        // WHEN
        comp.confirmDelete('user');

        // THEN
        expect(service.delete).toHaveBeenCalledWith('user');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      });
    });
  });
});