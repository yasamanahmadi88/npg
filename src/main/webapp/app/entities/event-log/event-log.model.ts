import dayjs from 'dayjs';

export interface IEventLog {
  id?: number;
  conversationId?: string | null;
  sender?: string | null;
  receiver?: string | null;
  message?: string | null;
  requestBody?: string | null;
  responseBody?: string | null;
  flg0Ordinary1Exception?: number;
  eventSource?: string;
  exceptionBody?: string | null;
  httpStatus?: string | null;
  expanded?: boolean | null;
  insertTimestamp?: dayjs.Dayjs;
}

export class EventLog implements IEventLog {
  constructor(
    public id?: number,
    public conversationId?: string | null,
    public sender?: string | null,
    public receiver?: string | null,
    public message?: string | null,
    public requestBody?: string | null,
    public responseBody?: string | null,
    public flg0Ordinary1Exception?: number,
    public eventSource?: string,
    public exceptionBody?: string | null,
    public httpStatus?: string | null,
    public expanded?: boolean | null,
    public insertTimestamp?: dayjs.Dayjs
  ) {}
}

export function getEventLogIdentifier(eventLog: IEventLog): number | undefined {
  return eventLog.id;
}
