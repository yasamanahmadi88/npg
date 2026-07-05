package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.CheckStatusRepository;
import ix.portal.npg.service.CheckStatusQueryService;
import ix.portal.npg.service.CheckStatusService;
import ix.portal.npg.service.criteria.CheckStatusCriteria;
import ix.portal.npg.service.dto.CheckStatusDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.CheckStatusEntity}.
 */
@RestController
@RequestMapping("/api")
public class CheckStatusResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(CheckStatusResource.class);

    private static final String ENTITY_NAME = "checkStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckStatusService checkStatusService;

    private final CheckStatusRepository checkStatusRepository;

    private final CheckStatusQueryService checkStatusQueryService;

    public CheckStatusResource(
        CheckStatusService checkStatusService,
        CheckStatusRepository checkStatusRepository,
        CheckStatusQueryService checkStatusQueryService
    ) {
        this.checkStatusService = checkStatusService;
        this.checkStatusRepository = checkStatusRepository;
        this.checkStatusQueryService = checkStatusQueryService;
    }

    /**
     * {@code POST  /check-statuses} : Create a new checkStatus.
     *
     * @param checkStatusDTO the checkStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkStatusDTO, or with status {@code 400 (Bad Request)} if the checkStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-statuses")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CheckStatusDTO> createCheckStatus(@Valid @RequestBody CheckStatusDTO checkStatusDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save CheckStatus : {}", checkStatusDTO);
            if (checkStatusDTO.getId() != null) {
                throw new BadRequestAlertException("A new checkStatus cannot already have an ID", ENTITY_NAME, "idexists");
            }
            CheckStatusDTO result = checkStatusService.save(checkStatusDTO);
            return ResponseEntity
                .created(new URI("/api/check-statuses/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /check-statuses/:id} : Updates an existing checkStatus.
     *
     * @param id the id of the checkStatusDTO to save.
     * @param checkStatusDTO the checkStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkStatusDTO,
     * or with status {@code 400 (Bad Request)} if the checkStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-statuses/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CheckStatusDTO> updateCheckStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CheckStatusDTO checkStatusDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update CheckStatus : {}, {}", id, checkStatusDTO);
            if (checkStatusDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, checkStatusDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!checkStatusRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            CheckStatusDTO result = checkStatusService.save(checkStatusDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkStatusDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /check-statuses/:id} : Partial updates given fields of an existing checkStatus, field will ignore if it is null
     *
     * @param id the id of the checkStatusDTO to save.
     * @param checkStatusDTO the checkStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkStatusDTO,
     * or with status {@code 400 (Bad Request)} if the checkStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/check-statuses/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CheckStatusDTO> partialUpdateCheckStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CheckStatusDTO checkStatusDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update CheckStatus partially : {}, {}", id, checkStatusDTO);
            if (checkStatusDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, checkStatusDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!checkStatusRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<CheckStatusDTO> result = checkStatusService.partialUpdate(checkStatusDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkStatusDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /check-statuses} : get all the checkStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkStatuses in body.
     */
    @GetMapping("/check-statuses")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<CheckStatusDTO>> getAllCheckStatuses(CheckStatusCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get CheckStatuses by criteria: {}", criteria);
            Page<CheckStatusDTO> page = checkStatusQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /check-statuses/count} : count all the checkStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/check-statuses/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countCheckStatuses(CheckStatusCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count CheckStatuses by criteria: {}", criteria);
            return ResponseEntity.ok().body(checkStatusQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /check-statuses/:id} : get the "id" checkStatus.
     *
     * @param id the id of the checkStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-statuses/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CheckStatusDTO> getCheckStatus(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get CheckStatus : {}", id);
            Optional<CheckStatusDTO> checkStatusDTO = checkStatusService.findOne(id);
            return ResponseUtil.wrapOrNotFound(checkStatusDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /check-statuses/:id} : delete the "id" checkStatus.
     *
     * @param id the id of the checkStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-statuses/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteCheckStatus(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete CheckStatus : {}", id);
            checkStatusService.delete(id);
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


