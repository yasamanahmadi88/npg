import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IFileReportGenerationLog } from '../file-report-generation-log.model';
import { ASC, DESC } from 'app/config/pagination.constants';
import { FileReportGenerationLogService } from '../service/file-report-generation-log.service';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { EventManager } from '../../../core/util/event-manager.service';
import { MatTableDataSource } from '@angular/material/table';
import moment from 'moment';

@Component({
  selector: 'jhi-file-report-generation-log',
  templateUrl: './file-report-generation-log.component.html',
  styleUrls: ['./file-report-generation-log.scss'],
  standalone: false,
})
export class FileReportGenerationLogComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild(MatSort) empTbSort?: MatSort;
  @ViewChild(MatPaginator) paginator?: MatPaginator;

  searchForm: UntypedFormGroup;
  dataSource = new MatTableDataSource<IFileReportGenerationLog>([]);
  fileReportGenerationLogs?: IFileReportGenerationLog[] = [];

  isLoading = false;
  totalItems = 0;
  itemsPerPage = 10;
  page = 0;
  predicate = 'id';
  ascending = true;
  ngbPaginationPage = 1;
  expanded = false;
  isDataLoaded = false;
  displayedColumns = ['fileName', 'rowNumber', 'porNumber', 'content', 'reportDate', 'action'];

  private query: any = {};
  private sortSubscription?: Subscription;

  constructor(
    protected fileReportGenerationLogService: FileReportGenerationLogService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    protected fb: UntypedFormBuilder,
    protected eventManager: EventManager
  ) {
    this.searchForm = this.initializeSearchForm();
  }
  goHome(): void {
    this.router.navigate(['/']);
  }

  checkExpanded(): void {
    this.expanded = !this.expanded;
  }

  clear(): void {
    this.searchForm.reset();
    this.query = {};
    this.page = 0;
    this.fileReportGenerationLogs = undefined;
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

    this.fileReportGenerationLogService.query(this.query).subscribe({
      next: (res: HttpResponse<IFileReportGenerationLog[]>) => {
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

  ngOnInit(): void {
    this.expanded = true;
  }

  ngAfterViewInit(): void {
    const sort = this.empTbSort;

    if (sort) {
      this.dataSource.sort = sort;

      this.sortSubscription = sort.sortChange.subscribe((sortEvent: Sort) => {
        this.handleSortChange(sortEvent);
      });
    }

    this.updatePaginator();
    this.loadInitialData();
  }

  ngOnDestroy(): void {
    this.sortSubscription?.unsubscribe();
  }
  // search(): void {
  //   this.expanded = false;
  //   this.isDataLoaded = false;
  //   this.page = 0;
  //   this.buildQuery();
  //
  //   this.fileReportGenerationLogService.query(this.query).subscribe({
  //     next: (res: HttpResponse<IFileReportGenerationLog[]>) => {
  //       this.isLoading = false;
  //       this.isDataLoaded = true;
  //       this.onSuccess(res.body, res.headers);
  //
  //       this.paginator.firstPage();
  //     },
  //     error: () => {
  //       this.isDataLoaded = true;
  //       this.isLoading = false;
  //       this.onError();
  //     },
  //   });
  // }

  search(): void {
    this.expanded = false;
    this.isDataLoaded = false;
    this.isLoading = true;
    this.page = 0;
    this.buildQuery();
    this.fileReportGenerationLogService.query(this.query).subscribe({
      next: (res: HttpResponse<IFileReportGenerationLog[]>) => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onSuccess(res.body, res.headers);
        this.paginator?.firstPage();
      },
      error: () => {
        this.isLoading = false;
        this.isDataLoaded = true;
        this.onError();
      },
    });
  }
  trackId(index: number, item: IFileReportGenerationLog): number {
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
      reportName: [''],
      reportDate: [''],
      porNumber: [''],
    });
  }

  private loadInitialData(): void {
    this.isLoading = true;
    this.isDataLoaded = false;
    this.buildQuery();

    this.fileReportGenerationLogService.query(this.query).subscribe({
      next: (res: HttpResponse<IFileReportGenerationLog[]>) => {
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

    if (formValues.reportName) {
      this.query['reportName.equals'] = formValues.reportName;
    }

    // if (formValues.reportDate) {
    //   const reportDate = moment(formValues.reportDate);
    //   this.query['reportDate.equals'] = reportDate.format(DATE_TIME_FORMAT);
    // }

    if (formValues.reportDate) {
      const startOfDay = moment(formValues.reportDate).startOf('day');
      const endOfDay = moment(formValues.reportDate).endOf('day');

      this.query['reportDate.greaterThanOrEqual'] = startOfDay.format('YYYY-MM-DDTHH:mm:ss');
      this.query['reportDate.lessThanOrEqual'] = endOfDay.format('YYYY-MM-DDTHH:mm:ss');
    }

    if (formValues.porNumber) {
      this.query['porNumber.equals'] = formValues.porNumber;
    }
  }

  private handleSortChange(sort: Sort): void {
    this.predicate = sort.active;
    this.ascending = sort.direction === ASC;
    this.page = 0;
    this.search();
  }

  private updatePaginator(): void {
    const paginator = this.paginator;

    if (!paginator) {
      return;
    }
    paginator.length = this.totalItems;
    paginator.pageIndex = this.page;
    paginator.pageSize = this.itemsPerPage;

    paginator.pageSizeOptions = [10, 20, 50, 100];
  }

  private onSuccess(data: IFileReportGenerationLog[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.fileReportGenerationLogs = data ?? [];
    this.dataSource.data = this.fileReportGenerationLogs;
    this.dataSource.paginator = null;
    this.updatePaginator();
  }

  private onError(): void {
    this.ngbPaginationPage = 1;
  }
}
