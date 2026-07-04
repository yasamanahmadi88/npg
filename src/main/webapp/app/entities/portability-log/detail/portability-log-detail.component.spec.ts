import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PortabilityLogDetailComponent } from './portability-log-detail.component';

describe('Component Tests', () => {
  describe('PortabilityLog Management Detail Component', () => {
    let comp: PortabilityLogDetailComponent;
    let fixture: ComponentFixture<PortabilityLogDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PortabilityLogDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ portabilityLog: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PortabilityLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PortabilityLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load portabilityLog on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.portabilityLog).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
