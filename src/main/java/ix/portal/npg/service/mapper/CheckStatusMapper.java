package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.CheckStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckStatusEntity} and its DTO {@link CheckStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckStatusMapper extends EntityMapper<CheckStatusDTO, CheckStatusEntity> {}


