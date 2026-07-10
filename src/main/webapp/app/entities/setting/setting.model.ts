export interface ISetting {
  id?: number;
  name?: string;
  key?: string;
  value?: string;
}

export class Setting implements ISetting {
  constructor(public id?: number, public name?: string, public key?: string, public value?: string) {}
}

export function getSettingIdentifier(setting: ISetting): number | undefined {
  return setting.id;
}
