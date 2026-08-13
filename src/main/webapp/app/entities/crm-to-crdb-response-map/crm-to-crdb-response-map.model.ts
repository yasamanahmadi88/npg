export interface ICrmToCrdbResponseMap {
  id?: number;
  code?: string;
  description?: string;
  crmInterface?: string;
  rspCode?: string;
  rspNote?: string;
}

export class CrmToCrdbResponseMap implements ICrmToCrdbResponseMap {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public crmInterface?: string,
    public rspCode?: string,
    public rspNote?: string
  ) {}
}

export function getCrmToCrdbResponseMapIdentifier(crmToCrdbResponseMap: ICrmToCrdbResponseMap): number | undefined {
  return crmToCrdbResponseMap.id;
}
