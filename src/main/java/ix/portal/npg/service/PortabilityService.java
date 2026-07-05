package ix.portal.npg.service;

import ix.portal.npg.service.dto.PortabilityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.PortabilityEntity}.
 */
public interface PortabilityService {
    /**
     * Save a portability.
     *
     * @param portabilityDTO the entity to save.
     * @return the persisted entity.
     */
    PortabilityDTO save(PortabilityDTO portabilityDTO);

    /**
     * Partially updates a portability.
     *
     * @param portabilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PortabilityDTO> partialUpdate(PortabilityDTO portabilityDTO);

    /**
     * Get all the portabilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PortabilityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" portability.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PortabilityDTO> findOne(Long id);

    /**
     * Delete the "id" portability.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


