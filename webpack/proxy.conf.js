function setupProxy({ tls }) {
  const serverUrl = `http${tls ? 's' : ''}://localhost:8080`;
  return [
    {
      context: ['/api', '/services', '/management', '/v3/api-docs', '/h2-console', '/auth', '/health'],
      target: serverUrl,
      secure: false,
      changeOrigin: tls,
    },
    {
      context: ['/websocket'],
      target: serverUrl,
      secure: false,
      changeOrigin: tls,
      ws: true,
    },
  ];
}

module.exports = setupProxy;
