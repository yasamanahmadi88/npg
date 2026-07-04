package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.ResourceAuthorityRepository;
import ix.portal.npg.service.ResourceAuthorityQueryService;
import ix.portal.npg.service.ResourceAuthorityService;
import ix.portal.npg.service.criteria.ResourceAuthorityCriteria;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import ix.portal.npg.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ix.portal.npg.domain.ResourceAuthorityEntity}.
 */
@RestController
@RequestMapping("/api")
public class ResourceAuthorityResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(ResourceAuthorityResource.class);

    private static final String ENTITY_NAME = "resourceAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceAuthorityService resourceAuthorityService;

    private final ResourceAuthorityRepository resourceAuthorityRepository;

    private final ResourceAuthorityQueryService resourceAuthorityQueryService;

    public ResourceAuthorityResource(
        ResourceAuthorityService resourceAuthorityService,
        ResourceAuthorityRepository resourceAuthorityRepository,
        ResourceAuthorityQueryService resourceAuthorityQueryService
    ) {
        this.resourceAuthorityService = resourceAuthorityService;
        this.resourceAuthorityRepository = resourceAuthorityRepository;
        this.resourceAuthorityQueryService = resourceAuthorityQueryService;
    }

    /**
     * {@code POST  /resource-authorities} : Create a new resourceAuthority.
     *
     * @param resourceAuthorityDTO the resourceAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourceAuthorityDTO, or with status {@code 400 (Bad Request)} if the resourceAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resource-authorities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<ResourceAuthorityDTO> createResourceAuthority(@Valid @RequestBody ResourceAuthorityDTO resourceAuthorityDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save ResourceAuthority : {}", resourceAuthorityDTO);
            if (resourceAuthorityDTO.getId() != null) {
                throw new BadRequestAlertException("A new resourceAuthority cannot already have an ID", ENTITY_NAME, "idexists");
            }
            ResourceAuthorityDTO result = resourceAuthorityService.save(resourceAuthorityDTO);
            return ResponseEntity
                .created(new URI("/api/resource-authorities/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /resource-authorities/:id} : Updates an existing resourceAuthority.
     *
     * @param id the id of the resourceAuthorityDTO to save.
     * @param resourceAuthorityDTO the resourceAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the resourceAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourceAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resource-authorities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<ResourceAuthorityDTO> updateResourceAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResourceAuthorityDTO resourceAuthorityDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update ResourceAuthority : {}, {}", id, resourceAuthorityDTO);
            if (resourceAuthorityDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, resourceAuthorityDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!resourceAuthorityRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            ResourceAuthorityDTO result = resourceAuthorityService.save(resourceAuthorityDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceAuthorityDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /resource-authorities/:id} : Partial updates given fields of an existing resourceAuthority, field will ignore if it is null
     *
     * @param id the id of the resourceAuthorityDTO to save.
     * @param resourceAuthorityDTO the resourceAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the resourceAuthorityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resourceAuthorityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resourceAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resource-authorities/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<ResourceAuthorityDTO> partialUpdateResourceAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResourceAuthorityDTO resourceAuthorityDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update ResourceAuthority partially : {}, {}", id, resourceAuthorityDTO);
            if (resourceAuthorityDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, resourceAuthorityDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!resourceAuthorityRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<ResourceAuthorityDTO> result = resourceAuthorityService.partialUpdate(resourceAuthorityDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceAuthorityDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /resource-authorities} : get all the resourceAuthorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourceAuthorities in body.
     */
    @GetMapping("/resource-authorities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<ResourceAuthorityDTO>> getAllResourceAuthorities(ResourceAuthorityCriteria criteria, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get ResourceAuthorities by criteria: {}", criteria);
            Page<ResourceAuthorityDTO> page = resourceAuthorityQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /resource-authorities/count} : count all the resourceAuthorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/resource-authorities/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countResourceAuthorities(ResourceAuthorityCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count ResourceAuthorities by criteria: {}", criteria);
            return ResponseEntity.ok().body(resourceAuthorityQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /resource-authorities/:id} : get the "id" resourceAuthority.
     *
     * @param id the id of the resourceAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-authorities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<ResourceAuthorityDTO> getResourceAuthority(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get ResourceAuthority : {}", id);
            Optional<ResourceAuthorityDTO> resourceAuthorityDTO = resourceAuthorityService.findOne(id);
            return ResponseUtil.wrapOrNotFound(resourceAuthorityDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /resource-authorities/:id} : delete the "id" resourceAuthority.
     *
     * @param id the id of the resourceAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-authorities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteResourceAuthority(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete ResourceAuthority : {}", id);
            resourceAuthorityService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/resource-authorities/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<ResourceAuthorityDTO>> searchResourceAuthorities(String searchText, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get ResourceAuthorities by searchText: {}", searchText);
            Page<ResourceAuthorityDTO> page = resourceAuthorityQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/resource-authorities/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<ResourceAuthorityDTO>> getAll(ResourceAuthorityCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get  ResourceAuthorities by criteria: {}", criteria);
            Page<ResourceAuthorityDTO> page = resourceAuthorityQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


