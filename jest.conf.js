const { pathsToModuleNameMapper } = require('ts-jest');
const { createCjsPreset } = require('jest-preset-angular/presets');

const {
  compilerOptions: { paths = {}, baseUrl = './' },
} = require('./tsconfig.json');

const presetConfig = createCjsPreset({
  tsconfig: '<rootDir>/tsconfig.spec.json',
});

module.exports = {
  ...presetConfig,

  roots: ['<rootDir>', `<rootDir>/${baseUrl}`],
  modulePaths: [`<rootDir>/${baseUrl}`],

  setupFiles: ['jest-date-mock'],
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],

  moduleNameMapper: {
    ...pathsToModuleNameMapper(paths, { prefix: `<rootDir>/${baseUrl}/` }),
  },

  reporters: [
    'default',
    [
      'jest-junit',
      {
        outputDirectory: '<rootDir>/target/test-results/',
        outputName: 'TESTS-results-jest.xml',
      },
    ],
  ],

  testResultsProcessor: 'jest-sonar-reporter',

  testMatch: ['<rootDir>/src/main/webapp/app/**/*.spec.ts'],

  testEnvironmentOptions: {
    url: 'http://localhost/',
  },

  coverageDirectory: '<rootDir>/target/test-results/jest',
};
