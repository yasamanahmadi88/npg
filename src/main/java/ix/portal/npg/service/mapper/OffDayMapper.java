package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.OffDayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OffDayEntity} and its DTO {@link OffDayDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OffDayMapper extends EntityMapper<OffDayDTO, OffDayEntity> {}


