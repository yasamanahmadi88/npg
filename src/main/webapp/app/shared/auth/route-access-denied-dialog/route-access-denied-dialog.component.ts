import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-route-access-denied-dialog',
  templateUrl: './route-access-denied-dialog.component.html',
  styleUrls: ['./route-access-denied-dialog.component.scss'],
  standalone: false,
})
export class RouteAccessDeniedDialogComponent implements OnInit {
  @Input() description?: string;
  constructor(public activeModal: NgbActiveModal) {
    console.warn('In route-access-denied-dialog');
  }

  ngOnInit(): void {
    console.warn('In route-access-denied-dialog');
  }
}
