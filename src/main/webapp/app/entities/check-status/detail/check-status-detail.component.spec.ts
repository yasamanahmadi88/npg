import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckStatusDetailComponent } from './check-status-detail.component';

describe('Component Tests', () => {
  describe('CheckStatus Management Detail Component', () => {
    let comp: CheckStatusDetailComponent;
    let fixture: ComponentFixture<CheckStatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CheckStatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ checkStatus: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CheckStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkStatus).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
