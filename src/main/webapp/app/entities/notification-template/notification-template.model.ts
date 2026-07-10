export interface INotificationTemplate {
  id?: number;
  templateCode?: string | null;
  language?: string | null;
  content?: string | null;
  type?: string | null;
}

export class NotificationTemplate implements INotificationTemplate {
  constructor(
    public id?: number,
    public templateCode?: string | null,
    public language?: string | null,
    public content?: string | null,
    public type?: string | null
  ) {}
}

export function getNotificationTemplateIdentifier(notificationTemplate: INotificationTemplate): number | undefined {
  return notificationTemplate.id;
}
