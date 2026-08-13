import { FlatCompat } from "@eslint/eslintrc";
import js from "@eslint/js";
import { fileURLToPath } from "node:url";
import path from "node:path";
import fs from "node:fs";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: js.configs.recommended,
  allConfig: js.configs.all,
});

function readIgnoreFile(fileName) {
  const filePath = path.join(__dirname, fileName);

  if (!fs.existsSync(filePath)) {
    return [];
  }

  return fs
    .readFileSync(filePath, "utf8")
    .split(/\r?\n/)
    .map(line => line.trim())
    .filter(line => line && !line.startsWith("#"));
}

export default [
  {
    ignores: [
      ...readIgnoreFile(".eslintignore.legacy"),
      "node_modules/**",
      "target/**",
      "dist/**",
      "coverage/**",
      ".angular/**",
    ],
  },

  ...compat.config(
    JSON.parse(fs.readFileSync(path.join(__dirname, ".eslintrc.json"), "utf8")),
  ),

  {
    files: ["src/main/webapp/app/shared/export/mat-table-exporter-compat.directive.ts"],
    rules: {
      "@angular-eslint/directive-selector": "off",
      "no-bitwise": "off",
      "no-useless-escape": "off",
    },
  },
];
