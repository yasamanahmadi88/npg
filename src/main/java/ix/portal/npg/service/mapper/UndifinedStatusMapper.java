package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.UndifinedStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UndifinedStatusEntity} and its DTO {@link UndifinedStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UndifinedStatusMapper extends EntityMapper<UndifinedStatusDTO, UndifinedStatusEntity> {}


