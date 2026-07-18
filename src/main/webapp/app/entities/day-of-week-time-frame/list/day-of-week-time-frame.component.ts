import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IDayOfWeekTimeFrame } from '../day-of-week-time-frame.model';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { DayOfWeekTimeFrameService } from '../service/day-of-week-time-frame.service';
import { DayOfWeekTimeFrameDeleteDialogComponent } from '../delete/day-of-week-time-frame-delete-dialog.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'jhi-day-of-week-time-frame',
  templateUrl: './day-of-week-time-frame.component.html',
  styleUrls: ['./day-of-week-time-frame.component.scss'],
  standalone: false,
})
export class DayOfWeekTimeFrameComponent implements OnInit {
  dayOfWeekTimeFrames?: IDayOfWeekTimeFrame[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  currentSearch!: string;

  constructor(
    protected dayOfWeekTimeFrameService: DayOfWeekTimeFrameService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected translateService: TranslateService,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    if (this.currentSearch) {
      this.dayOfWeekTimeFrameService
        .search({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          searchText: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<IDayOfWeekTimeFrame[]>) => {
            this.isLoading = false;
            this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
          },
          () => {
            this.isLoading = false;
            this.onError();
          }
        );
    } else {
      this.dayOfWeekTimeFrameService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IDayOfWeekTimeFrame[]>) => {
            this.isLoading = false;
            this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
          },
          () => {
            this.isLoading = false;
            this.onError();
          }
        );
    }
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackId(index: number, item: IDayOfWeekTimeFrame): number {
    return item.id!;
  }

  delete(dayOfWeekTimeFrame: IDayOfWeekTimeFrame): void {
    const modalRef = this.modalService.open(DayOfWeekTimeFrameDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dayOfWeekTimeFrame = dayOfWeekTimeFrame;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  search(query: string): void {
    if (!query) {
      return this.clearSearch();
    }
    this.page = 0;
    this.currentSearch = query;
    this.loadPage();
  }

  detectLang(val: number): any {
    if (this.translateService.currentLang === 'fa') {
      if (val === 1) {
        return 'شنبه';
      } else if (val === 2) {
        return 'یک شنبه';
      } else if (val === 3) {
        return 'دو شنبه';
      } else if (val === 4) {
        return 'سه شنبه';
      } else if (val === 5) {
        return 'چهار شنبه';
      } else if (val === 6) {
        return 'پنج شنبه';
      } else if (val === 7) {
        return 'جمعه';
      }
    } else if (this.translateService.currentLang === 'en') {
      if (val === 1) {
        return 'Saturday';
      } else if (val === 2) {
        return 'Sunday';
      } else if (val === 3) {
        return 'Monday';
      } else if (val === 4) {
        return 'Tuesday';
      } else if (val === 5) {
        return 'Wednesday';
      } else if (val === 6) {
        return 'Thursday';
      } else if (val === 7) {
        return 'Friday';
      }
    }
  }

  clearSearch(): void {
    this.currentSearch = '';
    this.loadPage();
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IDayOfWeekTimeFrame[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/day-of-week-time-frame'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.dayOfWeekTimeFrames = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
    this.dayOfWeekTimeFrames = [];
  }
}
