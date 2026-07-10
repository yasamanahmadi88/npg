import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UndifinedStatusDetailComponent } from './undifined-status-detail.component';

describe('Component Tests', () => {
  describe('UndifinedStatus Management Detail Component', () => {
    let comp: UndifinedStatusDetailComponent;
    let fixture: ComponentFixture<UndifinedStatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [UndifinedStatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ undifinedStatus: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(UndifinedStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UndifinedStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load undifinedStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.undifinedStatus).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
