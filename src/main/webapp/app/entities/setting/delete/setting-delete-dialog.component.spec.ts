import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { SettingService } from '../service/setting.service';
import { SettingDeleteDialogComponent } from './setting-delete-dialog.component';

describe('Component Tests', () => {
  describe('SettingDeleteDialogComponent', () => {
    let comp: SettingDeleteDialogComponent;
    let fixture: ComponentFixture<SettingDeleteDialogComponent>;
    let service: SettingService;
    let mockActiveModal: { close: jest.Mock; dismiss: jest.Mock };

    beforeEach(() => {
      mockActiveModal = {
        close: jest.fn(),
        dismiss: jest.fn(),
      };

      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SettingDeleteDialogComponent],
        providers: [{ provide: NgbActiveModal, useValue: mockActiveModal }],
      })
        .overrideTemplate(SettingDeleteDialogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SettingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SettingService);
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