import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortabilityLogDialogComponent } from './portability-log-dialog.component';

describe('PortabilityLogDialogComponent', () => {
  let component: PortabilityLogDialogComponent;
  let fixture: ComponentFixture<PortabilityLogDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PortabilityLogDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PortabilityLogDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
