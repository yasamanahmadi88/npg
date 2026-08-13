import { IResourceAuthority } from 'app/entities/resource-authority/resource-authority.model';
import { ResourceType } from 'app/entities/enumerations/resource-type.model';

export interface IResource {
  id?: number;
  name?: string;
  displayName?: string;
  apiUri?: string;
  resourceType?: ResourceType;
  resourceAuthorities?: IResourceAuthority[] | null;
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public name?: string,
    public displayName?: string,
    public apiUri?: string,
    public resourceType?: ResourceType,
    public resourceAuthorities?: IResourceAuthority[] | null
  ) {}
}

export function getResourceIdentifier(resource: IResource): number | undefined {
  return resource.id;
}
