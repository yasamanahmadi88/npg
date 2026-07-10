import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { PortabilityLogDialogComponent } from './portability-log-dialog.component';
import { PortabilityLogService } from '../../portability-log/service/portability-log.service';

describe('PortabilityLogDialogComponent', () => {
  let component: PortabilityLogDialogComponent;
  let fixture: ComponentFixture<PortabilityLogDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()],
      declarations: [PortabilityLogDialogComponent],
      providers: [
        {
          provide: NgbActiveModal,
          useValue: { dismiss: jest.fn(), close: jest.fn() },
        },
        {
          provide: PortabilityLogService,
          useValue: {
            query: () => of({ body: [] }),
          },
        },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PortabilityLogDialogComponent);
    component = fixture.componentInstance;
    component.portability = { id: 1 };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
