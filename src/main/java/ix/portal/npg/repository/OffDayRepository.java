package ix.portal.npg.repository;

import ix.portal.npg.domain.OffDayEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OffDayEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffDayRepository extends JpaRepository<OffDayEntity, Long>, JpaSpecificationExecutor<OffDayEntity> {
    Page<OffDayEntity> findById(Long id, Pageable pageable);
}


