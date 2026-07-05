package ix.portal.npg.service;

import ix.portal.npg.service.dto.UndifinedStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.UndifinedStatusEntity}.
 */
public interface UndifinedStatusService {
    /**
     * Save a undifinedStatus.
     *
     * @param undifinedStatusDTO the entity to save.
     * @return the persisted entity.
     */
    UndifinedStatusDTO save(UndifinedStatusDTO undifinedStatusDTO);

    /**
     * Partially updates a undifinedStatus.
     *
     * @param undifinedStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UndifinedStatusDTO> partialUpdate(UndifinedStatusDTO undifinedStatusDTO);

    /**
     * Get all the undifinedStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UndifinedStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" undifinedStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UndifinedStatusDTO> findOne(Long id);

    /**
     * Delete the "id" undifinedStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


