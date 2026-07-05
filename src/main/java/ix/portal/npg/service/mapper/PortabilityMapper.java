package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.PortabilityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PortabilityEntity} and its DTO {@link PortabilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PortabilityMapper extends EntityMapper<PortabilityDTO, PortabilityEntity> {}


