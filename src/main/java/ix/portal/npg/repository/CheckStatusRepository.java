package ix.portal.npg.repository;

import ix.portal.npg.domain.CheckStatusEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckStatusEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckStatusRepository extends JpaRepository<CheckStatusEntity, Long>, JpaSpecificationExecutor<CheckStatusEntity> {}


