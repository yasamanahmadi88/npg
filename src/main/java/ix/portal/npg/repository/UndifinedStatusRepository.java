package ix.portal.npg.repository;

import ix.portal.npg.domain.UndifinedStatusEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UndifinedStatusEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UndifinedStatusRepository
    extends JpaRepository<UndifinedStatusEntity, Long>, JpaSpecificationExecutor<UndifinedStatusEntity> {}


