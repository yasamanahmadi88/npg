import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimeFrameDetailComponent } from './time-frame-detail.component';

describe('Component Tests', () => {
  describe('TimeFrame Management Detail Component', () => {
    let comp: TimeFrameDetailComponent;
    let fixture: ComponentFixture<TimeFrameDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TimeFrameDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ timeFrame: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TimeFrameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TimeFrameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load timeFrame on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.timeFrame).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
