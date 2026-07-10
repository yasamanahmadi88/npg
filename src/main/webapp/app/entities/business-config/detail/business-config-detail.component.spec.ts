import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BusinessConfigDetailComponent } from './business-config-detail.component';

describe('Component Tests', () => {
  describe('BusinessConfig Management Detail Component', () => {
    let comp: BusinessConfigDetailComponent;
    let fixture: ComponentFixture<BusinessConfigDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BusinessConfigDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ businessConfig: { id: '123' } }) },
          },
        ],
      })
        .overrideTemplate(BusinessConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load businessConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessConfig).toEqual(expect.objectContaining({ id: '123' }));
      });
    });
  });
});
