package ix.portal.npg.service;

import ix.portal.npg.service.dto.CheckStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.CheckStatusEntity}.
 */
public interface CheckStatusService {
    /**
     * Save a checkStatus.
     *
     * @param checkStatusDTO the entity to save.
     * @return the persisted entity.
     */
    CheckStatusDTO save(CheckStatusDTO checkStatusDTO);

    /**
     * Partially updates a checkStatus.
     *
     * @param checkStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckStatusDTO> partialUpdate(CheckStatusDTO checkStatusDTO);

    /**
     * Get all the checkStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" checkStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckStatusDTO> findOne(Long id);

    /**
     * Delete the "id" checkStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


