export interface IServiceMonitor {
  id?: string;
  displayName?: string;
  name?: string;
  version?: string;
  partyId?: string;
  heartbeatStatus?: string;
  heartbeatDesc?: string;
}

export class ServiceMonitor implements IServiceMonitor {
  constructor(
    public id?: string,
    public displayName?: string,
    public name?: string,
    public version?: string,
    public partyId?: string,
    public heartbeatStatus?: string,
    public heartbeatDesc?: string
  ) {}
}
