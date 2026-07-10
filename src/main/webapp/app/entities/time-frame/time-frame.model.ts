export interface ITimeFrame {
  id?: number;
  begin?: number | null;
  end?: number | null;
  offDayId?: number | null;
}

export class TimeFrame implements ITimeFrame {
  constructor(public id?: number, public begin?: number | null, public end?: number | null, public offDayId?: number | null) {}
}

export function getTimeFrameIdentifier(timeFrame: ITimeFrame): number | undefined {
  return timeFrame.id;
}
