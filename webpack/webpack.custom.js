const path = require('path');
const webpack = require('webpack');
const { merge } = require('webpack-merge');
const { hashElement } = require('folder-hash');
const MergeJsonWebpackPlugin = require('merge-jsons-webpack-plugin');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');
const WebpackNotifierPlugin = require('webpack-notifier');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const environment = require('./environment');
const proxyConfig = require('./proxy.conf');

module.exports = async (config, options, targetOptions) => {
  const languagesHash = await hashElement(path.resolve(__dirname, '../src/main/webapp/i18n'), {
    algo: 'md5',
    encoding: 'hex',
    files: { include: ['*.json'] },
  });

  const tls = Boolean(process.env.TLS) || config.devServer?.server?.type === 'https';

  config.cache = {
    type: 'filesystem',
    cacheDirectory: path.resolve(__dirname, '../target/webpack'),
    buildDependencies: {
      config: [
        __filename,
        path.resolve(__dirname, 'environment.js'),
        path.resolve(__dirname, 'proxy.conf.js'),
        path.resolve(__dirname, '../angular.json'),
        path.resolve(__dirname, '../tsconfig.app.json'),
        path.resolve(__dirname, '../tsconfig.json'),
      ],
    },
  };

  if (config.devServer) {
    config.devServer.proxy = proxyConfig({ tls });
  }

  if (config.mode === 'development') {
    config.plugins.push(
      new WebpackNotifierPlugin({
        title: 'Npg Portal',
        contentImage: path.join(__dirname, 'logo-jhipster.png'),
      })
    );
  }

  if (targetOptions.target === 'serve' || config.watch) {
    config.plugins.push(
      new BrowserSyncPlugin(
        {
          host: 'localhost',
          port: 9000,
          https: tls,
          proxy: {
            target: `http${tls ? 's' : ''}://localhost:${targetOptions.target === 'serve' ? '4200' : '8080'}`,
            ws: true,
            proxyOptions: {
              changeOrigin: false,
            },
            proxyReq: [
              function (proxyReq) {
                proxyReq.setHeader('X-Forwarded-Host', 'localhost:9000');
                proxyReq.setHeader('X-Forwarded-Proto', `http${tls ? 's' : ''}`);
              },
            ],
          },
          socket: {
            clients: {
              heartbeatTimeout: 60000,
            },
          },
        },
        {
          reload: targetOptions.target === 'build',
        }
      )
    );
  }

  if (config.mode === 'production') {
    config.plugins.push(
      new BundleAnalyzerPlugin({
        analyzerMode: 'static',
        openAnalyzer: false,
        reportFilename: '../stats.html',
      })
    );
  }

  const patterns = [
    // jhipster-needle-add-assets-to-webpack
  ];

  if (patterns.length > 0) {
    config.plugins.push(new CopyWebpackPlugin({ patterns }));
  }

  config.plugins.push(
    new webpack.DefinePlugin({
      I18N_HASH: JSON.stringify(languagesHash.hash),
      __TIMESTAMP__: JSON.stringify(environment.__TIMESTAMP__ ?? String(Date.now())),
      __VERSION__: JSON.stringify(environment.__VERSION__ ?? 'DEV'),
      __DEBUG_INFO_ENABLED__: environment.__DEBUG_INFO_ENABLED__ ?? config.mode === 'development',
      __SERVER_API_URL__: JSON.stringify(environment.__SERVER_API_URL__ ?? ''),
    }),
    new MergeJsonWebpackPlugin({
      output: {
        groupBy: [
          { pattern: './src/main/webapp/i18n/en/*.json', fileName: './i18n/en.json' },
          { pattern: './src/main/webapp/i18n/fa/*.json', fileName: './i18n/fa.json' },
          // jhipster-needle-i18n-language-webpack
        ],
      },
    })
  );

  return merge(config);
};
