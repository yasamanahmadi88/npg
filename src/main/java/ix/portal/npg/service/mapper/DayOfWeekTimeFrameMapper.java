package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.DayOfWeekTimeFrameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DayOfWeekTimeFrameEntity} and its DTO {@link DayOfWeekTimeFrameDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DayOfWeekTimeFrameMapper extends EntityMapper<DayOfWeekTimeFrameDTO, DayOfWeekTimeFrameEntity> {}


