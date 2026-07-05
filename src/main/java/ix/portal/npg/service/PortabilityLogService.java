package ix.portal.npg.service;

import ix.portal.npg.service.dto.PortabilityLogDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.PortabilityLogEntity}.
 */
public interface PortabilityLogService {
    /**
     * Save a portabilityLog.
     *
     * @param portabilityLogDTO the entity to save.
     * @return the persisted entity.
     */
    PortabilityLogDTO save(PortabilityLogDTO portabilityLogDTO);

    /**
     * Partially updates a portabilityLog.
     *
     * @param portabilityLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PortabilityLogDTO> partialUpdate(PortabilityLogDTO portabilityLogDTO);

    /**
     * Get all the portabilityLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PortabilityLogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" portabilityLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PortabilityLogDTO> findOne(Long id);

    /**
     * Delete the "id" portabilityLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


