package ix.portal.npg.repository;

import ix.portal.npg.domain.ResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResourceEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long>, JpaSpecificationExecutor<ResourceEntity> {
    Page<ResourceEntity> findByIdOrNameContainingIgnoreCaseOrDisplayNameContainingIgnoreCaseOrApiUriContainingIgnoreCase(
        Long id,
        String p1,
        String p2,
        String p3,
        Pageable pageable
    );
}


