import { Component, OnInit, RendererFactory2, Renderer2, OnDestroy, HostListener } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import dayjs from 'dayjs';

import { AccountService } from 'app/core/auth/account.service';
import { FindLanguageFromKeyPipe } from 'app/shared/language/find-language-from-key.pipe';
import { LoginService } from '../../login/login.service';
import { PublicService } from '../../shared/public.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  standalone: false,
})
export class MainComponent implements OnInit, OnDestroy {
  private renderer: Renderer2;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private findLanguageFromKeyPipe: FindLanguageFromKeyPipe,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2,
    private loginService: LoginService,
    private publicService: PublicService,
    private localStorageService: LocalStorageService
  ) {
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  @HostListener('window:beforeunload')
  ngOnDestroy(): void {
    this.loginService.logout();
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();
      dayjs.locale(langChangeEvent.lang);
      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);

      this.updatePageDirection();
    });

    if (document.location.port === '9000') {
      this.publicService.getCaptchaBackUrl().subscribe(
        response => {
          this.localStorageService.store('backendUrl', response.body?.backUrl);
        },
        (error: HttpErrorResponse) => {
          console.error('cannot load backend url from server..');
        }
      );
    } else {
      this.localStorageService.store('backendUrl', document.location.origin);
    }
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data['pageTitle'] ?? '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }

  private updatePageDirection(): void {
    this.renderer.setAttribute(
      document.querySelector('html'),
      'dir',
      this.findLanguageFromKeyPipe.isRTL(this.translateService.currentLang) ? 'rtl' : 'ltr'
    );
  }
}
