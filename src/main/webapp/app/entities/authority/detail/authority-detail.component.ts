import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAuthority } from '../authority.model';

@Component({
  selector: 'jhi-authority-detail',
  templateUrl: './authority-detail.component.html',
  standalone: false,
})
export class AuthorityDetailComponent implements OnInit {
  authority: IAuthority | any | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authority }) => {
      this.authority = authority;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
