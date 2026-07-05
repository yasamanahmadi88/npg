package ix.portal.npg.service;

import ix.portal.npg.service.dto.OffDayDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.OffDayEntity}.
 */
public interface OffDayService {
    /**
     * Save a offDay.
     *
     * @param offDayDTO the entity to save.
     * @return the persisted entity.
     */
    OffDayDTO save(OffDayDTO offDayDTO);

    /**
     * Partially updates a offDay.
     *
     * @param offDayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OffDayDTO> partialUpdate(OffDayDTO offDayDTO);

    /**
     * Get all the offDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OffDayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" offDay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OffDayDTO> findOne(Long id);

    /**
     * Delete the "id" offDay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


