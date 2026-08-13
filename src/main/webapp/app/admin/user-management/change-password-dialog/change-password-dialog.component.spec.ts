import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { ChangePasswordDialogComponent } from './change-password-dialog.component';
import { UserService } from 'app/entities/user/user.service';
import { PasswordService } from 'app/account/password/password.service';

describe('ChangePasswordDialogComponent', () => {
  let component: ChangePasswordDialogComponent;
  let fixture: ComponentFixture<ChangePasswordDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ChangePasswordDialogComponent],
      providers: [
        FormBuilder,
        {
          provide: NgbActiveModal,
          useValue: { dismiss: jest.fn(), close: jest.fn() },
        },
        {
          provide: UserService,
          useValue: {},
        },
        {
          provide: PasswordService,
          useValue: {
            resetPassword: () => of({}),
          },
        },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangePasswordDialogComponent);
    component = fixture.componentInstance;
    component.user = { login: 'user' } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
