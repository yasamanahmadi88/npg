import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrmToCrdbResponseMap } from '../crm-to-crdb-response-map.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { CrmToCrdbResponseMapService } from '../service/crm-to-crdb-response-map.service';
import { CrmToCrdbResponseMapDeleteDialogComponent } from '../delete/crm-to-crdb-response-map-delete-dialog.component';

@Component({
  selector: 'jhi-crm-to-crdb-response-map',
  templateUrl: './crm-to-crdb-response-map.component.html',
  styleUrls: ['./crm-to-crdb-response-map.component.scss'],
  standalone: false,
})
export class CrmToCrdbResponseMapComponent implements OnInit {
  crmToCrdbResponseMaps?: ICrmToCrdbResponseMap[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  currentSearch!: string;

  constructor(
    protected crmToCrdbResponseMapService: CrmToCrdbResponseMapService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    if (this.currentSearch) {
      this.crmToCrdbResponseMapService
        .search({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          searchText: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<ICrmToCrdbResponseMap[]>) => {
            this.isLoading = false;
            this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
          },
          () => {
            this.isLoading = false;
            this.onError();
          }
        );
    } else {
      this.crmToCrdbResponseMapService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<ICrmToCrdbResponseMap[]>) => {
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

  trackId(index: number, item: ICrmToCrdbResponseMap): number {
    return item.id!;
  }

  search(query: string): void {
    if (!query) {
      return this.clearSearch();
    }
    this.page = 0;
    this.currentSearch = query;
    this.loadPage();
  }

  clearSearch(): void {
    this.currentSearch = '';
    this.loadPage();
  }

  delete(crmToCrdbResponseMap: ICrmToCrdbResponseMap): void {
    const modalRef = this.modalService.open(CrmToCrdbResponseMapDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.crmToCrdbResponseMap = crmToCrdbResponseMap;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason: string) => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
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

  protected onSuccess(data: ICrmToCrdbResponseMap[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/crm-to-crdb-response-map'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.crmToCrdbResponseMaps = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
    this.crmToCrdbResponseMaps = [];
  }
}
