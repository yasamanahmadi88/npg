const fs = require('fs');
const path = require('path');

const file = path.resolve('.eslintrc.json');

const raw = fs.readFileSync(file, 'utf8').replace(/^\uFEFF/, '');
const config = JSON.parse(raw);

config.rules = config.rules || {};

config.rules['@angular-eslint/prefer-inject'] = 'off';

// این‌ها هم قبلاً برای migration مرحله‌ای لازم بودند؛ نگهشان دار
config.rules['@angular-eslint/prefer-standalone'] = 'off';
config.rules['@typescript-eslint/no-empty-object-type'] = 'off';
delete config.rules['@typescript-eslint/ban-types'];

fs.writeFileSync(file, JSON.stringify(config, null, 2) + '\n', 'utf8');

console.log('prefer-inject =', config.rules['@angular-eslint/prefer-inject']);
console.log('prefer-standalone =', config.rules['@angular-eslint/prefer-standalone']);
console.log('no-empty-object-type =', config.rules['@typescript-eslint/no-empty-object-type']);
console.log('ban-types =', config.rules['@typescript-eslint/ban-types']);
