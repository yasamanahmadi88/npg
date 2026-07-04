package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.SettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SettingEntity} and its DTO {@link SettingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SettingMapper extends EntityMapper<SettingDTO, SettingEntity> {}


