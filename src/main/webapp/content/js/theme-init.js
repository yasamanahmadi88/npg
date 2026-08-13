(function () {
  try {
    var key = 'npg-portal-theme';
    var saved = localStorage.getItem(key);
    var theme = saved === 'light' || saved === 'dark'
      ? saved
      : (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
    var root = document.documentElement;
    root.setAttribute('data-theme', theme);
    root.classList.remove('theme-light', 'theme-dark');
    root.classList.add(theme === 'dark' ? 'theme-dark' : 'theme-light');
  } catch (e) {
    // Ignore storage access errors; ThemeService will apply defaults later.
  }
})();
