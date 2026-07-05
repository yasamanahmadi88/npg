import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PortabilityLogService } from '../../portability-log/service/portability-log.service';
import { IPortability } from '../portability.model';
import { IPortabilityLog } from '../../portability-log/portability-log.model';

@Component({
  selector: 'jhi-portability-log-dialog',
  templateUrl: './portability-log-dialog.component.html',
  styleUrls: ['./portability-log-dialog.component.scss'],
  standalone: false,
})
export class PortabilityLogDialogComponent implements OnInit {
  portability?: IPortability;
  portabilityLogs?: IPortabilityLog[] | null;

  constructor(protected portabilityLogService: PortabilityLogService, protected activeModal: NgbActiveModal) {
    console.warn('PortabilityLogDialogComponent constructor');
  }

  ngOnInit(): void {
    console.warn('PortabilityLogDialogComponent ngOnInit');
    this.portabilityLogService.query({ 'porId.equals': this.portability?.id }).subscribe(res => {
      this.portabilityLogs = res.body;
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
