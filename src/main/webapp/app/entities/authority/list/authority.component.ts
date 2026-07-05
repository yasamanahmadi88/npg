import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthorityService } from '../service/authority.service';
import { AuthorityDeleteDialogComponent } from '../delete/authority-delete-dialog.component';
import { IAuthority } from '../authority.model';
import { ITEMS_PER_PAGE } from '../../../config/pagination.constants';

@Component({
  selector: 'jhi-authority',
  templateUrl: './authority.component.html',
  standalone: false,
})
export class AuthorityComponent implements OnInit, OnDestroy {
  isLoading = false;
  authorities?: IAuthority[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  currentSearch: any;

  constructor(
    protected authorityService: AuthorityService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    // protected eventManager: JhiEventManager,
    protected modalService: NgbModal // protected toastr: ToastrService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? (this.page || 1);
    if (this.currentSearch) {
      this.authorityService
        .search({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          searchText: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<IAuthority[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          (err: HttpErrorResponse) => this.onError(err)
        );
      return;
    } else {
      this.authorityService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IAuthority[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          (err: HttpErrorResponse) => this.onError(err)
        );
    }
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInAuthorities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      // this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAuthority): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAuthorities(): void {
    // this.eventSubscriber = this.eventManager.subscribe('authorityListModification', () => this.loadPage());
  }

  delete(authority: IAuthority): void {
    const modalRef = this.modalService.open(AuthorityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.authority = authority;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  search(query: string): void {
    if (!query || query?.length === 0) {
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

  protected onSuccess(data: IAuthority[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.isLoading = false;
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/authority'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.authorities = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(error: any): void {
    this.isLoading = false;
    this.ngbPaginationPage = this.page || 1;
    // this.toastr.error(error.detail.message, error.message);
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
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
    }).subscribe();
  }
}
