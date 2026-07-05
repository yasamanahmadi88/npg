package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.BusinessConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessConfigEntity} and its DTO {@link BusinessConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessConfigMapper extends EntityMapper<BusinessConfigDTO, BusinessConfigEntity> {}


