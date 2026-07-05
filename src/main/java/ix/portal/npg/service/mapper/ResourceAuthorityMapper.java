package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceAuthorityEntity} and its DTO {@link ResourceAuthorityDTO}.
 */
@Mapper(componentModel = "spring", uses = { ResourceMapper.class })
public interface ResourceAuthorityMapper extends EntityMapper<ResourceAuthorityDTO, ResourceAuthorityEntity> {
    @Mapping(target = "resource", source = "resource")
    ResourceAuthorityDTO toDto(ResourceAuthorityEntity s);
}


