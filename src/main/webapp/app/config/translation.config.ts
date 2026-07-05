import {
  MissingTranslationHandler,
  MissingTranslationHandlerParams,
  provideMissingTranslationHandler,
} from '@ngx-translate/core';
import { provideTranslateHttpLoader } from '@ngx-translate/http-loader';

import { TIMESTAMP } from '../app.constants';

export const translationNotFoundMessage = 'translation-not-found';

export class MissingTranslationHandlerImpl implements MissingTranslationHandler {
  handle(params: MissingTranslationHandlerParams): string {
    const key = params.key;
    return `${translationNotFoundMessage}[${key}]`;
  }
}

export const translatePartialLoader = provideTranslateHttpLoader({
  prefix: 'i18n/',
  suffix: `.json?buildTimestamp=${TIMESTAMP}`,
});

export const missingTranslationHandler = provideMissingTranslationHandler(MissingTranslationHandlerImpl);
