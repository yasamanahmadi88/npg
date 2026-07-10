import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { UndifinedStatusService } from '../service/undifined-status.service';
import { UndifinedStatusDeleteDialogComponent } from './undifined-status-delete-dialog.component';

describe('Component Tests', () => {
  describe('UndifinedStatusDeleteDialogComponent', () => {
    let comp: UndifinedStatusDeleteDialogComponent;
    let fixture: ComponentFixture<UndifinedStatusDeleteDialogComponent>;
    let service: UndifinedStatusService;
    let mockActiveModal: { close: jest.Mock; dismiss: jest.Mock };

    beforeEach(() => {
      mockActiveModal = {
        close: jest.fn(),
        dismiss: jest.fn(),
      };

      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UndifinedStatusDeleteDialogComponent],
        providers: [{ provide: NgbActiveModal, useValue: mockActiveModal }],
      })
        .overrideTemplate(UndifinedStatusDeleteDialogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UndifinedStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(UndifinedStatusService);
    });

    it('Should call delete service on confirmDelete', () => {
      // GIVEN
      const entityId = 123;
      jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>({})));

      // WHEN
      comp.confirmDelete(entityId as never);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(entityId);
      expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
    });

    it('Should not call delete service on cancel', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});