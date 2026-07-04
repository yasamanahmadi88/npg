package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.EventLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventLogEntity} and its DTO {@link EventLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventLogMapper extends EntityMapper<EventLogDTO, EventLogEntity> {}


