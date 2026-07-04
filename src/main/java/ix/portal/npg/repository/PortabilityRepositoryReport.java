package ix.portal.npg.repository;

import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.domain.PortabilityEntityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PortabilityEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortabilityRepositoryReport
    extends JpaRepository<PortabilityEntityReport, Long>, JpaSpecificationExecutor<PortabilityEntityReport> {}


