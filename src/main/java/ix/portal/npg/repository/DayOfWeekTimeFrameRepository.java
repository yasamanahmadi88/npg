package ix.portal.npg.repository;

import ix.portal.npg.domain.DayOfWeekTimeFrameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DayOfWeekTimeFrameEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayOfWeekTimeFrameRepository
    extends JpaRepository<DayOfWeekTimeFrameEntity, Long>, JpaSpecificationExecutor<DayOfWeekTimeFrameEntity> {
    Page<DayOfWeekTimeFrameEntity> findById(Long id, Pageable pageable);
}


