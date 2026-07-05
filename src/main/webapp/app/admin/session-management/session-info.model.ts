import { Dayjs } from 'dayjs';
import dayjs from 'dayjs';

export interface ISessionInfo {
  principal?: any;
  sessionId?: string;
  ip?: string;
  username?: string;
  jwtToken?: string;
  loginDate?: dayjs.Dayjs | null;
  logoutDate?: dayjs.Dayjs | null;
  validToken?: boolean;
  lastActionDate?: dayjs.Dayjs | null;
}

export class SessionInfo implements ISessionInfo {
  constructor(
    public principal?: any,
    public sessionId?: string,
    public ip?: string,
    public username?: string,
    public jwtToken?: string,
    public loginDate?: dayjs.Dayjs | null,
    public logoutDate?: dayjs.Dayjs | null,
    public validToken?: boolean,
    public lastActionDate?: dayjs.Dayjs | null
  ) {}
}
