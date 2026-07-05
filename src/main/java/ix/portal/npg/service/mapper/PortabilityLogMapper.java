package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.PortabilityLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PortabilityLogEntity} and its DTO {@link PortabilityLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PortabilityLogMapper extends EntityMapper<PortabilityLogDTO, PortabilityLogEntity> {}


