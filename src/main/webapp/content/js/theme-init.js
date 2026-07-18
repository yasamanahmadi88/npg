(function () {
  try {
    var key = 'npg-portal-theme';
    var saved = localStorage.getItem(key);
    // Default to light unless the user explicitly chose a theme.
    // Auto dark (OS preference) broke Material/Bootstrap form contrast.
    var theme = saved === 'light' || saved === 'dark' ? saved : 'light';
    var root = document.documentElement;
    root.setAttribute('data-theme', theme);
    root.classList.remove('theme-light', 'theme-dark');
    root.classList.add(theme === 'dark' ? 'theme-dark' : 'theme-light');
  } catch (e) {
    // Ignore storage access errors; ThemeService will apply defaults later.
  }
})();
