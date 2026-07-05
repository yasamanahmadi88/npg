package ix.portal.npg.repository;

import ix.portal.npg.domain.PersistentAuditEvent;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the {@link PersistentAuditEvent} entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {
    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principal, Instant after, String type);

    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    List<PersistentAuditEvent> findByAuditEventDateBefore(Instant before);

    @Query(
        nativeQuery = true,
        value = "select count(distinct(t.principal)) as count_ from JHI_PERSISTENT_AUDIT_EVENT t where t.event_type=:eventTypeParam group by t.event_type"
    )
    Integer findCountByAuditEventType(@Param("eventTypeParam") String eventType);

    List<PersistentAuditEvent> findByPrincipal(String principal, Pageable pageable);
}


