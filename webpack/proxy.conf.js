// webpack/proxy.conf.js
// Forwards backend API paths to Spring Boot during ng serve.

function setupProxy({ tls = false } = {}) {
  const protocol = tls ? 'https' : 'http';
  const target = `${protocol}://localhost:8080`;

  const serverResources = [
    '/api',
    '/services',
    '/management',
    '/swagger-resources',
    '/v3/api-docs',
    '/h2-console',
  ];

  return [
    {
      context: serverResources,
      target,
      secure: false,
      changeOrigin: Boolean(tls),
      logLevel: 'debug',
    },
  ];
}

module.exports = setupProxy;
