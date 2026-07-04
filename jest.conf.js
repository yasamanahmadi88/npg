const { pathsToModuleNameMapper } = require('ts-jest');

module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/src/main/webapp/setup-jest.ts'],
  cacheDirectory: '<rootDir>/target/jest-cache',
  coverageDirectory: '<rootDir>/target/test-results/',
  coverageReporters: ['text-summary', 'lcov'],
  reporters: [
    'default',
    ['jest-junit', { outputDirectory: '<rootDir>/target/test-results/jest', outputName: 'TESTS-results-jest.xml' }],
    ['jest-sonar-reporter', { outputDirectory: '<rootDir>/target/test-results/jest', outputName: 'TESTS-results-sonar.xml' }],
  ],
  testMatch: ['<rootDir>/src/main/webapp/**/*.spec.ts'],
  moduleNameMapper: pathsToModuleNameMapper(
    {
      app: ['src/main/webapp/app'],
    },
    { prefix: '<rootDir>/' }
  ),
  testEnvironmentOptions: {
    url: 'http://localhost/',
  },
  transformIgnorePatterns: ['node_modules/(?!.*\\.mjs$|dayjs|@angular|@ngx-translate|@fortawesome|ngx-webstorage)'],
};
