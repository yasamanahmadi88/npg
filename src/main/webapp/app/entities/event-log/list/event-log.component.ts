import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventLog } from '../event-log.model';

import { ASC, DESC, SORT } from 'app/config/pagination.constants';
import { EventLogService } from '../service/event-log.service';
import { TranslateService } from '@ngx-translate/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { EventManager } from '../../../core/util/event-manager.service';
import { MatSort, Sort } from '@angular/material/sort';
import { ECalendarValue, IDatePickerConfig } from 'app/shared/date-picker/dp-date-picker-compat.types';
import { DATE_FORMAT, DATE_TIME_FORMAT } from '../../../config/input.constants';
import { MatTableDataSource } from '@angular/material/table';
import moment from 'moment';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'jhi-event-log',
  templateUrl: './event-log.component.html',
  styleUrls: ['./event-log.component.scss'],
  standalone: false,
})
export class EventLogComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild(MatSort) empTbSort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  searchForm: UntypedFormGroup;
  dataSource = new MatTableDataSource<IEventLog | any>([]);
  dpConfig: IDatePickerConfig;

  eventLogs: IEventLog[] | any = [];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = 10;
  page = 0;
  predicate = 'id';
  ascending = true;
  ngbPaginationPage = 1;
  expanded = false;
  isDataLoaded = false;
  displayedColumns = [
    'conversationId',
    'sender',
    'receiver',
    'httpStatus',
    'flg0Ordinary1Exception',
    'message',
    'insertTimestamp',
    'action',
  ];

  private query: any = {};
  private sortSubscription?: Subscription;

  constructor(
    protected eventLogService: EventLogService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    protected translateService: TranslateService,
    protected fb: UntypedFormBuilder,
    protected eventManager: EventManager
  ) {
    this.searchForm = this.initializeSearchForm();
    this.dpConfig = this.initializeDatePickerConfig();
  }

  goHome(): void {
    this.router.navigate(['/']);
  }

  // ============= Lifecycle Hooks =============
  ngOnInit(): void {
    this.expanded = true;
    this.loadInitialData();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.empTbSort;

    this.sortSubscription = this.empTbSort.sortChange.subscribe((sort: Sort) => {
      this.handleSortChange(sort);
    });

    this.updatePaginator();
  }

  ngOnDestroy(): void {
    this.sortSubscription?.unsubscribe();
  }

  checkExpanded(): void {
    this.expanded = !this.expanded;
  }

  clear(): void {
    this.searchForm.reset();
    this.query = {};
    this.page = 0;
    this.eventLogs = undefined;
    this.dataSource.data = [];
    this.totalItems = 0;
    this.updatePaginator();
  }

  loadPage(event: PageEvent): void {
    this.isLoading = true;
    this.isDataLoaded = false;
    this.page = event.pageIndex;
    this.itemsPerPage = event.pageSize;
    this.buildQuery();

    this.eventLogService.query(this.query).subscribe({
      next: (res: HttpResponse<IEventLog[]>) => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onSuccess(res.body, res.headers);
      },
      error: () => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onError();
      },
    });
  }

  search(): void {
    this.isLoading = true;
    this.expanded = false;
    this.isDataLoaded = false;
    this.page = 0;
    this.buildQuery();

    this.eventLogService.query(this.query).subscribe({
      next: (res: HttpResponse<IEventLog[]>) => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onSuccess(res.body, res.headers);

        this.paginator.firstPage();
      },
      error: () => {
        this.isDataLoaded = true;
        this.isLoading = false;
        this.onError();
      },
    });
  }

  trackId(index: number, item: IEventLog): number {
    return item.id!;
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private initializeSearchForm(): UntypedFormGroup {
    return this.fb.group({
      conversationId: [''],
      sender: [''],
      receiver: [''],
      message: [''],
      flg0Ordinary1Exception: [''],
      httpStatus: [''],
      startDate: [''],
      endDate: [''],
    });
  }

  private initializeDatePickerConfig(): IDatePickerConfig {
    return {
      disableKeypress: false,
      showMultipleYearsNavigation: true,
      format: DATE_FORMAT,
      multipleYearsNavigateBy: 3,
      monthBtnFormat: 'MMMM',
      showWeekNumbers: false,
      showTwentyFourHours: true,
      showSeconds: true,
      drops: 'up',
      locale: this.translateService.currentLang,
      returnedValueType: ECalendarValue.Moment,
    };
  }

  private loadInitialData(): void {
    this.isLoading = true;
    this.isDataLoaded = false;
    this.buildQuery();

    this.eventLogService.query(this.query).subscribe({
      next: (res: HttpResponse<IEventLog[]>) => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onSuccess(res.body, res.headers);
      },
      error: () => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onError();
      },
    });
  }

  private buildQuery(): void {
    this.query = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    const formValues = this.searchForm.value;

    if (formValues.conversationId) {
      this.query['conversationId.equals'] = formValues.conversationId;
    }
    if (formValues.sender) {
      this.query['sender.equals'] = formValues.sender;
    }
    if (formValues.receiver) {
      this.query['receiver.equals'] = formValues.receiver;
    }
    if (formValues.flg0Ordinary1Exception) {
      this.query['flg0Ordinary1Exception.equals'] = formValues.flg0Ordinary1Exception;
    }
    if (formValues.message) {
      this.query['message.equals'] = formValues.message;
    }
    if (formValues.httpStatus) {
      this.query['httpStatus.equals'] = formValues.httpStatus;
    }
    if (formValues.startDate) {
      const date = moment(formValues.startDate);
      this.query['insertTimestamp.greaterThanOrEqual'] = date.startOf('day').format(DATE_TIME_FORMAT);
    }
    if (formValues.endDate) {
      const date = moment(formValues.endDate);
      this.query['insertTimestamp.lessThan'] = date.endOf('day').format(DATE_TIME_FORMAT);
    }
  }

  private handleSortChange(sort: Sort): void {
    this.predicate = sort.active;
    this.ascending = sort.direction === ASC;
    this.page = 0;
    this.search();
  }

  private updatePaginator(): void {
    this.paginator.length = this.totalItems;
    this.paginator.pageIndex = this.page;
    this.paginator.pageSize = this.itemsPerPage;

    this.paginator.pageSizeOptions = [10, 20, 50, 100];
  }

  private onSuccess(data: IEventLog[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.eventLogs = data ?? [];

    this.dataSource.data = this.eventLogs;

    this.dataSource.paginator = null;
    this.isLoading = false;
    this.updatePaginator();
  }

  private onError(): void {
    this.ngbPaginationPage = 1;
    this.eventLogs = [];
    this.dataSource.data = [];
    this.isDataLoaded = true;
    this.isLoading = false;
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe({
      next: ([data, params]) => {
        const pageParam = params.get('page');
        const pageNumber = pageParam !== null ? +pageParam : 1;
        const sortParam = params.get(SORT);
        const defaultSort = data['defaultSort'];
        const sort = (sortParam ?? defaultSort).split(',');
        const predicate = sort[0];
        const ascending = sort[1] === ASC;

        if (pageNumber !== this.page + 1 || predicate !== this.predicate || ascending !== this.ascending) {
          this.predicate = predicate;
          this.ascending = ascending;
          const pageEvent: PageEvent = {
            pageIndex: pageNumber - 1,
            pageSize: this.itemsPerPage,
            length: this.totalItems,
          };
          this.loadPage(pageEvent);
        }
      },
    });
  }
}
