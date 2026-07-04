package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.TimeFrameRepository;
import ix.portal.npg.service.TimeFrameQueryService;
import ix.portal.npg.service.TimeFrameService;
import ix.portal.npg.service.criteria.TimeFrameCriteria;
import ix.portal.npg.service.dto.TimeFrameDTO;
import ix.portal.npg.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link ix.portal.npg.domain.TimeFrameEntity}.
 */
@RestController
@RequestMapping("/api")
public class TimeFrameResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(TimeFrameResource.class);

    private static final String ENTITY_NAME = "timeFrame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeFrameService timeFrameService;

    private final TimeFrameRepository timeFrameRepository;

    private final TimeFrameQueryService timeFrameQueryService;

    public TimeFrameResource(
        TimeFrameService timeFrameService,
        TimeFrameRepository timeFrameRepository,
        TimeFrameQueryService timeFrameQueryService
    ) {
        this.timeFrameService = timeFrameService;
        this.timeFrameRepository = timeFrameRepository;
        this.timeFrameQueryService = timeFrameQueryService;
    }

    /**
     * {@code POST  /time-frames} : Create a new timeFrame.
     *
     * @param timeFrameDTO the timeFrameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeFrameDTO, or with status {@code 400 (Bad Request)} if the timeFrame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-frames")
    @Secured(ENTITY_NAME)
    public ResponseEntity<TimeFrameDTO> createTimeFrame(@RequestBody TimeFrameDTO timeFrameDTO) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save TimeFrame : {}", timeFrameDTO);
            if (timeFrameDTO.getId() != null) {
                throw new BadRequestAlertException("A new timeFrame cannot already have an ID", ENTITY_NAME, "idexists");
            }
            TimeFrameDTO result = timeFrameService.save(timeFrameDTO);
            return ResponseEntity
                .created(new URI("/api/time-frames/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /time-frames/:id} : Updates an existing timeFrame.
     *
     * @param id the id of the timeFrameDTO to save.
     * @param timeFrameDTO the timeFrameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeFrameDTO,
     * or with status {@code 400 (Bad Request)} if the timeFrameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeFrameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-frames/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<TimeFrameDTO> updateTimeFrame(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeFrameDTO timeFrameDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update TimeFrame : {}, {}", id, timeFrameDTO);
            if (timeFrameDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, timeFrameDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!timeFrameRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            TimeFrameDTO result = timeFrameService.save(timeFrameDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeFrameDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /time-frames/:id} : Partial updates given fields of an existing timeFrame, field will ignore if it is null
     *
     * @param id the id of the timeFrameDTO to save.
     * @param timeFrameDTO the timeFrameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeFrameDTO,
     * or with status {@code 400 (Bad Request)} if the timeFrameDTO is not valid,
     * or with status {@code 404 (Not Found)} if the timeFrameDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the timeFrameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/time-frames/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<TimeFrameDTO> partialUpdateTimeFrame(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeFrameDTO timeFrameDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update TimeFrame partially : {}, {}", id, timeFrameDTO);
            if (timeFrameDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, timeFrameDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!timeFrameRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<TimeFrameDTO> result = timeFrameService.partialUpdate(timeFrameDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeFrameDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /time-frames} : get all the timeFrames.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeFrames in body.
     */
    @GetMapping("/time-frames")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<TimeFrameDTO>> getAllTimeFrames(TimeFrameCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get TimeFrames by criteria: {}", criteria);
            Page<TimeFrameDTO> page = timeFrameQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /time-frames/count} : count all the timeFrames.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/time-frames/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countTimeFrames(TimeFrameCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count TimeFrames by criteria: {}", criteria);
            return ResponseEntity.ok().body(timeFrameQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /time-frames/:id} : get the "id" timeFrame.
     *
     * @param id the id of the timeFrameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeFrameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-frames/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<TimeFrameDTO> getTimeFrame(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get TimeFrame : {}", id);
            Optional<TimeFrameDTO> timeFrameDTO = timeFrameService.findOne(id);
            return ResponseUtil.wrapOrNotFound(timeFrameDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /time-frames/:id} : delete the "id" timeFrame.
     *
     * @param id the id of the timeFrameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-frames/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteTimeFrame(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete TimeFrame : {}", id);
            timeFrameService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/time-frames/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<TimeFrameDTO>> searchTimeFrames(String searchText, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get TimeFrames by searchText: {}", searchText);
            Page<TimeFrameDTO> page = timeFrameQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/time-frames/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<TimeFrameDTO>> getAll(TimeFrameCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get  TimeFrames by criteria: {}", criteria);
            Page<TimeFrameDTO> page = timeFrameQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


