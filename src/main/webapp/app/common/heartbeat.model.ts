export interface IHeartbeat {
  status?: string;
  partyId?: string;
  service?: string;
}

export class Heartbeat implements IHeartbeat {
  constructor(public status?: string, public partyId?: string, public service?: string) {}
}
