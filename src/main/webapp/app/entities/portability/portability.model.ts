import dayjs from 'dayjs';

export interface IPortability {
  id?: number;
  porRequestId?: string | null;
  porNumber?: string | null;
  porLegalTerm?: number | null;
  porOpr?: string | null;
  porAccType?: string | null;
  porIdNumber?: string | null;
  porContactNumber?: string | null;
  porStatus?: string | null;
  porPortedDate?: dayjs.Dayjs | null;
  porRouting?: string | null;
  porType?: string | null;
  porOpOrg?: string | null;
  porRspCode?: string | null;
  porRspNote?: string | null;
  porCancelNote?: string | null;
  porMnpid?: string | null;
  portationDate?: dayjs.Dayjs | null;
  portaCode?: string | null;
  mvno?: string | null;
  context?: string | null;
  porErrCode?: string | null;
  porErrMessage?: string | null;
  porOpd?: string | null;
  porNumType?: string | null;
  porNote?: string | null;
  porDeadline?: dayjs.Dayjs | null;
  porResponseTimestamp?: number | null;
  porEligible?: number | null;
  porBillingOk?: number | null;
  intermediaryActionState?: string | null;
  porCrDate?: dayjs.Dayjs | null;
  porUpdDate?: dayjs.Dayjs | null;
  porTechStatus?: string | null;
  porTechDeadline?: dayjs.Dayjs | null;
  needManualRetry?: number | null;
  refPorId?: number | null;
  retryCount?: number;
  expanded?: boolean;
}

export class Portability implements IPortability {
  constructor(
    public id?: number,
    public porRequestId?: string | null,
    public porNumber?: string | null,
    public porLegalTerm?: number | null,
    public porOpr?: string | null,
    public porAccType?: string | null,
    public porIdNumber?: string | null,
    public porContactNumber?: string | null,
    public porStatus?: string | null,
    public porPortedDate?: dayjs.Dayjs | null,
    public porRouting?: string | null,
    public porType?: string | null,
    public porOpOrg?: string | null,
    public porRspCode?: string | null,
    public porRspNote?: string | null,
    public porCancelNote?: string | null,
    public porMnpid?: string | null,
    public portationDate?: dayjs.Dayjs | null,
    public portaCode?: string | null,
    public mvno?: string | null,
    public context?: string | null,
    public porErrCode?: string | null,
    public porErrMessage?: string | null,
    public porOpd?: string | null,
    public porNumType?: string | null,
    public porNote?: string | null,
    public porDeadline?: dayjs.Dayjs | null,
    public porResponseTimestamp?: number | null,
    public porEligible?: number | null,
    public porBillingOk?: number | null,
    public intermediaryActionState?: string | null,
    public porCrDate?: dayjs.Dayjs | null,
    public porUpdDate?: dayjs.Dayjs | null,
    public porTechStatus?: string | null,
    public porTechDeadline?: dayjs.Dayjs | null,
    public needManualRetry?: number | null,
    public refPorId?: number | null,
    public retryCount?: number,
    public expanded?: boolean
  ) {
    this.expanded = false;
  }
}

export function getPortabilityIdentifier(portability: IPortability): number | undefined {
  return portability.id;
}
