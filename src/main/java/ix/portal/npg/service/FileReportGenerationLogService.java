package ix.portal.npg.service;

import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.FileReportGenerationLogEntity}.
 */
public interface FileReportGenerationLogService {
    /**
     * Save a fileReportGenerationLog.
     *
     * @param fileReportGenerationLogDTO the entity to save.
     * @return the persisted entity.
     */
    FileReportGenerationLogDTO save(FileReportGenerationLogDTO fileReportGenerationLogDTO);

    /**
     * Partially updates a fileReportGenerationLog.
     *
     * @param fileReportGenerationLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileReportGenerationLogDTO> partialUpdate(FileReportGenerationLogDTO fileReportGenerationLogDTO);

    /**
     * Get all the fileReportGenerationLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileReportGenerationLogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileReportGenerationLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileReportGenerationLogDTO> findOne(Long id);

    /**
     * Delete the "id" fileReportGenerationLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


