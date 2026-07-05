package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.NotificationTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationTemplateEntity} and its DTO {@link NotificationTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificationTemplateMapper extends EntityMapper<NotificationTemplateDTO, NotificationTemplateEntity> {}


