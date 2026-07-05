// webpack/environment.js
// Default values; webpack.custom.js injects the real build-time values.

module.exports = {
  I18N_HASH: 'generated_hash',
  __VERSION__: process.env.APP_VERSION ?? 'DEV',
  __TIMESTAMP__: String(Date.now()),
  __DEBUG_INFO_ENABLED__: process.env.NODE_ENV !== 'production',
  __SERVER_API_URL__: process.env.SERVER_API_URL ?? '',
};
