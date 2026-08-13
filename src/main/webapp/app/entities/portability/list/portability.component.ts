import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPortability } from '../portability.model';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { PortabilityService } from '../service/portability.service';
import { PortabilityDeleteDialogComponent } from '../delete/portability-delete-dialog.component';
import { PortabilityLogDialogComponent } from '../portability-log-dialog/portability-log-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { ECalendarValue, IDatePickerConfig } from 'app/shared/date-picker/dp-date-picker-compat.types';
import { DATE_FORMAT, DATE_TIME_FORMAT } from '../../../config/input.constants';
import { TranslateService } from '@ngx-translate/core';
import { UntypedFormBuilder } from '@angular/forms';
import moment from 'moment';

import { MatSort, Sort } from '@angular/material/sort';

@Component({
  selector: 'jhi-portability',
  templateUrl: './portability.component.html',
  styleUrls: ['./portability.scss'],
  standalone: false,
})
export class PortabilityComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('empTbSort') empTbSort = new MatSort();
  pageToLoad = 1;

  editForm = this.fb.group({
    porRequestId: [],
    porNumber: [],
    porCrDate: [],
    porIdNumber: [],
    porMnpid: [],
    porReceiver: [],
    porRouting: [],
    porStatus: [],
    porPortedDate: [],
    porDeadline: [],
    porUpdDate: [],
    porErr: [],
    porAccType: [],
    porType: [],
    porRspCode: [],
    donor: [],
    recipient: [],
  });

  portabilities!: IPortability[] | any;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  readonly pageSizeOptions = [10, 20, 50, 75, 100];
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  displayedColumns = ['id', 'porType', 'porCrDate', 'porNumber', 'porAccType', 'porIdNumber', 'porRequestId', 'porOpd', 'porOpr', 'action'];
  dataSource: MatTableDataSource<IPortability> = new MatTableDataSource<IPortability>([]);
  portability?: IPortability[];

  dpConfig: IDatePickerConfig = {
    disableKeypress: false,
    showMultipleYearsNavigation: true,
    format: DATE_FORMAT,
    multipleYearsNavigateBy: 3,
    monthBtnFormat: 'MMMM',
    showWeekNumbers: false,
    showTwentyFourHours: true,
    showSeconds: true,
    locale: this.translateService.currentLang,
    returnedValueType: ECalendarValue.Moment,
  };
  expanded = false;
  private query: any = {};
  private requestSubscription?: Subscription;
  private routeStateSubscription?: Subscription;
  private sortSubscription?: Subscription;
  private routeStateInitialized = false;
  private routeFilterSignature = '';
  private readonly filterParamNames = [
    'porRequestId',
    'porNumber',
    'porCrDate',
    'porIdNumber',
    'porMnpid',
    'porReceiver',
    'porRouting',
    'porStatus',
    'porPortedDate',
    'porUpdDate',
    'porDeadline',
    'porErr',
    'porAccType',
    'porType',
    'porRspCode',
    'donor',
    'recipient',
  ] as const;
  private readonly dateFilterParamNames = new Set<string>(['porCrDate', 'porPortedDate', 'porUpdDate', 'porDeadline']);
  private readonly sortableColumns = new Set([
    'id',
    'porType',
    'porCrDate',
    'porNumber',
    'porAccType',
    'porIdNumber',
    'porRequestId',
    'porOpd',
    'porOpr',
  ]);

  constructor(
    protected portabilityService: PortabilityService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    protected translateService: TranslateService,
    protected fb: UntypedFormBuilder
  ) {
    this.page = 1;
    this.ascending = true;
    this.predicate = 'porCrDate';
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.totalItems = 0;
  }

  loadPage(page?: number | { pageIndex?: number; pageSize?: number }, dontNavigate = false): void {
    this.requestSubscription?.unsubscribe();

    let routePage = this.page ?? 1;
    if (typeof page === 'number') {
      routePage = page;
    } else if (page?.pageIndex !== undefined) {
      routePage = page.pageIndex + 1;
    }

    routePage = Number.isFinite(routePage) ? Math.max(Math.trunc(routePage), 1) : 1;

    const requestedSize = typeof page === 'number' ? this.itemsPerPage : (page?.pageSize ?? this.itemsPerPage);
    this.itemsPerPage = requestedSize > 0 ? requestedSize : ITEMS_PER_PAGE;
    this.pageToLoad = routePage;

    const request = {
      ...this.query,
      page: routePage - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.isLoading = true;
    this.requestSubscription = this.portabilityService
      .query(request)
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (res: HttpResponse<IPortability[] | any>) => {
          this.onSuccess(res.body, res.headers, routePage, !dontNavigate);
        },
        error: () => this.onError(),
      });
  }

  ngOnInit(): void {
    this.routeStateSubscription?.unsubscribe();
    this.routeStateSubscription = combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(
      ([data, params]) => {
        const portability = data['portability'];

        if (Array.isArray(portability)) {
          this.portabilities = portability;
          this.portability = portability;
          this.dataSource.data = portability;
          return;
        }

        const configuredDefaultSort =
          typeof data['defaultSort'] === 'string'
            ? data['defaultSort']
            : 'porCrDate,asc';

        const [defaultPredicateCandidate, defaultDirectionCandidate] = configuredDefaultSort.split(',');
        const defaultPredicate = this.sortableColumns.has(defaultPredicateCandidate)
          ? defaultPredicateCandidate
          : 'porCrDate';
        const defaultAscending = defaultDirectionCandidate !== DESC;

        const pageParameter = params.get('page');
        const parsedPage = pageParameter !== null ? Number(pageParameter) : 1;
        const routePage = Number.isInteger(parsedPage) && parsedPage > 0 ? parsedPage : 1;

        const sizeParameter = params.get('size');
        const parsedSize = sizeParameter !== null ? Number(sizeParameter) : ITEMS_PER_PAGE;
        const routeSize = this.pageSizeOptions.includes(parsedSize) ? parsedSize : ITEMS_PER_PAGE;

        const requestedSort = params.get(SORT);
        const [requestedPredicate, requestedDirection] =
          requestedSort !== null
            ? requestedSort.split(',')
            : [defaultPredicate, defaultAscending ? ASC : DESC];

        const requestedSortIsValid =
          this.sortableColumns.has(requestedPredicate) &&
          (requestedDirection === ASC || requestedDirection === DESC);

        const routePredicate = requestedSortIsValid ? requestedPredicate : defaultPredicate;
        const routeAscending = requestedSortIsValid ? requestedDirection === ASC : defaultAscending;
        const routeFilterState = this.getFilterStateFromRoute(params);
        const routeFilterSignature = JSON.stringify(routeFilterState);

        const stateChanged =
          !this.routeStateInitialized ||
          routePage !== this.page ||
          routeSize !== this.itemsPerPage ||
          routePredicate !== this.predicate ||
          routeAscending !== this.ascending ||
          routeFilterSignature !== this.routeFilterSignature;

        if (!stateChanged) {
          return;
        }

        this.routeStateInitialized = true;
        this.page = routePage;
        this.itemsPerPage = routeSize;
        this.predicate = routePredicate;
        this.ascending = routeAscending;
        this.routeFilterSignature = routeFilterSignature;
        this.editForm.patchValue(routeFilterState, { emitEvent: false });
        this.applyFiltersFromForm();

        this.loadPage(routePage, true);
      }
    );
  }

  ngAfterViewInit(): void {
    this.sortSubscription?.unsubscribe();
    this.sortSubscription = this.empTbSort.sortChange.subscribe((sort: Sort) => {
      if (!sort.direction) {
        return;
      }

      this.predicate = sort.active;
      this.ascending = sort.direction === ASC;
      this.loadPage(1);
    });
  }

  ngOnDestroy(): void {
    this.requestSubscription?.unsubscribe();
    this.routeStateSubscription?.unsubscribe();
    this.sortSubscription?.unsubscribe();
  }

  clear(): void {
    this.editForm.reset();
    this.applyFiltersFromForm();
    this.routeFilterSignature = JSON.stringify(this.getCurrentFilterState());
    this.dataSource.data = [];
    this.portabilities = undefined;
    this.page = 1;
    this.pageToLoad = 1;
    this.loadPage(1);
  }

  trackId(index: number, item: IPortability): number {
    return item.id!;
  }

  delete(portability: IPortability): void {
    const modalRef = this.modalService.open(PortabilityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.portability = portability;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  showLogStatus(portability: IPortability): void {
    const modalRef = this.modalService.open(PortabilityLogDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.portability = portability;
  }

  result(value: IPortability[]): void {
    this.dataSource.data = value;
    this.portability = value;
  }

  checkExpanded(): void {
    this.expanded = !this.expanded;
  }

  search(): void {
    this.isLoading = true;
    this.dataSource.data = [];
    this.portabilities = undefined;
    this.applyFiltersFromForm();
    this.routeFilterSignature = JSON.stringify(this.getCurrentFilterState());
    this.page = 1;
    this.pageToLoad = 1;
    this.loadPage(1);
  }
  porTypeChange(): void {
    this.editForm.get('porStatus')?.setValue(null);
  }

  private getFilterStateFromRoute(params: ParamMap): Record<string, string | null> {
    const state: Record<string, string | null> = {};

    for (const name of this.filterParamNames) {
      const value = params.get(name);
      state[name] = value !== null && value.trim() !== '' ? value : null;
    }

    return state;
  }

  private getCurrentFilterState(): Record<string, string | null> {
    const state: Record<string, string | null> = {};

    for (const name of this.filterParamNames) {
      const value = this.editForm.get([name])?.value;

      if (value === null || value === undefined || value === '') {
        state[name] = null;
      } else if (this.dateFilterParamNames.has(name)) {
        const dateValue = moment(value);
        state[name] = dateValue.isValid() ? dateValue.format(DATE_FORMAT) : null;
      } else {
        state[name] = String(value);
      }
    }

    return state;
  }

  private getFilterQueryParams(): Record<string, string> {
    const state = this.getCurrentFilterState();
    const queryParams: Record<string, string> = {};

    for (const name of this.filterParamNames) {
      const value = state[name];

      if (value !== null) {
        queryParams[name] = value;
      }
    }

    return queryParams;
  }

  private applyFiltersFromForm(): void {
    this.query = {};
    if (this.editForm.get(['porRequestId'])?.value) {
      this.query['porRequestId.equals'] = this.editForm.get(['porRequestId'])?.value;
    }
    if (this.editForm.get(['porNumber'])?.value) {
      this.query['porNumber.equals'] = String(this.editForm.get(['porNumber'])?.value);
    }
    if (this.editForm.get(['porCrDate'])?.value) {
      this.query['porCrDate.greaterThanOrEqual'] =
        this.editForm.get(['porCrDate'])?.value != null
          ? moment(this.editForm.get(['porCrDate'])?.value)
              .set('h', 0)
              .set('m', 0)
              .set('s', 0)
              .format(DATE_TIME_FORMAT)
          : null;
      this.query['porCrDate.lessThan'] =
        this.editForm.get(['porCrDate'])?.value != null
          ? moment(this.editForm.get(['porCrDate'])?.value)
              .set('h', 23)
              .set('m', 59)
              .set('s', 59)
              .format(DATE_TIME_FORMAT)
          : null;
    }
    if (this.editForm.get(['porIdNumber'])?.value) {
      this.query['porIdNumber.equals'] = this.editForm.get(['porIdNumber'])?.value;
    }
    if (this.editForm.get(['porMnpid'])?.value) {
      this.query['porMnpid.equals'] = this.editForm.get(['porMnpid'])?.value;
    }

    if (this.editForm.get(['porReceiver'])?.value) {
      this.query['porOpr.equals'] = this.editForm.get(['porReceiver'])?.value;
    }
    if (this.editForm.get(['porRouting'])?.value) {
      this.query['porRouting.equals'] = this.editForm.get(['porRouting'])?.value;
    }
    if (this.editForm.get(['porStatus'])?.value) {
      this.query['porStatus.equals'] = this.editForm.get(['porStatus'])?.value;
    }
    if (this.editForm.get(['porPortedDate'])?.value) {
      this.query['porPortedDate.greaterThanOrEqual'] =
        this.editForm.get(['porPortedDate'])?.value != null
          ? moment(this.editForm.get(['porPortedDate'])?.value)
              .set('h', 0)
              .set('m', 0)
              .set('s', 0)
              .format(DATE_TIME_FORMAT)
          : null;
      this.query['porPortedDate.lessThan'] =
        this.editForm.get(['porPortedDate'])?.value != null
          ? moment(this.editForm.get(['porPortedDate'])?.value)
              .set('h', 23)
              .set('m', 59)
              .set('s', 59)
              .format(DATE_TIME_FORMAT)
          : null;
    }
    if (this.editForm.get(['porUpdDate'])?.value) {
      this.query['porUpdDate.greaterThanOrEqual'] =
        this.editForm.get(['porUpdDate'])?.value != null
          ? moment(this.editForm.get(['porUpdDate'])?.value)
              .set('h', 0)
              .set('m', 0)
              .set('s', 0)
              .format(DATE_TIME_FORMAT)
          : null;
      this.query['porUpdDate.lessThan'] =
        this.editForm.get(['porUpdDate'])?.value != null
          ? moment(this.editForm.get(['porUpdDate'])?.value)
              .set('h', 23)
              .set('m', 59)
              .set('s', 59)
              .format(DATE_TIME_FORMAT)
          : null;
    }

    if (this.editForm.get(['porDeadline'])?.value) {
      this.query['porDeadline.greaterThanOrEqual'] =
        this.editForm.get(['porDeadline'])?.value != null
          ? moment(this.editForm.get(['porDeadline'])?.value)
              .set('h', 0)
              .set('m', 0)
              .set('s', 0)
              .format(DATE_TIME_FORMAT)
          : null;
      this.query['porDeadline.lessThan'] =
        this.editForm.get(['porDeadline'])?.value != null
          ? moment(this.editForm.get(['porDeadline'])?.value)
              .set('h', 23)
              .set('m', 59)
              .set('s', 59)
              .format(DATE_TIME_FORMAT)
          : null;
    }
    if (this.editForm.get(['porErr'])?.value) {
      this.query['porErr.equals'] = this.editForm.get(['porErr'])?.value;
    }
    if (this.editForm.get(['porAccType'])?.value) {
      this.query['porAccType.equals'] = this.editForm.get(['porAccType'])?.value;
    }
    if (this.editForm.get(['porType'])?.value) {
      this.query['porType.equals'] = this.editForm.get(['porType'])?.value;
    }
    if (this.editForm.get(['porRspCode'])?.value) {
      this.query['porRspCode.equals'] = this.editForm.get(['porRspCode'])?.value;
    }

    if (this.editForm.get(['donor'])?.value) {
      this.query['porOpd.equals'] = this.editForm.get(['donor'])?.value;
    }
    if (this.editForm.get(['recipient'])?.value) {
      this.query['porOpr.equals'] = this.editForm.get(['recipient'])?.value;
    }
  }
  protected sort(): string[] {
    const direction = this.ascending ? ASC : DESC;
    const result = [(this.predicate === 'porCrDate' ? 'porCrDate' : this.predicate) + ',' + direction];

    if (this.predicate !== 'id') {
      result.push('id,' + direction);
    }

    return result;
  }



  protected onSuccess(data: IPortability[] | undefined, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/portability'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
            ...this.getFilterQueryParams(),
        },
      });
    }

    this.portabilities = data;
    this.dataSource.data = data ?? [];

    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
