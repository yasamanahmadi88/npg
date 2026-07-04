package ix.portal.npg.web.rest;

import ix.portal.npg.exception.DBException;
import ix.portal.npg.exception.IxssException;
import ix.portal.npg.service.AuthorityQueryService;
import ix.portal.npg.service.AuthorityService;
import ix.portal.npg.service.criteria.AuthorityCriteria;
import ix.portal.npg.service.dto.AuthorityDTO;
import ix.portal.npg.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ix.portal.npg.domain.AuthorityEntity}.
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    private static final String ENTITY_NAME = "authority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthorityService authorityService;

    private final AuthorityQueryService authorityQueryService;

    public AuthorityResource(AuthorityService authorityService, AuthorityQueryService authorityQueryService) {
        this.authorityService = authorityService;
        this.authorityQueryService = authorityQueryService;
    }

    /**
     * {@code POST  /authorities} : Create a new authority.
     *
     * @param authorityDTO the authorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorityDTO, or with status {@code 400 (Bad Request)} if the authority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authorities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<AuthorityDTO> createAuthority(@Valid @RequestBody AuthorityDTO authorityDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save Authority : {}", authorityDTO);
            if (authorityDTO.getId() != null) {
                throw new BadRequestAlertException("A new authority cannot already have an ID", ENTITY_NAME, "idexists");
            }
            AuthorityDTO result = authorityService.save(authorityDTO);
            return ResponseEntity
                .created(new URI("/api/authorities/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /authorities} : Updates an existing authority.
     *
     * @param authorityDTO the authorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityDTO,
     * or with status {@code 400 (Bad Request)} if the authorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/authorities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<AuthorityDTO> updateAuthority(@Valid @RequestBody AuthorityDTO authorityDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update Authority : {}", authorityDTO);
            if (authorityDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            AuthorityDTO result = authorityService.save(authorityDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorityDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /authorities} : get all the authorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorities in body.
     */
    @GetMapping("/authorities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<AuthorityDTO>> getAllAuthorities(AuthorityCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get Authorities by criteria: {}", criteria);
            Page<AuthorityDTO> page = authorityQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/authorities/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<AuthorityDTO>> getAll(AuthorityCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get Authorities by criteria: {}", criteria);
            Page<AuthorityDTO> page = authorityQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /authorities/count} : count all the authorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/authorities/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countAuthorities(AuthorityCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count Authorities by criteria: {}", criteria);
            return ResponseEntity.ok().body(authorityQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /authorities/:id} : get the "id" authority.
     *
     * @param id the id of the authorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/authorities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<AuthorityDTO> getAuthority(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get Authority : {}", id);
            Optional<AuthorityDTO> authorityDTO = authorityService.findOne(id);
            return ResponseUtil.wrapOrNotFound(authorityDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /authorities/:id} : delete the "id" authority.
     *
     * @param id the id of the authorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authorities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteAuthority(@PathVariable("id") Long id) throws IxssException, DBException {
        log.debug("REST request to delete Authority : {}", id);
        try {
            authorityService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/authorities/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<AuthorityDTO>> searchAuthorities(String searchText, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get Authorities by searchText: {}", searchText);
            Page<AuthorityDTO> page = authorityQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


