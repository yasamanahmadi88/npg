export interface IBusinessConfig {
  id?: string;
  value?: string;
}

export class BusinessConfig implements IBusinessConfig {
  constructor(public id?: string, public value?: string) {}
}

export function getBusinessConfigIdentifier(businessConfig: IBusinessConfig): string | undefined {
  return businessConfig.id;
}
