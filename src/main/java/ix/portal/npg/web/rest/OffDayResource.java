package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.OffDayRepository;
import ix.portal.npg.service.OffDayQueryService;
import ix.portal.npg.service.OffDayService;
import ix.portal.npg.service.criteria.OffDayCriteria;
import ix.portal.npg.service.dto.OffDayDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ix.portal.npg.domain.OffDayEntity}.
 */
@RestController
@RequestMapping("/api")
public class OffDayResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(OffDayResource.class);

    private static final String ENTITY_NAME = "offDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffDayService offDayService;

    private final OffDayRepository offDayRepository;

    private final OffDayQueryService offDayQueryService;

    public OffDayResource(OffDayService offDayService, OffDayRepository offDayRepository, OffDayQueryService offDayQueryService) {
        this.offDayService = offDayService;
        this.offDayRepository = offDayRepository;
        this.offDayQueryService = offDayQueryService;
    }

    /**
     * {@code POST  /off-days} : Create a new offDay.
     *
     * @param offDayDTO the offDayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offDayDTO, or with status {@code 400 (Bad Request)} if the offDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/off-days")
    @Secured(ENTITY_NAME)
    public ResponseEntity<OffDayDTO> createOffDay(@RequestBody OffDayDTO offDayDTO) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save OffDay : {}", offDayDTO);
            if (offDayDTO.getId() != null) {
                throw new BadRequestAlertException("A new offDay cannot already have an ID", ENTITY_NAME, "idexists");
            }
            OffDayDTO result = offDayService.save(offDayDTO);
            return ResponseEntity
                .created(new URI("/api/off-days/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /off-days/:id} : Updates an existing offDay.
     *
     * @param id the id of the offDayDTO to save.
     * @param offDayDTO the offDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offDayDTO,
     * or with status {@code 400 (Bad Request)} if the offDayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/off-days/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<OffDayDTO> updateOffDay(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OffDayDTO offDayDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update OffDay : {}, {}", id, offDayDTO);
            if (offDayDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, offDayDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!offDayRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            OffDayDTO result = offDayService.save(offDayDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offDayDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /off-days/:id} : Partial updates given fields of an existing offDay, field will ignore if it is null
     *
     * @param id the id of the offDayDTO to save.
     * @param offDayDTO the offDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offDayDTO,
     * or with status {@code 400 (Bad Request)} if the offDayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the offDayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the offDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/off-days/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<OffDayDTO> partialUpdateOffDay(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OffDayDTO offDayDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update OffDay partially : {}, {}", id, offDayDTO);
            if (offDayDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, offDayDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!offDayRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<OffDayDTO> result = offDayService.partialUpdate(offDayDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offDayDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /off-days} : get all the offDays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offDays in body.
     */
    @GetMapping("/off-days")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<OffDayDTO>> getAllOffDays(OffDayCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get OffDays by criteria: {}", criteria);
            Page<OffDayDTO> page = offDayQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /off-days/count} : count all the offDays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/off-days/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countOffDays(OffDayCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count OffDays by criteria: {}", criteria);
            return ResponseEntity.ok().body(offDayQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /off-days/:id} : get the "id" offDay.
     *
     * @param id the id of the offDayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offDayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/off-days/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<OffDayDTO> getOffDay(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get OffDay : {}", id);
            Optional<OffDayDTO> offDayDTO = offDayService.findOne(id);
            return ResponseUtil.wrapOrNotFound(offDayDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /off-days/:id} : delete the "id" offDay.
     *
     * @param id the id of the offDayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/off-days/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteOffDay(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete OffDay : {}", id);
            offDayService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/off-days/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<OffDayDTO>> searchOffDays(String searchText, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get OffDays by searchText: {}", searchText);
            Page<OffDayDTO> page = offDayQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/off-days/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<OffDayDTO>> getAll(OffDayCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get OffDays by criteria: {}", criteria);
            Page<OffDayDTO> page = offDayQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


