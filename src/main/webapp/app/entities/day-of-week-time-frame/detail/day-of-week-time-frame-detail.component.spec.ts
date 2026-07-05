import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DayOfWeekTimeFrameDetailComponent } from './day-of-week-time-frame-detail.component';

describe('Component Tests', () => {
  describe('DayOfWeekTimeFrame Management Detail Component', () => {
    let comp: DayOfWeekTimeFrameDetailComponent;
    let fixture: ComponentFixture<DayOfWeekTimeFrameDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DayOfWeekTimeFrameDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dayOfWeekTimeFrame: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DayOfWeekTimeFrameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DayOfWeekTimeFrameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dayOfWeekTimeFrame on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dayOfWeekTimeFrame).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
