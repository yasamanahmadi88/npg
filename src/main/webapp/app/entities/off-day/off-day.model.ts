import dayjs from 'dayjs';

export interface IOffDay {
  id?: number;
  offDate?: dayjs.Dayjs | null;
  fullOff?: number | null;
}

export class OffDay implements IOffDay {
  constructor(public id?: number, public offDate?: dayjs.Dayjs | null, public fullOff?: number | null) {}
}

export function getOffDayIdentifier(offDay: IOffDay): number | undefined {
  return offDay.id;
}
