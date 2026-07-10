import { Directive, ElementRef, Input, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Subject, merge } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { translationNotFoundMessage } from 'app/config/translation.config';

@Directive({
  selector: '[jhiTranslate]',
  standalone: false,
})
export class TranslateDirective implements OnInit, OnChanges, OnDestroy {
  @Input() jhiTranslate!: string;
  @Input() translateValues?: Record<string, unknown>;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private el: ElementRef<HTMLElement>,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    merge(
      this.translateService.onLangChange,
      this.translateService.onTranslationChange
    )
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => this.updateTranslation());
  }

  ngOnChanges(): void {
    this.updateTranslation();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private updateTranslation(): void {
    if (!this.jhiTranslate) {
      return;
    }

    this.translateService
      .get(this.jhiTranslate, this.translateValues)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (value: string) => {
          this.setText(value);
        },
        error: () => {
          this.setText(`${translationNotFoundMessage}[${this.jhiTranslate}]`);
        },
      });
  }

  private setText(value: string): void {
    this.el.nativeElement.textContent = value;
  }
}


