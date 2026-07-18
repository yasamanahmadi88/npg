// @ts-check
const eslint = require('@eslint/js');
const angular = require('@angular-eslint/eslint-plugin');
const tseslint = require('typescript-eslint');
const prettier = require('eslint-config-prettier');

module.exports = tseslint.config(
  {
    ignores: [
      'target/**',
      'node_modules/**',
      'dist/**',
      'src/main/webapp/content/js/**',
      'webpack/**',
    ],
  },
  eslint.configs.recommended,
  ...tseslint.configs.recommended,
  prettier,
  {
    files: ['src/main/webapp/**/*.ts'],
    languageOptions: {
      parserOptions: {
        projectService: true,
        allowDefaultProject: ['*.js', '*.mjs', '*.cjs'],
      },
    },
    plugins: {
      '@angular-eslint': angular,
    },
    rules: {
      '@typescript-eslint/no-explicit-any': 'off',
      '@typescript-eslint/no-unused-vars': 'off',
      '@typescript-eslint/no-empty-object-type': 'off',
      '@typescript-eslint/ban-ts-comment': 'off',
      '@typescript-eslint/no-unused-expressions': 'off',
      'no-console': 'off',
      'no-useless-escape': 'off',
    },
  }
);
