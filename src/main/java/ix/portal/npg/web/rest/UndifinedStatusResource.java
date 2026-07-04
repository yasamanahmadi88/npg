package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.UndifinedStatusRepository;
import ix.portal.npg.service.UndifinedStatusQueryService;
import ix.portal.npg.service.UndifinedStatusService;
import ix.portal.npg.service.criteria.UndifinedStatusCriteria;
import ix.portal.npg.service.dto.UndifinedStatusDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.UndifinedStatusEntity}.
 */
@RestController
@RequestMapping("/api")
public class UndifinedStatusResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(UndifinedStatusResource.class);

    private static final String ENTITY_NAME = "undifinedStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UndifinedStatusService undifinedStatusService;

    private final UndifinedStatusRepository undifinedStatusRepository;

    private final UndifinedStatusQueryService undifinedStatusQueryService;

    public UndifinedStatusResource(
        UndifinedStatusService undifinedStatusService,
        UndifinedStatusRepository undifinedStatusRepository,
        UndifinedStatusQueryService undifinedStatusQueryService
    ) {
        this.undifinedStatusService = undifinedStatusService;
        this.undifinedStatusRepository = undifinedStatusRepository;
        this.undifinedStatusQueryService = undifinedStatusQueryService;
    }

    /**
     * {@code POST  /undifined-statuses} : Create a new undifinedStatus.
     *
     * @param undifinedStatusDTO the undifinedStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new undifinedStatusDTO, or with status {@code 400 (Bad Request)} if the undifinedStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/undifined-statuses")
    @Secured(ENTITY_NAME)
    public ResponseEntity<UndifinedStatusDTO> createUndifinedStatus(@Valid @RequestBody UndifinedStatusDTO undifinedStatusDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save UndifinedStatus : {}", undifinedStatusDTO);
            if (undifinedStatusDTO.getId() != null) {
                throw new BadRequestAlertException("A new undifinedStatus cannot already have an ID", ENTITY_NAME, "idexists");
            }
            UndifinedStatusDTO result = undifinedStatusService.save(undifinedStatusDTO);
            return ResponseEntity
                .created(new URI("/api/undifined-statuses/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /undifined-statuses/:id} : Updates an existing undifinedStatus.
     *
     * @param id the id of the undifinedStatusDTO to save.
     * @param undifinedStatusDTO the undifinedStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated undifinedStatusDTO,
     * or with status {@code 400 (Bad Request)} if the undifinedStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the undifinedStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/undifined-statuses/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<UndifinedStatusDTO> updateUndifinedStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UndifinedStatusDTO undifinedStatusDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update UndifinedStatus : {}, {}", id, undifinedStatusDTO);
            if (undifinedStatusDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, undifinedStatusDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!undifinedStatusRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            UndifinedStatusDTO result = undifinedStatusService.save(undifinedStatusDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, undifinedStatusDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /undifined-statuses/:id} : Partial updates given fields of an existing undifinedStatus, field will ignore if it is null
     *
     * @param id the id of the undifinedStatusDTO to save.
     * @param undifinedStatusDTO the undifinedStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated undifinedStatusDTO,
     * or with status {@code 400 (Bad Request)} if the undifinedStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the undifinedStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the undifinedStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/undifined-statuses/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<UndifinedStatusDTO> partialUpdateUndifinedStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UndifinedStatusDTO undifinedStatusDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update UndifinedStatus partially : {}, {}", id, undifinedStatusDTO);
            if (undifinedStatusDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, undifinedStatusDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!undifinedStatusRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<UndifinedStatusDTO> result = undifinedStatusService.partialUpdate(undifinedStatusDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, undifinedStatusDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /undifined-statuses} : get all the undifinedStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of undifinedStatuses in body.
     */
    @GetMapping("/undifined-statuses")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<UndifinedStatusDTO>> getAllUndifinedStatuses(UndifinedStatusCriteria criteria, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get UndifinedStatuses by criteria: {}", criteria);
            Page<UndifinedStatusDTO> page = undifinedStatusQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /undifined-statuses/count} : count all the undifinedStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/undifined-statuses/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countUndifinedStatuses(UndifinedStatusCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count UndifinedStatuses by criteria: {}", criteria);
            return ResponseEntity.ok().body(undifinedStatusQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /undifined-statuses/:id} : get the "id" undifinedStatus.
     *
     * @param id the id of the undifinedStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the undifinedStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/undifined-statuses/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<UndifinedStatusDTO> getUndifinedStatus(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get UndifinedStatus : {}", id);
            Optional<UndifinedStatusDTO> undifinedStatusDTO = undifinedStatusService.findOne(id);
            return ResponseUtil.wrapOrNotFound(undifinedStatusDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /undifined-statuses/:id} : delete the "id" undifinedStatus.
     *
     * @param id the id of the undifinedStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/undifined-statuses/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteUndifinedStatus(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete UndifinedStatus : {}", id);
            undifinedStatusService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


