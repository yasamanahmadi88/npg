package ix.portal.npg.service;

import ix.portal.npg.service.dto.AuthorityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ix.portal.npg.domain.AuthorityEntity}.
 */
public interface AuthorityService {
    /**
     * Save a authority.
     *
     * @param authorityDTO the entity to save.
     * @return the persisted entity.
     */
    AuthorityDTO save(AuthorityDTO authorityDTO);

    /**
     * Get all the authorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AuthorityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" authority.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" authority.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


