package ix.portal.npg.repository;

import ix.portal.npg.domain.CrmToCrdbResponseMapEntity;
import ix.portal.npg.domain.DayOfWeekTimeFrameEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CrmToCrdbResponseMapEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrmToCrdbResponseMapRepository
    extends JpaRepository<CrmToCrdbResponseMapEntity, Long>, JpaSpecificationExecutor<CrmToCrdbResponseMapEntity> {
    Page<CrmToCrdbResponseMapEntity> findByIdOrCodeContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCrmInterfaceContainingIgnoreCaseOrRspCodeContainingIgnoreCaseOrRspNoteContainingIgnoreCase(
        Long id,
        String a,
        String b,
        String c,
        String s,
        String s1,
        Pageable pageable
    );
}


