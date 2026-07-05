package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.SettingRepository;
import ix.portal.npg.service.SettingQueryService;
import ix.portal.npg.service.SettingService;
import ix.portal.npg.service.criteria.SettingCriteria;
import ix.portal.npg.service.dto.SettingDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.SettingEntity}.
 */
@RestController
@RequestMapping("/api")
public class SettingResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(SettingResource.class);

    private static final String ENTITY_NAME = "setting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SettingService settingService;

    private final SettingRepository settingRepository;

    private final SettingQueryService settingQueryService;

    public SettingResource(SettingService settingService, SettingRepository settingRepository, SettingQueryService settingQueryService) {
        this.settingService = settingService;
        this.settingRepository = settingRepository;
        this.settingQueryService = settingQueryService;
    }

    /**
     * {@code POST  /settings} : Create a new setting.
     *
     * @param settingDTO the settingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new settingDTO, or with status {@code 400 (Bad Request)} if the setting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/settings")
    @Secured(ENTITY_NAME)
    public ResponseEntity<SettingDTO> createSetting(@Valid @RequestBody SettingDTO settingDTO) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save Setting : {}", settingDTO);
            if (settingDTO.getId() != null) {
                throw new BadRequestAlertException("A new setting cannot already have an ID", ENTITY_NAME, "idexists");
            }
            SettingDTO result = settingService.save(settingDTO);
            return ResponseEntity
                .created(new URI("/api/settings/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /settings/:id} : Updates an existing setting.
     *
     * @param id the id of the settingDTO to save.
     * @param settingDTO the settingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingDTO,
     * or with status {@code 400 (Bad Request)} if the settingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the settingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/settings/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<SettingDTO> updateSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SettingDTO settingDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update Setting : {}, {}", id, settingDTO);
            if (settingDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, settingDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!settingRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            SettingDTO result = settingService.save(settingDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /settings/:id} : Partial updates given fields of an existing setting, field will ignore if it is null
     *
     * @param id the id of the settingDTO to save.
     * @param settingDTO the settingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingDTO,
     * or with status {@code 400 (Bad Request)} if the settingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the settingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the settingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/settings/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<SettingDTO> partialUpdateSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SettingDTO settingDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update Setting partially : {}, {}", id, settingDTO);
            if (settingDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, settingDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!settingRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<SettingDTO> result = settingService.partialUpdate(settingDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /settings} : get all the settings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of settings in body.
     */
    @GetMapping("/settings")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<SettingDTO>> getAllSettings(SettingCriteria criteria, Pageable pageable) throws IxssException {
        try {
            log.debug("REST request to get Settings by criteria: {}", criteria);
            Page<SettingDTO> page = settingQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /settings/count} : count all the settings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/settings/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countSettings(SettingCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count Settings by criteria: {}", criteria);
            return ResponseEntity.ok().body(settingQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /settings/:id} : get the "id" setting.
     *
     * @param id the id of the settingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the settingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/settings/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<SettingDTO> getSetting(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get Setting : {}", id);
            Optional<SettingDTO> settingDTO = settingService.findOne(id);
            return ResponseUtil.wrapOrNotFound(settingDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /settings/:id} : delete the "id" setting.
     *
     * @param id the id of the settingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/settings/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteSetting(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete Setting : {}", id);
            settingService.delete(id);
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


