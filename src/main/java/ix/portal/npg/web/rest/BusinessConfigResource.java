package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.BusinessConfigRepository;
import ix.portal.npg.service.BusinessConfigQueryService;
import ix.portal.npg.service.BusinessConfigService;
import ix.portal.npg.service.criteria.BusinessConfigCriteria;
import ix.portal.npg.service.dto.BusinessConfigDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.BusinessConfigEntity}.
 */
@RestController
@RequestMapping("/api")
public class BusinessConfigResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(BusinessConfigResource.class);

    private static final String ENTITY_NAME = "businessConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessConfigService businessConfigService;

    private final BusinessConfigRepository businessConfigRepository;

    private final BusinessConfigQueryService businessConfigQueryService;

    public BusinessConfigResource(
        BusinessConfigService businessConfigService,
        BusinessConfigRepository businessConfigRepository,
        BusinessConfigQueryService businessConfigQueryService
    ) {
        this.businessConfigService = businessConfigService;
        this.businessConfigRepository = businessConfigRepository;
        this.businessConfigQueryService = businessConfigQueryService;
    }

    /**
     * {@code POST  /business-configs} : Create a new businessConfig.
     *
     * @param businessConfigDTO the businessConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessConfigDTO, or with status {@code 400 (Bad Request)} if the businessConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-configs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<BusinessConfigDTO> createBusinessConfig(@Valid @RequestBody BusinessConfigDTO businessConfigDTO)
        throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save BusinessConfig : {}", businessConfigDTO);
            if (businessConfigDTO.getId() != null) {
                throw new BadRequestAlertException("A new businessConfig cannot already have an ID", ENTITY_NAME, "idexists");
            }
            BusinessConfigDTO result = businessConfigService.save(businessConfigDTO);
            return ResponseEntity
                .created(new URI("/api/business-configs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /business-configs/:id} : Updates an existing businessConfig.
     *
     * @param id the id of the businessConfigDTO to save.
     * @param businessConfigDTO the businessConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessConfigDTO,
     * or with status {@code 400 (Bad Request)} if the businessConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-configs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<BusinessConfigDTO> updateBusinessConfig(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody BusinessConfigDTO businessConfigDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update BusinessConfig : {}, {}", id, businessConfigDTO);
            if (businessConfigDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, businessConfigDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            /*if (!businessConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

            BusinessConfigDTO result = businessConfigService.save(businessConfigDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessConfigDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /business-configs/:id} : Partial updates given fields of an existing businessConfig, field will ignore if it is null
     *
     * @param id the id of the businessConfigDTO to save.
     * @param businessConfigDTO the businessConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessConfigDTO,
     * or with status {@code 400 (Bad Request)} if the businessConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-configs/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<BusinessConfigDTO> partialUpdateBusinessConfig(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody BusinessConfigDTO businessConfigDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update BusinessConfig partially : {}, {}", id, businessConfigDTO);
            if (businessConfigDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, businessConfigDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!businessConfigRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<BusinessConfigDTO> result = businessConfigService.partialUpdate(businessConfigDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessConfigDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /business-configs} : get all the businessConfigs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessConfigs in body.
     */
    @GetMapping("/business-configs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<BusinessConfigDTO>> getAllBusinessConfigs(BusinessConfigCriteria criteria, Pageable pageable)
        throws IxssException {
        try {
            log.debug("REST request to get BusinessConfigs by criteria: {}", criteria);
            Page<BusinessConfigDTO> page = businessConfigQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /business-configs/count} : count all the businessConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/business-configs/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countBusinessConfigs(BusinessConfigCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count BusinessConfigs by criteria: {}", criteria);
            return ResponseEntity.ok().body(businessConfigQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /business-configs/:id} : get the "id" businessConfig.
     *
     * @param id the id of the businessConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-configs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<BusinessConfigDTO> getBusinessConfig(@PathVariable String id) throws IxssException {
        try {
            log.debug("REST request to get BusinessConfig : {}", id);
            Optional<BusinessConfigDTO> businessConfigDTO = businessConfigService.findOne(id);
            return ResponseUtil.wrapOrNotFound(businessConfigDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /business-configs/:id} : delete the "id" businessConfig.
     *
     * @param id the id of the businessConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-configs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteBusinessConfig(@PathVariable String id) throws IxssException {
        try {
            log.debug("REST request to delete BusinessConfig : {}", id);
            businessConfigService.delete(id);
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


