package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.AuthorityEntity;
import ix.portal.npg.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AuthorityEntity} and its DTO {@link AuthorityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, AuthorityEntity> {
    @Mapping(source = "parent.id", target = "parentId")
    AuthorityDTO toDto(AuthorityEntity authorityEntity);

    @Mapping(source = "parentId", target = "parent")
    AuthorityEntity toEntity(AuthorityDTO authorityDTO);

    default AuthorityEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setId(id);
        return authorityEntity;
    }
}


