export interface ICheckStatus {
  id?: number;
  porStatus?: string;
  porTechStatus?: string | null;
  porErrCode?: string | null;
  porRspCode?: string | null;
  statusMessageFa?: string;
  statusMessageEn?: string | null;
}

export class CheckStatus implements ICheckStatus {
  constructor(
    public id?: number,
    public porStatus?: string,
    public porTechStatus?: string | null,
    public porErrCode?: string | null,
    public porRspCode?: string | null,
    public statusMessageFa?: string,
    public statusMessageEn?: string | null
  ) {}
}

export function getCheckStatusIdentifier(checkStatus: ICheckStatus): number | undefined {
  return checkStatus.id;
}
