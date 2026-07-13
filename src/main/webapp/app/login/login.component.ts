import { Component, ViewChild, OnInit, AfterViewInit, ElementRef, ChangeDetectorRef } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Login } from './login.model';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.scss'],
  standalone: false,
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username!: ElementRef;

  authenticationError = false;
  wrongCaptcha = false;
  captchaLoadError = false;
  captchaLoading = false;
  loginSubmitting = false;
  captchaId = '';
  captchaImageUrl = '';

  concurrentSessionError = false;
  tooManyFailedReq = false;

  loginForm = new UntypedFormGroup({
    username: new UntypedFormControl('', { validators: [Validators.required] }),
    password: new UntypedFormControl('', { validators: [Validators.required] }),
    rememberMe: new UntypedFormControl(false),
    userCaptchaInput: new UntypedFormControl('', { validators: [Validators.required, Validators.minLength(4), Validators.maxLength(6)] }),
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
    setTimeout(() => this.loadCaptcha(), 0);
  }

  get isBusy(): boolean {
    return this.loginSubmitting || this.captchaLoading;
  }

  fieldInvalid(controlName: string): boolean {
    const control = this.loginForm.get(controlName);
    return !!control && control.invalid && (control.dirty || control.touched);
  }

  loadCaptcha(): void {
    this.captchaLoading = true;
    this.captchaLoadError = false;
    this.changeDetectorRef.detectChanges();

    this.http.post<{ captchaId: string; captchaImageUrl: string }>('/api/captcha-endpoint', {}).subscribe({
      next: response => {
        this.captchaId = response.captchaId;
        const separator = response.captchaImageUrl.includes('?') ? '&' : '?';
        this.captchaImageUrl = `${response.captchaImageUrl}${separator}t=${Date.now()}`;
        this.loginForm.patchValue({ userCaptchaInput: '' });
        this.loginForm.get('userCaptchaInput')?.markAsUntouched();
        this.loginForm.get('userCaptchaInput')?.markAsPristine();
        this.captchaLoading = false;
        this.changeDetectorRef.detectChanges();
      },
      error: () => {
        this.captchaId = '';
        this.captchaImageUrl = '';
        this.captchaLoadError = true;
        this.captchaLoading = false;
        this.changeDetectorRef.detectChanges();
      },
    });
  }

  reloadCaptcha(): void {
    if (this.captchaLoading || this.loginSubmitting) {
      return;
    }
    this.authenticationError = false;
    this.wrongCaptcha = false;
    this.captchaLoadError = false;
    this.loadCaptcha();
  }

  login(): void {
    this.authenticationError = false;
    this.wrongCaptcha = false;
    this.tooManyFailedReq = false;
    this.concurrentSessionError = false;

    this.loginForm.markAllAsTouched();

    if (this.loginForm.invalid) {
      return;
    }

    if (this.loginSubmitting || this.captchaLoading) {
      return;
    }

    if (!this.captchaId) {
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

    this.loginSubmitting = true;

    this.loginService.login(credentials).subscribe({
      next: () => {
        this.loginSubmitting = false;
        if (!this.router.getCurrentNavigation()) {
          this.router.navigate(['/dashboard']);
        }
      },
      error: (error: HttpErrorResponse) => {
        this.loginSubmitting = false;
        if (error.status === 429) {
          this.tooManyFailedReq = true;
        } else if (error.error?.detail === 'error.user.has.concurrent.session') {
          this.concurrentSessionError = true;
        } else if (this.isCaptchaValidationError(error)) {
          this.wrongCaptcha = true;
        } else {
          this.authenticationError = true;
        }

        // Captcha is one-time. Reload it after every failed login attempt.
        this.loadCaptcha();
      },
    });
  }

  private isCaptchaValidationError(error: HttpErrorResponse): boolean {
    const body = error.error;
    if (!body) {
      return false;
    }
    const nested = body.parameters ?? {};
    return (
      body.errorKey === 'captcha.validation' ||
      nested.errorKey === 'captcha.validation' ||
      body.message === 'error.captcha.validation' ||
      nested.message === 'error.captcha.validation' ||
      body.title === 'Captcha Invalid'
    );
  }
}
