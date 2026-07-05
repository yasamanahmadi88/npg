import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { PortabilitySearchComponent } from './portability-search.component';
import { PortabilityService } from '../service/portability.service';

describe('PortabiltySearchComponent', () => {
  let component: PortabilitySearchComponent;
  let fixture: ComponentFixture<PortabilitySearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot()],
      declarations: [PortabilitySearchComponent],
      providers: [
        FormBuilder,
        {
          provide: PortabilityService,
          useValue: {
            query: () => of({ body: [] }),
          },
        },
      ],
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
