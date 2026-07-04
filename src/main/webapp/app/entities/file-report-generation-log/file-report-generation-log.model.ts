import dayjs from 'dayjs';

export interface IFileReportGenerationLog {
  id?: number;
  reportName?: string;
  reportDate?: dayjs.Dayjs;
  fileName?: string;
  rowNumber?: number;
  porNumber?: string;
  content?: string;
}

export class FileReportGenerationLog implements IFileReportGenerationLog {
  constructor(
    public id?: number,
    public reportName?: string,
    public reportDate?: dayjs.Dayjs,
    public fileName?: string,
    public rowNumber?: number,
    public porNumber?: string,
    public content?: string
  ) {}
}

export function getFileReportGenerationLogIdentifier(fileReportGenerationLog: IFileReportGenerationLog): number | undefined {
  return fileReportGenerationLog.id;
}
