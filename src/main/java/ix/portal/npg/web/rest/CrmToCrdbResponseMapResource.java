package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.CrmToCrdbResponseMapRepository;
import ix.portal.npg.service.CrmToCrdbResponseMapQueryService;
import ix.portal.npg.service.CrmToCrdbResponseMapService;
import ix.portal.npg.service.criteria.CrmToCrdbResponseMapCriteria;
import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.CrmToCrdbResponseMapEntity}.
 */
@RestController
@RequestMapping("/api")
public class CrmToCrdbResponseMapResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(CrmToCrdbResponseMapResource.class);

    private static final String ENTITY_NAME = "crmToCrdbResponseMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrmToCrdbResponseMapService crmToCrdbResponseMapService;

    private final CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository;

    private final CrmToCrdbResponseMapQueryService crmToCrdbResponseMapQueryService;

    public CrmToCrdbResponseMapResource(
        CrmToCrdbResponseMapService crmToCrdbResponseMapService,
        CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository,
        CrmToCrdbResponseMapQueryService crmToCrdbResponseMapQueryService
    ) {
        this.crmToCrdbResponseMapService = crmToCrdbResponseMapService;
        this.crmToCrdbResponseMapRepository = crmToCrdbResponseMapRepository;
        this.crmToCrdbResponseMapQueryService = crmToCrdbResponseMapQueryService;
    }

    /**
     * {@code POST  /crm-to-crdb-response-maps} : Create a new crmToCrdbResponseMap.
     *
     * @param crmToCrdbResponseMapDTO the crmToCrdbResponseMapDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crmToCrdbResponseMapDTO, or with status {@code 400 (Bad Request)} if the crmToCrdbResponseMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crm-to-crdb-response-maps")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CrmToCrdbResponseMapDTO> createCrmToCrdbResponseMap(
        @Valid @RequestBody CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save CrmToCrdbResponseMap : {}", crmToCrdbResponseMapDTO);
            if (crmToCrdbResponseMapDTO.getId() != null) {
                throw new BadRequestAlertException("A new crmToCrdbResponseMap cannot already have an ID", ENTITY_NAME, "idexists");
            }
            CrmToCrdbResponseMapDTO result = crmToCrdbResponseMapService.save(crmToCrdbResponseMapDTO);
            return ResponseEntity
                .created(new URI("/api/crm-to-crdb-response-maps/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /crm-to-crdb-response-maps/:id} : Updates an existing crmToCrdbResponseMap.
     *
     * @param id the id of the crmToCrdbResponseMapDTO to save.
     * @param crmToCrdbResponseMapDTO the crmToCrdbResponseMapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crmToCrdbResponseMapDTO,
     * or with status {@code 400 (Bad Request)} if the crmToCrdbResponseMapDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crmToCrdbResponseMapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crm-to-crdb-response-maps/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CrmToCrdbResponseMapDTO> updateCrmToCrdbResponseMap(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update CrmToCrdbResponseMap : {}, {}", id, crmToCrdbResponseMapDTO);
            if (crmToCrdbResponseMapDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, crmToCrdbResponseMapDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!crmToCrdbResponseMapRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            CrmToCrdbResponseMapDTO result = crmToCrdbResponseMapService.save(crmToCrdbResponseMapDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crmToCrdbResponseMapDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /crm-to-crdb-response-maps/:id} : Partial updates given fields of an existing crmToCrdbResponseMap, field will ignore if it is null
     *
     * @param id the id of the crmToCrdbResponseMapDTO to save.
     * @param crmToCrdbResponseMapDTO the crmToCrdbResponseMapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crmToCrdbResponseMapDTO,
     * or with status {@code 400 (Bad Request)} if the crmToCrdbResponseMapDTO is not valid,
     * or with status {@code 404 (Not Found)} if the crmToCrdbResponseMapDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the crmToCrdbResponseMapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crm-to-crdb-response-maps/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CrmToCrdbResponseMapDTO> partialUpdateCrmToCrdbResponseMap(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update CrmToCrdbResponseMap partially : {}, {}", id, crmToCrdbResponseMapDTO);
            if (crmToCrdbResponseMapDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, crmToCrdbResponseMapDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!crmToCrdbResponseMapRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<CrmToCrdbResponseMapDTO> result = crmToCrdbResponseMapService.partialUpdate(crmToCrdbResponseMapDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crmToCrdbResponseMapDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /crm-to-crdb-response-maps} : get all the crmToCrdbResponseMaps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crmToCrdbResponseMaps in body.
     */
    @GetMapping("/crm-to-crdb-response-maps")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<CrmToCrdbResponseMapDTO>> getAllCrmToCrdbResponseMaps(
        CrmToCrdbResponseMapCriteria criteria,
        Pageable pageable
    ) throws IxssException {
        try {
            log.debug("REST request to get CrmToCrdbResponseMaps by criteria: {}", criteria);
            Page<CrmToCrdbResponseMapDTO> page = crmToCrdbResponseMapQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /crm-to-crdb-response-maps/count} : count all the crmToCrdbResponseMaps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/crm-to-crdb-response-maps/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countCrmToCrdbResponseMaps(CrmToCrdbResponseMapCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count CrmToCrdbResponseMaps by criteria: {}", criteria);
            return ResponseEntity.ok().body(crmToCrdbResponseMapQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /crm-to-crdb-response-maps/:id} : get the "id" crmToCrdbResponseMap.
     *
     * @param id the id of the crmToCrdbResponseMapDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crmToCrdbResponseMapDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crm-to-crdb-response-maps/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<CrmToCrdbResponseMapDTO> getCrmToCrdbResponseMap(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get CrmToCrdbResponseMap : {}", id);
            Optional<CrmToCrdbResponseMapDTO> crmToCrdbResponseMapDTO = crmToCrdbResponseMapService.findOne(id);
            return ResponseUtil.wrapOrNotFound(crmToCrdbResponseMapDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /crm-to-crdb-response-maps/:id} : delete the "id" crmToCrdbResponseMap.
     *
     * @param id the id of the crmToCrdbResponseMapDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crm-to-crdb-response-maps/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteCrmToCrdbResponseMap(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete CrmToCrdbResponseMap : {}", id);
            crmToCrdbResponseMapService.delete(id);
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/crm-to-crdb-response-maps/search")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<CrmToCrdbResponseMapDTO>> searchCrmToCrdbResponseMaps(String searchText, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get CrmToCrdbResponseMaps by searchText: {}", searchText);
            Page<CrmToCrdbResponseMapDTO> page = crmToCrdbResponseMapQueryService.searchByText(searchText, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/crm-to-crdb-response-maps/all")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<CrmToCrdbResponseMapDTO>> getAll(CrmToCrdbResponseMapCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to get CrmToCrdbResponseMaps by criteria: {}", criteria);
            Page<CrmToCrdbResponseMapDTO> page = crmToCrdbResponseMapQueryService.findByCriteria(criteria, Pageable.unpaged());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


