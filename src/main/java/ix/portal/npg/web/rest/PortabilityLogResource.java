package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.PortabilityLogRepository;
import ix.portal.npg.service.PortabilityLogQueryService;
import ix.portal.npg.service.PortabilityLogService;
import ix.portal.npg.service.criteria.PortabilityLogCriteria;
import ix.portal.npg.service.dto.PortabilityLogDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.PortabilityLogEntity}.
 */
@RestController
@RequestMapping("/api")
public class PortabilityLogResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(PortabilityLogResource.class);

    private static final String ENTITY_NAME = "portabilityLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortabilityLogService portabilityLogService;

    private final PortabilityLogRepository portabilityLogRepository;

    private final PortabilityLogQueryService portabilityLogQueryService;

    public PortabilityLogResource(
        PortabilityLogService portabilityLogService,
        PortabilityLogRepository portabilityLogRepository,
        PortabilityLogQueryService portabilityLogQueryService
    ) {
        this.portabilityLogService = portabilityLogService;
        this.portabilityLogRepository = portabilityLogRepository;
        this.portabilityLogQueryService = portabilityLogQueryService;
    }

    /**
     * {@code POST  /portability-logs} : Create a new portabilityLog.
     *
     * @param portabilityLogDTO the portabilityLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portabilityLogDTO, or with status {@code 400 (Bad Request)} if the portabilityLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portability-logs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityLogDTO> createPortabilityLog(@Valid @RequestBody PortabilityLogDTO portabilityLogDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save PortabilityLog : {}", portabilityLogDTO);
            if (portabilityLogDTO.getId() != null) {
                throw new BadRequestAlertException("A new portabilityLog cannot already have an ID", ENTITY_NAME, "idexists");
            }
            PortabilityLogDTO result = portabilityLogService.save(portabilityLogDTO);
            return ResponseEntity
                .created(new URI("/api/portability-logs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /portability-logs/:id} : Updates an existing portabilityLog.
     *
     * @param id the id of the portabilityLogDTO to save.
     * @param portabilityLogDTO the portabilityLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portabilityLogDTO,
     * or with status {@code 400 (Bad Request)} if the portabilityLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portabilityLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portability-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityLogDTO> updatePortabilityLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PortabilityLogDTO portabilityLogDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update PortabilityLog : {}, {}", id, portabilityLogDTO);
            if (portabilityLogDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, portabilityLogDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!portabilityLogRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            PortabilityLogDTO result = portabilityLogService.save(portabilityLogDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portabilityLogDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /portability-logs/:id} : Partial updates given fields of an existing portabilityLog, field will ignore if it is null
     *
     * @param id the id of the portabilityLogDTO to save.
     * @param portabilityLogDTO the portabilityLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portabilityLogDTO,
     * or with status {@code 400 (Bad Request)} if the portabilityLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the portabilityLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the portabilityLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/portability-logs/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityLogDTO> partialUpdatePortabilityLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PortabilityLogDTO portabilityLogDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update PortabilityLog partially : {}, {}", id, portabilityLogDTO);
            if (portabilityLogDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, portabilityLogDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!portabilityLogRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<PortabilityLogDTO> result = portabilityLogService.partialUpdate(portabilityLogDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portabilityLogDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /portability-logs} : get all the portabilityLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portabilityLogs in body.
     */
    @GetMapping("/portability-logs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<PortabilityLogDTO>> getAllPortabilityLogs(PortabilityLogCriteria criteria, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get PortabilityLogs by criteria: {}", criteria);
            Page<PortabilityLogDTO> page = portabilityLogQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /portability-logs/count} : count all the portabilityLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/portability-logs/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countPortabilityLogs(PortabilityLogCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count PortabilityLogs by criteria: {}", criteria);
            return ResponseEntity.ok().body(portabilityLogQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /portability-logs/:id} : get the "id" portabilityLog.
     *
     * @param id the id of the portabilityLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portabilityLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portability-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityLogDTO> getPortabilityLog(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get PortabilityLog : {}", id);
            Optional<PortabilityLogDTO> portabilityLogDTO = portabilityLogService.findOne(id);
            return ResponseUtil.wrapOrNotFound(portabilityLogDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /portability-logs/:id} : delete the "id" portabilityLog.
     *
     * @param id the id of the portabilityLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portability-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deletePortabilityLog(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete PortabilityLog : {}", id);
            portabilityLogService.delete(id);
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


