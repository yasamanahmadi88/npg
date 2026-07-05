package ix.portal.npg.repository;

import ix.portal.npg.domain.EventLogEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EventLogEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventLogRepository extends JpaRepository<EventLogEntity, Long>, JpaSpecificationExecutor<EventLogEntity> {}


