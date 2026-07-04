import dayjs from 'dayjs';

export interface IUndifinedStatus {
  id?: number;
  porStatus?: string | null;
  porTechStatus?: string | null;
  porErrCode?: string | null;
  porRspCode?: string | null;
  porRequestId?: string | null;
  insertDate?: dayjs.Dayjs;
}

export class UndifinedStatus implements IUndifinedStatus {
  constructor(
    public id?: number,
    public porStatus?: string | null,
    public porTechStatus?: string | null,
    public porErrCode?: string | null,
    public porRspCode?: string | null,
    public porRequestId?: string | null,
    public insertDate?: dayjs.Dayjs
  ) {}
}

export function getUndifinedStatusIdentifier(undifinedStatus: IUndifinedStatus): number | undefined {
  return undifinedStatus.id;
}
