package ix.portal.npg.web.rest;

import ix.portal.npg.domain.model.GeneralReport;
import ix.portal.npg.domain.model.SimpleReport;
import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.PortabilityRepository;
import ix.portal.npg.service.PortabilityQueryService;
import ix.portal.npg.service.PortabilityReportQueryService;
import ix.portal.npg.service.PortabilityService;
import ix.portal.npg.service.criteria.PortabilityCriteria;
import ix.portal.npg.service.criteria.PortabilityReportCriteria;
import ix.portal.npg.service.dto.PortabilityDTO;
import ix.portal.npg.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ix.portal.npg.domain.PortabilityEntity}.
 */
@RestController
@RequestMapping("/api")
public class PortabilityResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(PortabilityResource.class);

    private static final String ENTITY_NAME = "portability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortabilityService portabilityService;

    private final PortabilityRepository portabilityRepository;

    private final PortabilityQueryService portabilityQueryService;
    private final PortabilityReportQueryService portabilityReportQueryService;

    public PortabilityResource(
        PortabilityService portabilityService,
        PortabilityRepository portabilityRepository,
        PortabilityQueryService portabilityQueryService,
        PortabilityReportQueryService portabilityReportQueryService
    ) {
        this.portabilityService = portabilityService;
        this.portabilityRepository = portabilityRepository;
        this.portabilityQueryService = portabilityQueryService;
        this.portabilityReportQueryService = portabilityReportQueryService;
    }

    /**
     * {@code POST  /portabilities} : Create a new portability.
     *
     * @param portabilityDTO the portabilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portabilityDTO, or with status {@code 400 (Bad Request)} if the portability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portabilities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityDTO> createPortability(@Valid @RequestBody PortabilityDTO portabilityDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save Portability : {}", portabilityDTO);
            if (portabilityDTO.getId() != null) {
                throw new BadRequestAlertException("A new portability cannot already have an ID", ENTITY_NAME, "idexists");
            }
            PortabilityDTO result = portabilityService.save(portabilityDTO);
            return ResponseEntity
                .created(new URI("/api/portabilities/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /portabilities/:id} : Updates an existing portability.
     *
     * @param id the id of the portabilityDTO to save.
     * @param portabilityDTO the portabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portabilityDTO,
     * or with status {@code 400 (Bad Request)} if the portabilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portabilities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityDTO> updatePortability(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PortabilityDTO portabilityDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update Portability : {}, {}", id, portabilityDTO);
            if (portabilityDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, portabilityDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!portabilityRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            PortabilityDTO result = portabilityService.save(portabilityDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portabilityDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /portabilities/:id} : Partial updates given fields of an existing portability, field will ignore if it is null
     *
     * @param id the id of the portabilityDTO to save.
     * @param portabilityDTO the portabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portabilityDTO,
     * or with status {@code 400 (Bad Request)} if the portabilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the portabilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the portabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/portabilities/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityDTO> partialUpdatePortability(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PortabilityDTO portabilityDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update Portability partially : {}, {}", id, portabilityDTO);
            if (portabilityDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, portabilityDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!portabilityRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<PortabilityDTO> result = portabilityService.partialUpdate(portabilityDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portabilityDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /portabilities} : get all the portabilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portabilities in body.
     */
    @GetMapping("/portabilities")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<PortabilityDTO>> getAllPortabilities(PortabilityCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get Portabilities by criteria: {}", criteria);
            Page<PortabilityDTO> page = portabilityQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/portabilities/report")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<PortabilityDTO>> getAllPortabilities(PortabilityReportCriteria criteria, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get Portabilities by criteria: {}", criteria);
            Page<PortabilityDTO> page = portabilityReportQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /portabilities/count} : count all the portabilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/portabilities/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countPortabilities(PortabilityCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count Portabilities by criteria: {}", criteria);
            return ResponseEntity.ok().body(portabilityQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /portabilities/:id} : get the "id" portability.
     *
     * @param id the id of the portabilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portabilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portabilities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<PortabilityDTO> getPortability(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get Portability : {}", id);
            Optional<PortabilityDTO> portabilityDTO = portabilityService.findOne(id);
            return ResponseUtil.wrapOrNotFound(portabilityDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/portabilities/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<PortabilityDTO>> getAllPortailities(PortabilityCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get Portabilities by criteria: {}", criteria);
            Page<PortabilityDTO> page = portabilityQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /portabilities/:id} : delete the "id" portability.
     *
     * @param id the id of the portabilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portabilities/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deletePortability(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete Portability : {}", id);
            portabilityService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/portabilities/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<PortabilityDTO>> searchPortabilities(String searchText, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get Portabilities by searchText: {}", searchText);
            Page<PortabilityDTO> page = portabilityQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/portabilities/report-por-type")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<SimpleReport>> getReportPorType(String startDate, String endDate) throws Exception {
        try {
            log.debug("REST request to get getReportPorType by startDate: {}, endDate: {}", startDate, endDate);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime localStartDate = (startDate != null && startDate.length() > 0)
                ? LocalDateTime.parse(startDate + " 00:00:00", dateFormatter)
                : LocalDateTime.now().minusDays(10);
            LocalDateTime localEndDate = (endDate != null && endDate.length() > 0)
                ? LocalDateTime.parse(endDate + " 23:59:59", dateFormatter)
                : LocalDateTime.now();

            if (localEndDate.isAfter(localStartDate.plusDays(90))) throw new Exception("incorrect range!");

            List<SimpleReport> page = portabilityQueryService.findPortTypeReport(localStartDate, localEndDate);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(),
                new PageImpl<>(page)
            );
            return ResponseEntity.ok().headers(headers).body(page);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/portabilities/report-por-date-trend")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<GeneralReport>> getReportPorDateTrend(String startDate, String endDate) throws Exception {
        try {
            log.debug("REST request to get getReportPorDateTrend by startDate: {}, endDate: {}", startDate, endDate);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime localStartDate = (startDate != null && startDate.length() > 0)
                ? LocalDateTime.parse(startDate + " 00:00:00", dateFormatter)
                : LocalDateTime.now().minusDays(10);
            LocalDateTime localEndDate = (endDate != null && endDate.length() > 0)
                ? LocalDateTime.parse(endDate + " 23:59:59", dateFormatter)
                : LocalDateTime.now();

            if (localEndDate.isAfter(localStartDate.plusDays(90))) throw new Exception("incorrect range!");

            List<GeneralReport> page = portabilityQueryService.findPorDateReport(localStartDate, localEndDate);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(),
                new PageImpl<>(page)
            );
            return ResponseEntity.ok().headers(headers).body(page);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


