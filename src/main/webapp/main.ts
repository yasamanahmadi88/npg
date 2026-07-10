import('./bootstrap').catch(err => {
  console.error('Angular bootstrap failed:', err);
  throw err;
});
