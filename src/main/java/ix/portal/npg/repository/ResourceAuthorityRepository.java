package ix.portal.npg.repository;

import ix.portal.npg.domain.ResourceAuthorityEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResourceAuthorityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceAuthorityRepository
    extends JpaRepository<ResourceAuthorityEntity, Long>, JpaSpecificationExecutor<ResourceAuthorityEntity> {
    Page<ResourceAuthorityEntity> findByAuthorityIdIn(List<Long> ids, Pageable pageable);
    Page<ResourceAuthorityEntity> findByIdOrResource_DisplayNameContainingIgnoreCaseOrResource_NameContainingIgnoreCase(
        Long id,
        String p1,
        String p2,
        Pageable pageable
    );
}


