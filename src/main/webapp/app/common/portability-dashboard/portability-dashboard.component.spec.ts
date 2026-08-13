jest.mock('@swimlane/ngx-charts', () => ({
  ScaleType: { Ordinal: 'ordinal' },
}));

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

import { AlertService } from 'app/core/util/alert.service';
import { CommonService } from 'app/common/common.service';

import { PortabilityDashboardComponent } from './portability-dashboard.component';

describe('DashboardComponent', () => {
  let component: PortabilityDashboardComponent;
  let fixture: ComponentFixture<PortabilityDashboardComponent>;

  const mockCommonService = {
    fetchPortabilityPortType: jest.fn(() => of(new HttpResponse({ body: [] }))),
    fetchPortabilityPortDate: jest.fn(() => of(new HttpResponse({ body: [] }))),
  };

  const mockTranslateService = {
    instant: jest.fn((key: string) => key),
    currentLang: 'en',
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PortabilityDashboardComponent],
      providers: [
        UntypedFormBuilder,
        { provide: CommonService, useValue: mockCommonService },
        { provide: TranslateService, useValue: mockTranslateService },
        { provide: AlertService, useValue: {} },
      ],
    })
      .overrideTemplate(PortabilityDashboardComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    mockCommonService.fetchPortabilityPortType.mockClear();
    mockCommonService.fetchPortabilityPortDate.mockClear();

    fixture = TestBed.createComponent(PortabilityDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});