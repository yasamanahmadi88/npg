package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.DayOfWeekTimeFrameRepository;
import ix.portal.npg.service.DayOfWeekTimeFrameQueryService;
import ix.portal.npg.service.DayOfWeekTimeFrameService;
import ix.portal.npg.service.criteria.CrmToCrdbResponseMapCriteria;
import ix.portal.npg.service.criteria.DayOfWeekTimeFrameCriteria;
import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
import ix.portal.npg.service.dto.DayOfWeekTimeFrameDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.DayOfWeekTimeFrameEntity}.
 */
@RestController
@RequestMapping("/api")
public class DayOfWeekTimeFrameResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(DayOfWeekTimeFrameResource.class);

    private static final String ENTITY_NAME = "dayOfWeekTimeFrame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DayOfWeekTimeFrameService dayOfWeekTimeFrameService;

    private final DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository;

    private final DayOfWeekTimeFrameQueryService dayOfWeekTimeFrameQueryService;

    public DayOfWeekTimeFrameResource(
        DayOfWeekTimeFrameService dayOfWeekTimeFrameService,
        DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository,
        DayOfWeekTimeFrameQueryService dayOfWeekTimeFrameQueryService
    ) {
        this.dayOfWeekTimeFrameService = dayOfWeekTimeFrameService;
        this.dayOfWeekTimeFrameRepository = dayOfWeekTimeFrameRepository;
        this.dayOfWeekTimeFrameQueryService = dayOfWeekTimeFrameQueryService;
    }

    /**
     * {@code POST  /day-of-week-time-frames} : Create a new dayOfWeekTimeFrame.
     *
     * @param dayOfWeekTimeFrameDTO the dayOfWeekTimeFrameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dayOfWeekTimeFrameDTO, or with status {@code 400 (Bad Request)} if the dayOfWeekTimeFrame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/day-of-week-time-frames")
    @Secured(ENTITY_NAME)
    public ResponseEntity<DayOfWeekTimeFrameDTO> createDayOfWeekTimeFrame(@RequestBody DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save DayOfWeekTimeFrame : {}", dayOfWeekTimeFrameDTO);
            if (dayOfWeekTimeFrameDTO.getId() != null) {
                throw new BadRequestAlertException("A new dayOfWeekTimeFrame cannot already have an ID", ENTITY_NAME, "idexists");
            }
            DayOfWeekTimeFrameDTO result = dayOfWeekTimeFrameService.save(dayOfWeekTimeFrameDTO);
            return ResponseEntity
                .created(new URI("/api/day-of-week-time-frames/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /day-of-week-time-frames/:id} : Updates an existing dayOfWeekTimeFrame.
     *
     * @param id the id of the dayOfWeekTimeFrameDTO to save.
     * @param dayOfWeekTimeFrameDTO the dayOfWeekTimeFrameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayOfWeekTimeFrameDTO,
     * or with status {@code 400 (Bad Request)} if the dayOfWeekTimeFrameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dayOfWeekTimeFrameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/day-of-week-time-frames/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<DayOfWeekTimeFrameDTO> updateDayOfWeekTimeFrame(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update DayOfWeekTimeFrame : {}, {}", id, dayOfWeekTimeFrameDTO);
            if (dayOfWeekTimeFrameDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, dayOfWeekTimeFrameDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!dayOfWeekTimeFrameRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            DayOfWeekTimeFrameDTO result = dayOfWeekTimeFrameService.save(dayOfWeekTimeFrameDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayOfWeekTimeFrameDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /day-of-week-time-frames/:id} : Partial updates given fields of an existing dayOfWeekTimeFrame, field will ignore if it is null
     *
     * @param id the id of the dayOfWeekTimeFrameDTO to save.
     * @param dayOfWeekTimeFrameDTO the dayOfWeekTimeFrameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayOfWeekTimeFrameDTO,
     * or with status {@code 400 (Bad Request)} if the dayOfWeekTimeFrameDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dayOfWeekTimeFrameDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dayOfWeekTimeFrameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/day-of-week-time-frames/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<DayOfWeekTimeFrameDTO> partialUpdateDayOfWeekTimeFrame(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update DayOfWeekTimeFrame partially : {}, {}", id, dayOfWeekTimeFrameDTO);
            if (dayOfWeekTimeFrameDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, dayOfWeekTimeFrameDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!dayOfWeekTimeFrameRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<DayOfWeekTimeFrameDTO> result = dayOfWeekTimeFrameService.partialUpdate(dayOfWeekTimeFrameDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayOfWeekTimeFrameDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /day-of-week-time-frames} : get all the dayOfWeekTimeFrames.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dayOfWeekTimeFrames in body.
     */
    @GetMapping("/day-of-week-time-frames")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<DayOfWeekTimeFrameDTO>> getAllDayOfWeekTimeFrames(DayOfWeekTimeFrameCriteria criteria, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get DayOfWeekTimeFrames by criteria: {}", criteria);
            Page<DayOfWeekTimeFrameDTO> page = dayOfWeekTimeFrameQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /day-of-week-time-frames/count} : count all the dayOfWeekTimeFrames.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/day-of-week-time-frames/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countDayOfWeekTimeFrames(DayOfWeekTimeFrameCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count DayOfWeekTimeFrames by criteria: {}", criteria);
            return ResponseEntity.ok().body(dayOfWeekTimeFrameQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /day-of-week-time-frames/:id} : get the "id" dayOfWeekTimeFrame.
     *
     * @param id the id of the dayOfWeekTimeFrameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dayOfWeekTimeFrameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/day-of-week-time-frames/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<DayOfWeekTimeFrameDTO> getDayOfWeekTimeFrame(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get DayOfWeekTimeFrame : {}", id);
            Optional<DayOfWeekTimeFrameDTO> dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameService.findOne(id);
            return ResponseUtil.wrapOrNotFound(dayOfWeekTimeFrameDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /day-of-week-time-frames/:id} : delete the "id" dayOfWeekTimeFrame.
     *
     * @param id the id of the dayOfWeekTimeFrameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/day-of-week-time-frames/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteDayOfWeekTimeFrame(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete DayOfWeekTimeFrame : {}", id);
            dayOfWeekTimeFrameService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/day-of-week-time-frames/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<DayOfWeekTimeFrameDTO>> searchDayOfWeekTimeFrames(String searchText, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get DayOfWeekTimeFrames by searchText: {}", searchText);
            Page<DayOfWeekTimeFrameDTO> page = dayOfWeekTimeFrameQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/day-of-week-time-frames/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<DayOfWeekTimeFrameDTO>> getAll(DayOfWeekTimeFrameCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get DayOfWeekTimeFrames by criteria: {}", criteria);
            Page<DayOfWeekTimeFrameDTO> page = dayOfWeekTimeFrameQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


