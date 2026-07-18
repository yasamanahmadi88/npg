import { ThemeService, THEME_STORAGE_KEY, PortalTheme } from './theme.service';

describe('ThemeService', () => {
  let service: ThemeService;
  let matchMediaMock: jest.Mock;

  beforeEach(() => {
    localStorage.clear();
    document.documentElement.removeAttribute('data-theme');
    document.documentElement.classList.remove('theme-light', 'theme-dark');

    matchMediaMock = jest.fn().mockImplementation((query: string) => ({
      matches: false,
      media: query,
      addEventListener: jest.fn(),
      removeEventListener: jest.fn(),
      addListener: jest.fn(),
      removeListener: jest.fn(),
      dispatchEvent: jest.fn(),
    }));
    Object.defineProperty(window, 'matchMedia', {
      writable: true,
      value: matchMediaMock,
    });
  });

  afterEach(() => {
    localStorage.clear();
  });

  it('defaults to light when no preference and system is light', () => {
    service = new ThemeService();
    expect(service.currentTheme).toBe('light');
    expect(document.documentElement.getAttribute('data-theme')).toBe('light');
    expect(document.documentElement.classList.contains('theme-light')).toBe(true);
  });

  it('defaults to light even when system prefers dark', () => {
    matchMediaMock.mockImplementation((query: string) => ({
      matches: query.includes('dark'),
      media: query,
      addEventListener: jest.fn(),
      removeEventListener: jest.fn(),
      addListener: jest.fn(),
      removeListener: jest.fn(),
      dispatchEvent: jest.fn(),
    }));
    service = new ThemeService();
    expect(service.currentTheme).toBe('light');
    expect(document.documentElement.getAttribute('data-theme')).toBe('light');
  });

  it('restores saved theme preference', () => {
    localStorage.setItem(THEME_STORAGE_KEY, 'dark');
    service = new ThemeService();
    expect(service.currentTheme).toBe('dark');
  });

  it('toggles from light to dark and persists', () => {
    service = new ThemeService();
    service.toggleTheme();
    expect(service.currentTheme).toBe('dark');
    expect(localStorage.getItem(THEME_STORAGE_KEY)).toBe('dark');
    expect(document.documentElement.getAttribute('data-theme')).toBe('dark');
  });

  it('toggles from dark to light and persists', () => {
    localStorage.setItem(THEME_STORAGE_KEY, 'dark');
    service = new ThemeService();
    service.toggleTheme();
    expect(service.currentTheme).toBe('light');
    expect(localStorage.getItem(THEME_STORAGE_KEY)).toBe('light');
  });

  it('setTheme updates root attribute and class', () => {
    service = new ThemeService();
    service.setTheme('dark');
    expect(document.documentElement.getAttribute('data-theme')).toBe('dark');
    expect(document.documentElement.classList.contains('theme-dark')).toBe(true);
    expect(document.documentElement.classList.contains('theme-light')).toBe(false);
  });

  it('explicit preference is not overridden by system theme hooks', () => {
    service = new ThemeService();
    service.setTheme('light');
    expect(service.currentTheme).toBe('light');
    service.setTheme('dark');
    expect(service.currentTheme).toBe('dark');
  });

  it('exposes isDark based on current theme', () => {
    service = new ThemeService();
    expect(service.isDark()).toBe(false);
    service.setTheme('dark' as PortalTheme);
    expect(service.isDark()).toBe(true);
  });
});
