package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.TimeFrameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeFrameEntity} and its DTO {@link TimeFrameDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TimeFrameMapper extends EntityMapper<TimeFrameDTO, TimeFrameEntity> {}


