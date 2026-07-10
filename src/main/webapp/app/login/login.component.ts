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
  loading = false;
  captchaId = '';
  captchaImageUrl = '';

  concurrentSessionError = false;
  tooManyFailedReq = false;

  loginForm = new UntypedFormGroup({
    username: new UntypedFormControl('', { validators: [Validators.required] }),
    password: new UntypedFormControl('', { validators: [Validators.required] }),
    rememberMe: new UntypedFormControl(false),
    userCaptchaInput: new UntypedFormControl('', { validators: [Validators.required] }),
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private http: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
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

  loadCaptcha(): void {
    this.loading = true;
    this.captchaLoadError = false;
    this.changeDetectorRef.detectChanges();

    this.http.post<{ captchaId: string; captchaImageUrl: string }>('/api/captcha-endpoint', {}).subscribe({
      next: response => {
        this.captchaId = response.captchaId;
        const separator = response.captchaImageUrl.includes('?') ? '&' : '?';
        this.captchaImageUrl = `${response.captchaImageUrl}${separator}t=${Date.now()}`;
        this.loginForm.patchValue({ userCaptchaInput: '' });
        this.loading = false;
        this.changeDetectorRef.detectChanges();
      },
      error: () => {
        this.captchaId = '';
        this.captchaImageUrl = '';
        this.captchaLoadError = true;
        this.authenticationError = true;
        this.loading = false;
        this.changeDetectorRef.detectChanges();
      },
    });
  }

  reloadCaptcha(): void {
    if (!this.loading) {
      this.authenticationError = false;
      this.wrongCaptcha = false;
      this.captchaLoadError = false;
      this.loadCaptcha();
    }
  }

  login(): void {
    this.authenticationError = false;
    this.wrongCaptcha = false;
    this.tooManyFailedReq = false;
    this.concurrentSessionError = false;

    if (this.loginForm.invalid || this.loading) {
      this.loginForm.markAllAsTouched();
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

    this.loading = true;

    this.loginService.login(credentials).subscribe({
      next: () => {
        if (!this.router.getCurrentNavigation()) {
          // There were no routing during login (eg from navigationToStoredUrl)
          this.router.navigate(['/dashboard']);
        }
        this.loading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.loading = false;
        if (error.status === 429) {
          this.tooManyFailedReq = true;
        } else if ('error.user.has.concurrent.session' === error.error?.detail) {
          this.concurrentSessionError = true;
        } else if (error.error?.errorKey === 'captcha.validation') {
          this.wrongCaptcha = true;
        } else {
          this.authenticationError = true;
        }

        // Captcha is one-time. Reload it after every failed login attempt.
        this.loadCaptcha();
      },
    });
  }
}
