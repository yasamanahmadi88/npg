import { IResource } from 'app/entities/resource/resource.model';
import { Verb } from 'app/entities/enumerations/verb.model';
import { IAuthority } from '../authority/authority.model';

export interface IResourceAuthority {
  id?: number;
  verb?: Verb;
  authorityId?: number;
  resource?: IResource | null;
  authority?: IAuthority | null;
  authorityDisplayName?: string;
  authorityName?: string;
  resourceDisplayName?: string;
  resourceId?: number;
  resourceName?: string;
}

export class ResourceAuthority implements IResourceAuthority {
  constructor(
    public id?: number,
    public verb?: Verb,
    public authorityId?: number,
    public resource?: IResource | null,
    public authority?: IAuthority | null,
    public authorityDisplayName?: string,
    public authorityName?: string,
    public resourceDisplayName?: string,
    public resourceId?: number,
    public resourceName?: string
  ) {}
}

export function getResourceAuthorityIdentifier(resourceAuthority: IResourceAuthority): number | undefined {
  return resourceAuthority.id;
}
