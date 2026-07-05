import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortabilityDetailComponent } from './portability-detail.component';

describe('Component Tests', () => {
  describe('Portability Management Detail Component', () => {
    let comp: PortabilityDetailComponent;
    let fixture: ComponentFixture<PortabilityDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PortabilityDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ portability: [{ id: 123 }] }) },
          },
        ],
      })
        .overrideTemplate(PortabilityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PortabilityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load portability on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.portability).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
