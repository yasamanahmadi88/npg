import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotificationTemplateDetailComponent } from './notification-template-detail.component';

describe('Component Tests', () => {
  describe('NotificationTemplate Management Detail Component', () => {
    let comp: NotificationTemplateDetailComponent;
    let fixture: ComponentFixture<NotificationTemplateDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NotificationTemplateDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ notificationTemplate: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NotificationTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificationTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notificationTemplate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificationTemplate).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
