package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CrmToCrdbResponseMapEntity} and its DTO {@link CrmToCrdbResponseMapDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmToCrdbResponseMapMapper extends EntityMapper<CrmToCrdbResponseMapDTO, CrmToCrdbResponseMapEntity> {}


