package ix.portal.npg.service;

import ix.portal.npg.service.dto.TimeFrameDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.TimeFrameEntity}.
 */
public interface TimeFrameService {
    /**
     * Save a timeFrame.
     *
     * @param timeFrameDTO the entity to save.
     * @return the persisted entity.
     */
    TimeFrameDTO save(TimeFrameDTO timeFrameDTO);

    /**
     * Partially updates a timeFrame.
     *
     * @param timeFrameDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimeFrameDTO> partialUpdate(TimeFrameDTO timeFrameDTO);

    /**
     * Get all the timeFrames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimeFrameDTO> findAll(Pageable pageable);

    /**
     * Get the "id" timeFrame.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeFrameDTO> findOne(Long id);

    /**
     * Delete the "id" timeFrame.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


