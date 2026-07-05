package ix.portal.npg.service;

import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.CrmToCrdbResponseMapEntity}.
 */
public interface CrmToCrdbResponseMapService {
    /**
     * Save a crmToCrdbResponseMap.
     *
     * @param crmToCrdbResponseMapDTO the entity to save.
     * @return the persisted entity.
     */
    CrmToCrdbResponseMapDTO save(CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO);

    /**
     * Partially updates a crmToCrdbResponseMap.
     *
     * @param crmToCrdbResponseMapDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CrmToCrdbResponseMapDTO> partialUpdate(CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO);

    /**
     * Get all the crmToCrdbResponseMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CrmToCrdbResponseMapDTO> findAll(Pageable pageable);

    /**
     * Get the "id" crmToCrdbResponseMap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CrmToCrdbResponseMapDTO> findOne(Long id);

    /**
     * Delete the "id" crmToCrdbResponseMap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


