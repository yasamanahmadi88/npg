import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OffDayDetailComponent } from './off-day-detail.component';

describe('Component Tests', () => {
  describe('OffDay Management Detail Component', () => {
    let comp: OffDayDetailComponent;
    let fixture: ComponentFixture<OffDayDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [OffDayDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ offDay: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(OffDayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OffDayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load offDay on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offDay).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
