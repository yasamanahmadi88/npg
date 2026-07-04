package ix.portal.npg.repository;

import ix.portal.npg.domain.PortabilityLogEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PortabilityLogEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortabilityLogRepository
    extends JpaRepository<PortabilityLogEntity, Long>, JpaSpecificationExecutor<PortabilityLogEntity> {}


