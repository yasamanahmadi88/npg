import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export type PortalTheme = 'light' | 'dark';

export const THEME_STORAGE_KEY = 'npg-portal-theme';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly themeSubject = new BehaviorSubject<PortalTheme>(this.resolveInitialTheme());

  readonly theme$: Observable<PortalTheme> = this.themeSubject.asObservable();

  constructor() {
    this.applyTheme(this.themeSubject.value);
    this.listenForSystemThemeChanges();
  }

  get currentTheme(): PortalTheme {
    return this.themeSubject.value;
  }

  isDark(): boolean {
    return this.currentTheme === 'dark';
  }

  setTheme(theme: PortalTheme, persist = true): void {
    if (persist) {
      localStorage.setItem(THEME_STORAGE_KEY, theme);
    }
    this.applyTheme(theme);
    this.themeSubject.next(theme);
  }

  toggleTheme(): void {
    this.setTheme(this.currentTheme === 'dark' ? 'light' : 'dark');
  }

  private resolveInitialTheme(): PortalTheme {
    const saved = localStorage.getItem(THEME_STORAGE_KEY);
    if (saved === 'light' || saved === 'dark') {
      return saved;
    }
    return this.getSystemTheme();
  }

  private getSystemTheme(): PortalTheme {
    if (typeof window !== 'undefined' && window.matchMedia?.('(prefers-color-scheme: dark)').matches) {
      return 'dark';
    }
    return 'light';
  }

  private applyTheme(theme: PortalTheme): void {
    const root = document.documentElement;
    root.setAttribute('data-theme', theme);
    root.classList.remove('theme-light', 'theme-dark');
    root.classList.add(theme === 'dark' ? 'theme-dark' : 'theme-light');
  }

  private listenForSystemThemeChanges(): void {
    if (typeof window === 'undefined' || !window.matchMedia) {
      return;
    }
    const media = window.matchMedia('(prefers-color-scheme: dark)');
    const handler = (event: MediaQueryListEvent): void => {
      const saved = localStorage.getItem(THEME_STORAGE_KEY);
      if (saved === 'light' || saved === 'dark') {
        return;
      }
      this.setTheme(event.matches ? 'dark' : 'light', false);
    };
    if (typeof media.addEventListener === 'function') {
      media.addEventListener('change', handler);
    } else if (typeof media.addListener === 'function') {
      media.addListener(handler);
    }
  }
}
