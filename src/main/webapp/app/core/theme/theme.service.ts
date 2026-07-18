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
    // Prefer light by default so Material + Bootstrap forms stay readable.
    // Users can still toggle dark explicitly via the navbar control.
    return 'light';
  }

  private applyTheme(theme: PortalTheme): void {
    const root = document.documentElement;
    root.setAttribute('data-theme', theme);
    root.classList.remove('theme-light', 'theme-dark');
    root.classList.add(theme === 'dark' ? 'theme-dark' : 'theme-light');
  }

  private listenForSystemThemeChanges(): void {
    // Intentionally no-op: portal theme follows explicit user choice / light default,
    // not OS prefers-color-scheme (which left forms unreadable with partial dark tokens).
  }
}
