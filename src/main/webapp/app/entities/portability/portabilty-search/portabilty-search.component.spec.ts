import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortabilitySearchComponent } from './portability-search.component';

describe('PortabiltySearchComponent', () => {
  let component: PortabilitySearchComponent;
  let fixture: ComponentFixture<PortabilitySearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PortabilitySearchComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PortabilitySearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
