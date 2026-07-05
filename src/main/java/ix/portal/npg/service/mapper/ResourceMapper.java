package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceEntity} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResourceMapper extends EntityMapper<ResourceDTO, ResourceEntity> {
    @Named("displayName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "displayName", source = "displayName")
    ResourceDTO toDtoDisplayName(ResourceEntity resourceEntity);
}


