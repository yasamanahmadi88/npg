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

  it('detects system dark theme when no saved preference', () => {
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
    expect(service.currentTheme).toBe('dark');
    expect(document.documentElement.getAttribute('data-theme')).toBe('dark');
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

  it('explicit preference overrides system theme changes', () => {
    let changeHandler: ((event: MediaQueryListEvent) => void) | undefined;
    matchMediaMock.mockImplementation((query: string) => ({
      matches: false,
      media: query,
      addEventListener: (_: string, handler: (event: MediaQueryListEvent) => void) => {
        changeHandler = handler;
      },
      removeEventListener: jest.fn(),
      addListener: jest.fn(),
      removeListener: jest.fn(),
      dispatchEvent: jest.fn(),
    }));
    service = new ThemeService();
    service.setTheme('light');
    changeHandler?.({ matches: true } as MediaQueryListEvent);
    expect(service.currentTheme).toBe('light');
  });

  it('exposes isDark based on current theme', () => {
    service = new ThemeService();
    expect(service.isDark()).toBe(false);
    service.setTheme('dark' as PortalTheme);
    expect(service.isDark()).toBe(true);
  });
});
