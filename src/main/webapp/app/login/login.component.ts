import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { Login } from './login.model';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.scss'],
  standalone: false,
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username!: ElementRef<HTMLInputElement>;

  authenticationError = false;
  wrongCaptcha = false;
  captchaLoadError = false;
  loading = false;
  captchaLoading = false;
  formSubmissionAttempted = false;
  captchaId = '';
  captchaImageUrl = '';

  concurrentSessionError = false;
  tooManyFailedReq = false;

  loginForm = new UntypedFormGroup({
    username: new UntypedFormControl('', { validators: [Validators.required] }),
    password: new UntypedFormControl('', { validators: [Validators.required] }),
    rememberMe: new UntypedFormControl(false),
    userCaptchaInput: new UntypedFormControl('', {
      validators: [Validators.required, Validators.minLength(6), Validators.maxLength(6)],
    }),
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private http: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['/dashboard']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.username.nativeElement.focus();

    setTimeout(() => {
      this.loadCaptcha();
    }, 0);
  }

  get submitDisabled(): boolean {
    return this.loading || this.captchaLoading || this.captchaLoadError || !this.captchaId;
  }

  isControlInvalid(controlName: string): boolean {
    const control = this.loginForm.get(controlName);
    return Boolean(control?.invalid && (control.touched || this.formSubmissionAttempted));
  }

  loadCaptcha(): void {
    this.captchaLoading = true;
    this.captchaLoadError = false;
    this.captchaId = '';
    this.captchaImageUrl = '';
    this.changeDetectorRef.detectChanges();

    this.http.post<{ captchaId: string; captchaImageUrl: string }>('/api/captcha-endpoint', {}).subscribe({
      next: response => {
        this.captchaId = response.captchaId;
        const separator = response.captchaImageUrl.includes('?') ? '&' : '?';
        this.captchaImageUrl = `${response.captchaImageUrl}${separator}t=${Date.now()}`;
        this.loginForm.patchValue({ userCaptchaInput: '' });
        this.captchaLoading = false;
        this.changeDetectorRef.detectChanges();
      },
      error: () => {
        this.captchaLoadError = true;
        this.captchaLoading = false;
        this.changeDetectorRef.detectChanges();
      },
    });
  }

  reloadCaptcha(): void {
    if (!this.loading && !this.captchaLoading) {
      this.authenticationError = false;
      this.wrongCaptcha = false;
      this.captchaLoadError = false;
      this.loadCaptcha();
    }
  }

  login(): void {
    this.formSubmissionAttempted = true;
    this.authenticationError = false;
    this.wrongCaptcha = false;
    this.tooManyFailedReq = false;
    this.concurrentSessionError = false;

    if (this.loginForm.invalid || this.loading) {
      this.loginForm.markAllAsTouched();
      return;
    }

    if (!this.captchaId || this.captchaLoadError) {
      this.captchaLoadError = true;
      this.loadCaptcha();
      return;
    }

    const credentials = new Login(
      this.loginForm.get('username')!.value,
      this.loginForm.get('password')!.value,
      this.loginForm.get('rememberMe')!.value,
      this.captchaId,
      this.loginForm.get('userCaptchaInput')!.value
    );

    this.loading = true;

    this.loginService.login(credentials).subscribe({
      next: () => {
        this.loading = false;
        if (!this.router.getCurrentNavigation()) {
          this.router.navigate(['/dashboard']);
        }
      },
      error: (error: HttpErrorResponse) => {
        this.loading = false;

        if (error.status === 429) {
          this.tooManyFailedReq = true;
        } else if ('error.user.has.concurrent.session' === error.error?.detail) {
          this.concurrentSessionError = true;
        } else if (this.isCaptchaValidationError(error)) {
          this.wrongCaptcha = true;
        } else {
          this.authenticationError = true;
        }

        // The server consumes every CAPTCHA attempt, successful or not.
        this.loadCaptcha();
      },
    });
  }

  private isCaptchaValidationError(error: HttpErrorResponse): boolean {
    const body = error.error ?? {};
    const type = String(body.type ?? '');
    const message = String(body.message ?? '');

    return (
      body.errorKey === 'captcha.validation' ||
      body.entityName === 'captchaValidation' ||
      message === 'error.captcha.validation' ||
      message === 'captcha.validation' ||
      type.includes('invalid-captcha-validation')
    );
  }
}
