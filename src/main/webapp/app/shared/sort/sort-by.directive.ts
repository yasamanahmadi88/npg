import { AfterContentInit, ContentChild, Directive, ElementRef, Host, HostListener, Input, OnDestroy, Renderer2 } from '@angular/core';
import { icon as renderFontAwesomeIcon, IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { SortDirective } from './sort.directive';

@Directive({
  selector: '[jhiSortBy]',
  exportAs: 'jhiSortBy',
  standalone: false,
})
export class SortByDirective<T> implements AfterContentInit, OnDestroy {
  @Input() jhiSortBy?: T;

  @ContentChild(FaIconComponent, { read: ElementRef, static: true })
  iconElement?: ElementRef<HTMLElement>;

  readonly sortIcon = faSort;
  readonly sortAscIcon = faSortUp;
  readonly sortDescIcon = faSortDown;

  icon: IconDefinition = this.sortIcon;

  private readonly destroy$ = new Subject<void>();

  constructor(@Host() private sort: SortDirective<T>, private renderer: Renderer2) {
    this.sort.predicateChange.pipe(takeUntil(this.destroy$)).subscribe(() => this.updateIconDefinition());
    this.sort.ascendingChange.pipe(takeUntil(this.destroy$)).subscribe(() => this.updateIconDefinition());
  }

  @HostListener('click')
  onClick(): void {
    this.sort.sort(this.jhiSortBy);
  }

  ngAfterContentInit(): void {
    this.updateIconDefinition();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private updateIconDefinition(): void {
    if (this.sort.predicate === this.jhiSortBy) {
      this.icon = this.sort.ascending ? this.sortAscIcon : this.sortDescIcon;
    } else {
      this.icon = this.sortIcon;
    }

    if (this.iconElement) {
      const renderedIcon = renderFontAwesomeIcon(this.icon).html.join('');
      this.renderer.setProperty(this.iconElement.nativeElement, 'innerHTML', renderedIcon);
    }
  }
}
