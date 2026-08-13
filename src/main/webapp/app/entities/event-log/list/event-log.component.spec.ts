// jest.mock('@angular/router');
//
// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { HttpHeaders, HttpResponse } from '@angular/common/http';
// import { HttpClientTestingModule } from '@angular/common/http/testing';
// import { ActivatedRoute, Router } from '@angular/router';
// import { of } from 'rxjs';
//
// import { TranslateModule } from '@ngx-translate/core';
//
// import { EventLogService } from '../service/event-log.service';
//
// import { EventLogComponent } from './event-log.component';
//
// describe('Component Tests', () => {
//   describe('EventLog Management Component', () => {
//     let comp: EventLogComponent;
//     let fixture: ComponentFixture<EventLogComponent>;
//     let service: EventLogService;
//
//     beforeEach(() => {
//       TestBed.configureTestingModule({
//         imports: [HttpClientTestingModule, TranslateModule.forRoot()],
//         declarations: [EventLogComponent],
//         providers: [
//           { provide: Router, useValue: { navigate: jest.fn() } },
//           {
//             provide: ActivatedRoute,
//             useValue: {
//               data: of({
//                 eventLog: [{ id: 123 }],
//                 defaultSort: 'id,asc',
//               }),
//             },
//           },
//         ],
//       })
//         .overrideTemplate(EventLogComponent, '')
//         .compileComponents();
//
//       fixture = TestBed.createComponent(EventLogComponent);
//       comp = fixture.componentInstance;
//       service = TestBed.inject(EventLogService);
//
//       const headers = new HttpHeaders().append('link', 'link;link');
//       jest.spyOn(service, 'query').mockReturnValue(
//         of(
//           new HttpResponse({
//             body: [{ id: 123 }],
//             headers,
//           })
//         )
//       );
//     });
//
//     it('Should initialize without loading from service', () => {
//       // WHEN
//       comp.ngOnInit();
//
//       // THEN
//       expect(service.query).not.toHaveBeenCalled();
//     });
//
//     it('should load a page', () => {
//       // WHEN
//       comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);
//
//       // THEN
//       expect(service.query).toHaveBeenCalled();
//       expect(comp.eventLogs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
//     });
//
//     it('should calculate the sort attribute for an id', () => {
//       // GIVEN
//       comp.predicate = 'id';
//       comp.ascending = true;
//
//       // WHEN
//       comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);
//
//       // THEN
//       expect(service.query).toHaveBeenCalledWith(expect.objectContaining({ sort: ['id,asc'] }));
//     });
//
//     it('should calculate the sort attribute for a non-id attribute', () => {
//       // GIVEN
//       comp.predicate = 'name';
//       comp.ascending = false;
//
//       // WHEN
//       comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);
//
//       // THEN
//       expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['name,desc', 'id'] }));
//     });
//   });
// });

jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { TranslateModule } from '@ngx-translate/core';

import { EventLogService } from '../service/event-log.service';

import { EventLogComponent } from './event-log.component';

describe('Component Tests', () => {
  describe('EventLog Management Component', () => {
    let comp: EventLogComponent;
    let fixture: ComponentFixture<EventLogComponent>;
    let service: EventLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, TranslateModule.forRoot()],
        declarations: [EventLogComponent],
        providers: [
          { provide: Router, useValue: { navigate: jest.fn() } },
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                eventLog: [{ id: 123 }],
                defaultSort: 'id,asc',
              }),
            },
          },
        ],
      })
        .overrideTemplate(EventLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventLogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EventLogService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should initialize without loading from service', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).not.toHaveBeenCalled();
    });

    it('should load a page', () => {
      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventLogs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // GIVEN
      comp.predicate = 'id';
      comp.ascending = true;

      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);

      // THEN
      expect(service.query).toHaveBeenCalledWith(expect.objectContaining({ sort: ['id,asc'] }));
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';
      comp.ascending = false;

      // WHEN
      comp.loadPage({ pageIndex: 1, pageSize: 1 } as any);

      // THEN
      expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['name,desc', 'id'] }));
    });
  });
});
