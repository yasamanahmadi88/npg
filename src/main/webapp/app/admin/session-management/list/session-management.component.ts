import { Component, OnInit } from '@angular/core';
import { SessionManagementService } from '../session-management.service';
import { ISessionInfo } from '../session-info.model';

@Component({
  selector: 'jhi-session-management',
  templateUrl: './session-management.component.html',
  styleUrls: ['./session-management.component.scss'],
  standalone: false,
})
export class SessionManagementComponent implements OnInit {
  isLoading = false;
  sessionInfos?: ISessionInfo[] | null;

  constructor(private sessionManagementService: SessionManagementService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  delete(session: ISessionInfo): void {
    if (session.jwtToken != null) {
      this.sessionManagementService.removeSession(session.jwtToken).subscribe(
        () => {
          this.loadAll();
        },
        error => {
          console.error(error.message);
        }
      );
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.sessionManagementService.fetchSessions().subscribe(
      resp => {
        this.sessionInfos = resp.body;
        this.isLoading = false;
      },
      error => (this.isLoading = false)
    );
  }
}
