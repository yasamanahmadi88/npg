package ix.portal.npg.repository;

import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.domain.model.GeneralReport;
import ix.portal.npg.domain.model.SimpleReport;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PortabilityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortabilityRepository extends JpaRepository<PortabilityEntity, Long>, JpaSpecificationExecutor<PortabilityEntity> {
    Page<PortabilityEntity> findByIdOrPorRequestIdContainingIgnoreCaseOrPorNumberContainingIgnoreCaseOrPorOprContainingIgnoreCaseOrPorAccTypeContainingIgnoreCaseOrPorIdNumberContainingIgnoreCaseOrPorContactNumberContainingIgnoreCaseOrPorStatusContainingIgnoreCaseOrPorRoutingContainingIgnoreCaseOrPorTypeContainingIgnoreCaseOrPorOpOrgContainingIgnoreCaseOrPorRspCodeContainingIgnoreCaseOrPorRspNoteContainingIgnoreCaseOrPorCancelNoteContainingIgnoreCaseOrPorMnpidContainingIgnoreCaseOrPortaCodeContainingIgnoreCaseOrMvnoContainingIgnoreCaseOrContextContainingIgnoreCaseOrPorTechStatusContainingIgnoreCaseOrPorErrCodeContainingIgnoreCaseOrPorErrMessageContainingIgnoreCaseOrPorOpdContainingIgnoreCaseOrPorNoteContainingIgnoreCaseOrIntermediaryActionStateContainingIgnoreCaseOrPorNumTypeContainingIgnoreCase(
        Long id,
        String p1,
        String p2,
        String p3,
        String p4,
        String p5,
        String p6,
        String p7,
        String p8,
        String p9,
        String p10,
        String p11,
        String p12,
        String p13,
        String p14,
        String p15,
        String p16,
        String p17,
        String p18,
        String p19,
        String p20,
        String p21,
        String p22,
        String p23,
        String p24,
        Pageable pageable
    );

    @Query(
        "SELECT new ix.portal.npg.domain.model.SimpleReport(t.porType, count(t.id)) From PortabilityEntity t where t.porCrDate> :startDateParam and t.porCrDate< :endDateParam GROUP BY t.porType"
    )
    List<SimpleReport> findPorTypeCount(@Param("startDateParam") LocalDateTime start, @Param("endDateParam") LocalDateTime end);

    //SELECT t.por_cr_date, count(t.por_id) FROM tbl_portability_entity t group by t.por_cr_date
    @Query(
        nativeQuery = true,
        value = "SELECT substr(t.por_cr_date, 0,10), t.por_Type, count(t.por_id) From tbl_portability_entity t where t.por_Type in ('IN','OUT') and t.por_Cr_Date> :startDateParam and t.por_Cr_Date< :endDateParam GROUP BY substr(t.por_cr_date, 0,10), t.por_Type"
    )
    List<Object[]> findPorDateCount(@Param("startDateParam") LocalDateTime start, @Param("endDateParam") LocalDateTime end);
}


