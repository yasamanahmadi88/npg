export interface IDayOfWeekTimeFrame {
  id?: number;
  day?: number | any;
  begin?: number | null;
  end?: number | null;
}

export class DayOfWeekTimeFrame implements IDayOfWeekTimeFrame {
  constructor(public id?: number, public day?: number | any, public begin?: number | null, public end?: number | null) {}
}

export function getDayOfWeekTimeFrameIdentifier(dayOfWeekTimeFrame: IDayOfWeekTimeFrame): number | undefined {
  return dayOfWeekTimeFrame.id;
}
