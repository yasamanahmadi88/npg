import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResourceAuthority } from '../resource-authority.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ResourceAuthorityService } from '../service/resource-authority.service';
import { ResourceAuthorityDeleteDialogComponent } from '../delete/resource-authority-delete-dialog.component';

@Component({
  selector: 'jhi-resource-authority',
  templateUrl: './resource-authority.component.html',
  standalone: false,
})
export class ResourceAuthorityComponent implements OnInit {
  resourceAuthorities?: IResourceAuthority[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  currentSearch!: string;

  constructor(
    protected resourceAuthorityService: ResourceAuthorityService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    if (this.currentSearch) {
      this.resourceAuthorityService
        .search({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          searchText: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<IResourceAuthority[]>) => {
            this.isLoading = false;
            this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
          },
          () => {
            this.isLoading = false;
            this.onError();
          }
        );
    } else {
      this.resourceAuthorityService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IResourceAuthority[]>) => {
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

  trackId(index: number, item: IResourceAuthority): number {
    return item.id!;
  }

  delete(resourceAuthority: IResourceAuthority): void {
    const modalRef = this.modalService.open(ResourceAuthorityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resourceAuthority = resourceAuthority;
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

  clearSearch(): void {
    this.currentSearch = '';
    this.loadPage();
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IResourceAuthority[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/resource-authority'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.resourceAuthorities = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
