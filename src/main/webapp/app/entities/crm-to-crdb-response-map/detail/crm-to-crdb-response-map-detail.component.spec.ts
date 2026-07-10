import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CrmToCrdbResponseMapDetailComponent } from './crm-to-crdb-response-map-detail.component';

describe('Component Tests', () => {
  describe('CrmToCrdbResponseMap Management Detail Component', () => {
    let comp: CrmToCrdbResponseMapDetailComponent;
    let fixture: ComponentFixture<CrmToCrdbResponseMapDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CrmToCrdbResponseMapDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ crmToCrdbResponseMap: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CrmToCrdbResponseMapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CrmToCrdbResponseMapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load crmToCrdbResponseMap on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.crmToCrdbResponseMap).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
