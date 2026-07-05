package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.EventLogRepository;
import ix.portal.npg.service.EventLogQueryService;
import ix.portal.npg.service.EventLogService;
import ix.portal.npg.service.criteria.EventLogCriteria;
import ix.portal.npg.service.dto.EventLogDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.EventLogEntity}.
 */
@RestController
@RequestMapping("/api")
public class EventLogResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(EventLogResource.class);

    private static final String ENTITY_NAME = "eventLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventLogService eventLogService;

    private final EventLogRepository eventLogRepository;

    private final EventLogQueryService eventLogQueryService;

    public EventLogResource(
        EventLogService eventLogService,
        EventLogRepository eventLogRepository,
        EventLogQueryService eventLogQueryService
    ) {
        this.eventLogService = eventLogService;
        this.eventLogRepository = eventLogRepository;
        this.eventLogQueryService = eventLogQueryService;
    }

    /**
     * {@code POST  /event-logs} : Create a new eventLog.
     *
     * @param eventLogDTO the eventLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventLogDTO, or with status {@code 400 (Bad Request)} if the eventLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-logs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<EventLogDTO> createEventLog(@Valid @RequestBody EventLogDTO eventLogDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save EventLog : {}", eventLogDTO);
            if (eventLogDTO.getId() != null) {
                throw new BadRequestAlertException("A new eventLog cannot already have an ID", ENTITY_NAME, "idexists");
            }
            EventLogDTO result = eventLogService.save(eventLogDTO);
            return ResponseEntity
                .created(new URI("/api/event-logs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /event-logs/:id} : Updates an existing eventLog.
     *
     * @param id the id of the eventLogDTO to save.
     * @param eventLogDTO the eventLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventLogDTO,
     * or with status {@code 400 (Bad Request)} if the eventLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<EventLogDTO> updateEventLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventLogDTO eventLogDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update EventLog : {}, {}", id, eventLogDTO);
            if (eventLogDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, eventLogDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!eventLogRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            EventLogDTO result = eventLogService.save(eventLogDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventLogDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /event-logs/:id} : Partial updates given fields of an existing eventLog, field will ignore if it is null
     *
     * @param id the id of the eventLogDTO to save.
     * @param eventLogDTO the eventLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventLogDTO,
     * or with status {@code 400 (Bad Request)} if the eventLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-logs/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<EventLogDTO> partialUpdateEventLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventLogDTO eventLogDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update EventLog partially : {}, {}", id, eventLogDTO);
            if (eventLogDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, eventLogDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!eventLogRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<EventLogDTO> result = eventLogService.partialUpdate(eventLogDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventLogDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /event-logs} : get all the eventLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventLogs in body.
     */
    @GetMapping("/event-logs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<EventLogDTO>> getAllEventLogs(EventLogCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get EventLogs by criteria: {}", criteria);
            Page<EventLogDTO> page = eventLogQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /event-logs/count} : count all the eventLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-logs/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countEventLogs(EventLogCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count EventLogs by criteria: {}", criteria);
            return ResponseEntity.ok().body(eventLogQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /event-logs/:id} : get the "id" eventLog.
     *
     * @param id the id of the eventLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<EventLogDTO> getEventLog(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get EventLog : {}", id);
            Optional<EventLogDTO> eventLogDTO = eventLogService.findOne(id);
            return ResponseUtil.wrapOrNotFound(eventLogDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /event-logs/:id} : delete the "id" eventLog.
     *
     * @param id the id of the eventLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteEventLog(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete EventLog : {}", id);
            eventLogService.delete(id);
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


