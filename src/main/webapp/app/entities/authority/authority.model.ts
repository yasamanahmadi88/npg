export interface IAuthority {
  id?: number;
  name?: string;
  displayName?: string;
  parentId?: number;
  parentDisplayName?: string;
  parentName?: string;
}

export class Authority implements IAuthority {
  constructor(
    public id?: number,
    public name?: string,
    public displayName?: string,
    public parentId?: number,
    public parentDisplayName?: string,
    public parentName?: string
  ) {}
}
