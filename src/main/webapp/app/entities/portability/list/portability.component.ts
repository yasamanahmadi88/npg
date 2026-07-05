import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
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
import { EventManager } from '../../../core/util/event-manager.service';
import { MatSort, Sort } from '@angular/material/sort';

@Component({
  selector: 'jhi-portability',
  templateUrl: './portability.component.html',
  styleUrls: ['./portability.scss'],
  standalone: false,
})
export class PortabilityComponent implements OnInit {
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
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  currentSearch!: string;
  eventSubscriber!: Subscription;
  displayedColumns = ['id', 'porType', 'porCrDate', 'porNumber', 'porAccType', 'porIdNumber', 'porRequestId', 'porOpd', 'porOpr', 'action'];
  dataSource: MatTableDataSource<IPortability[]> | any = [];
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

  constructor(
    protected portabilityService: PortabilityService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    protected translateService: TranslateService,
    protected fb: UntypedFormBuilder,
    protected eventManager: EventManager
  ) {
    this.page = 1;
    this.ascending = false;
    this.predicate = 'porCrDate';
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.totalItems = 0;
  }

  loadPage(page?: any, dontNavigate?: boolean): void {
    this.isLoading = true;
    this.pageToLoad = page.pageIndex ?? this.page ?? 1;
    this.query['page'] = this.pageToLoad;
    this.query['size'] = page.pageSize;
    this.query['sort'] = this.sort();
    this.portabilityService.query(this.query).subscribe(
      (res: HttpResponse<IPortability[] | any>) => {
        this.isLoading = false;
        this.onSuccess2(res.body, res.headers, this.pageToLoad, false);
      },
      () => {
        this.isLoading = false;
        this.onError();
      }
    );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ portability }) => {
      if (portability && Array.isArray(portability) && portability.length > 0) {
        this.portabilities = portability;
        this.portability = portability;
      } else {
        this.portabilities = [];
        this.portability = [];
      }
      this.dataSource = new MatTableDataSource<IPortability>(this.portability ?? []);
    });
  }

  clear(): void {
    this.editForm.reset();
    this.query = {};
    this.router.navigate(['./portability']);
    this.portabilities = undefined;
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
    this.dataSource = undefined;
    this.portabilities = undefined;
    this.query = {};

    if (this.editForm.get(['porRequestId'])?.value) {
      this.query['porRequestId.equals'] = this.editForm.get(['porRequestId'])?.value;
    }
    if (this.editForm.get(['porNumber'])?.value) {
      this.query['porNumber.equals'] = String(this.editForm.get(['porNumber'])?.value);
    }
    if (this.editForm.get(['porCrDate'])?.value) {
      this.query['porCrDateSearch.greaterThanOrEqual'] =
        this.editForm.get(['porCrDate'])?.value != null
          ? moment(this.editForm.get(['porCrDate'])?.value)
              .set('h', 0)
              .set('m', 0)
              .set('s', 0)
              .format(DATE_TIME_FORMAT)
          : null;
      this.query['porCrDateSearch.lessThan'] =
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

    this.query['size'] = 20;
    this.query['sort'] = this.sort();
    this.portabilityService.query(this.query).subscribe(
      (res: HttpResponse<IPortability[] | any>) => {
        this.isLoading = false;

        this.dataSource = new MatTableDataSource(this.portabilities);
        this.onSuccess2(res.body, res.headers, 1, false);
        this.dataSource.sort = this.empTbSort;
        this.dataSource.sort.sortChange.subscribe((sort: Sort) => {
          this.isLoading = true;
          this.predicate = sort.active;
          this.ascending = sort.direction !== ASC;
          this.query['sort'] = this.sort();
          this.portabilityService.query(this.query).subscribe(
            (res2: HttpResponse<IPortability[] | any>) => {
              this.isLoading = false;
              this.onSuccess2(res2.body, res2.headers, this.pageToLoad, false);
            },
            error => {
              this.isLoading = false;
            }
          );
        });
      },
      () => {
        this.isLoading = false;
        this.editForm.reset();
        this.query = {};
        this.onError();
      }
    );
    /* this.portabilityService.query(query).subscribe(this.onSuccess2(res => {
       this.portabilities = res.body;
       this.dataSource = res.body;
     });*/
  }

  porTypeChange(): void {
    this.editForm.get('porStatus')?.setValue(null);
  }

  protected sort(): string[] {
    const result = [(this.predicate === 'porCrDate' ? 'porCrDateSearch' : this.predicate) + ',' + (this.ascending ? DESC : ASC)];
    if (this.predicate !== 'id') {
      result.push('id,' + (this.ascending ? DESC : ASC));
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

  protected onSuccess(data: IPortability[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/portability'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.portabilities = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onSuccess2(data: IPortability[] | undefined, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/portability'], {
        queryParams: {
          page: this.page,
          size: 2,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }

    this.portabilities = data;
    this.dataSource.data = data;

    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
