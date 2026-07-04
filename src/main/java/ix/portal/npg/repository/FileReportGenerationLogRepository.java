package ix.portal.npg.repository;

import ix.portal.npg.domain.FileReportGenerationLogEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FileReportGenerationLogEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileReportGenerationLogRepository
    extends JpaRepository<FileReportGenerationLogEntity, Long>, JpaSpecificationExecutor<FileReportGenerationLogEntity> {}


