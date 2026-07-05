import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOffDay, OffDay } from '../off-day.model';
import { OffDayService } from '../service/off-day.service';

@Component({
  selector: 'jhi-off-day-update',
  templateUrl: './off-day-update.component.html',
  standalone: false,
})
export class OffDayUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    offDate: [],
    fullOff: [],
  });

  constructor(protected offDayService: OffDayService, protected activatedRoute: ActivatedRoute, protected fb: UntypedFormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offDay }) => {
      if (offDay.id === undefined) {
        const today = dayjs().startOf('day');
        offDay.offDate = today;
      }

      this.updateForm(offDay);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offDay = this.createFromForm();
    if (offDay.id !== undefined) {
      this.subscribeToSaveResponse(this.offDayService.update(offDay));
    } else {
      this.subscribeToSaveResponse(this.offDayService.create(offDay));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffDay>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(offDay: IOffDay): void {
    this.editForm.patchValue({
      id: offDay.id,
      offDate: offDay.offDate ? offDay.offDate.format(DATE_TIME_FORMAT) : null,
      fullOff: offDay.fullOff,
    });
  }

  protected createFromForm(): IOffDay {
    return {
      ...new OffDay(),
      id: this.editForm.get(['id'])!.value,
      offDate: this.editForm.get(['offDate'])!.value ? dayjs(this.editForm.get(['offDate'])!.value, DATE_TIME_FORMAT) : undefined,
      fullOff: this.editForm.get(['fullOff'])!.value,
    };
  }
}
