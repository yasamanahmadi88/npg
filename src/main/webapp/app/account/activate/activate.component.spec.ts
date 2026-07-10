import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

import { ActivateService } from './activate.service';
import { ActivateComponent } from './activate.component';

describe('Component Tests', () => {
  describe('ActivateComponent', () => {
    let comp: ActivateComponent;
    let fixture: ComponentFixture<ActivateComponent>;
    let service: ActivateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ActivateComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              queryParams: of({ key: 'ABC123' }),
            },
          },
        ],
      })
        .overrideTemplate(ActivateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ActivateService);
    });

    it('calls activate.get with the key from params', () => {
      // GIVEN
      jest.spyOn(service, 'get').mockReturnValue(of({}));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.get).toHaveBeenCalledWith('ABC123');
    });

    it('should set success to true upon successful activation', () => {
      // GIVEN
      jest.spyOn(service, 'get').mockReturnValue(of({}));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.error).toBe(false);
      expect(comp.success).toBe(true);
    });

    it('should set error to true upon activation failure', () => {
      // GIVEN
      jest.spyOn(service, 'get').mockReturnValue(throwError(() => new Error('ERROR')));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.error).toBe(true);
      expect(comp.success).toBe(false);
    });
  });
});