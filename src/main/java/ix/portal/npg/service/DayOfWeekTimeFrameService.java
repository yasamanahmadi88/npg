package ix.portal.npg.service;

import ix.portal.npg.service.dto.DayOfWeekTimeFrameDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.DayOfWeekTimeFrameEntity}.
 */
public interface DayOfWeekTimeFrameService {
    /**
     * Save a dayOfWeekTimeFrame.
     *
     * @param dayOfWeekTimeFrameDTO the entity to save.
     * @return the persisted entity.
     */
    DayOfWeekTimeFrameDTO save(DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO);

    /**
     * Partially updates a dayOfWeekTimeFrame.
     *
     * @param dayOfWeekTimeFrameDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DayOfWeekTimeFrameDTO> partialUpdate(DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO);

    /**
     * Get all the dayOfWeekTimeFrames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DayOfWeekTimeFrameDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dayOfWeekTimeFrame.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DayOfWeekTimeFrameDTO> findOne(Long id);

    /**
     * Delete the "id" dayOfWeekTimeFrame.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


