package ix.portal.npg.repository;

import ix.portal.npg.domain.TimeFrameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TimeFrameEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeFrameRepository extends JpaRepository<TimeFrameEntity, Long>, JpaSpecificationExecutor<TimeFrameEntity> {
    Page<TimeFrameEntity> findById(Long id, Pageable pageable);
}


