package ix.portal.npg.service;

import ix.portal.npg.service.dto.BusinessConfigDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.BusinessConfigEntity}.
 */
public interface BusinessConfigService {
    /**
     * Save a businessConfig.
     *
     * @param businessConfigDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessConfigDTO save(BusinessConfigDTO businessConfigDTO);

    /**
     * Partially updates a businessConfig.
     *
     * @param businessConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusinessConfigDTO> partialUpdate(BusinessConfigDTO businessConfigDTO);

    /**
     * Get all the businessConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessConfigDTO> findAll(Pageable pageable);

    /**
     * Get the "id" businessConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessConfigDTO> findOne(String id);

    /**
     * Delete the "id" businessConfig.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}


