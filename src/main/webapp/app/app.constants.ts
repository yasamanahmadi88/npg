// These constants are normally injected via webpack DefinePlugin.
// The fallback values prevent the app from crashing when webpack injection is missing during development.

declare const __DEBUG_INFO_ENABLED__: boolean | undefined;
declare const __TIMESTAMP__: string | number | undefined;
declare const __VERSION__: string | undefined;
declare const __SERVER_API_URL__: string | undefined;

export const VERSION = typeof __VERSION__ !== 'undefined' ? __VERSION__ : 'DEV';

export const DEBUG_INFO_ENABLED =
  typeof __DEBUG_INFO_ENABLED__ !== 'undefined' ? __DEBUG_INFO_ENABLED__ : true;

export const SERVER_API_URL =
  typeof __SERVER_API_URL__ !== 'undefined' ? __SERVER_API_URL__ : '';

export const TIMESTAMP =
  typeof __TIMESTAMP__ !== 'undefined' ? String(__TIMESTAMP__) : String(Date.now());

// export const BackUrl = 'http://192.168.21.208:8080';
export const BackUrl = 'https://tnpg.mci.ir';
