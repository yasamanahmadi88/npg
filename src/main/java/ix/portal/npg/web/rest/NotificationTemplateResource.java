package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.repository.NotificationTemplateRepository;
import ix.portal.npg.service.NotificationTemplateQueryService;
import ix.portal.npg.service.NotificationTemplateService;
import ix.portal.npg.service.criteria.NotificationTemplateCriteria;
import ix.portal.npg.service.dto.NotificationTemplateDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.NotificationTemplateEntity}.
 */
@RestController
@RequestMapping("/api")
public class NotificationTemplateResource extends BaseController {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplateResource.class);

    private static final String ENTITY_NAME = "notificationTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationTemplateService notificationTemplateService;

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final NotificationTemplateQueryService notificationTemplateQueryService;

    public NotificationTemplateResource(
        NotificationTemplateService notificationTemplateService,
        NotificationTemplateRepository notificationTemplateRepository,
        NotificationTemplateQueryService notificationTemplateQueryService
    ) {
        this.notificationTemplateService = notificationTemplateService;
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.notificationTemplateQueryService = notificationTemplateQueryService;
    }

    /**
     * {@code POST  /notification-templates} : Create a new notificationTemplate.
     *
     * @param notificationTemplateDTO the notificationTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationTemplateDTO, or with status {@code 400 (Bad Request)} if the notificationTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-templates")
    @Secured(ENTITY_NAME)
    public ResponseEntity<NotificationTemplateDTO> createNotificationTemplate(
        @Valid @RequestBody NotificationTemplateDTO notificationTemplateDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to save NotificationTemplate : {}", notificationTemplateDTO);
            if (notificationTemplateDTO.getId() != null) {
                throw new BadRequestAlertException("A new notificationTemplate cannot already have an ID", ENTITY_NAME, "idexists");
            }
            NotificationTemplateDTO result = notificationTemplateService.save(notificationTemplateDTO);
            return ResponseEntity
                .created(new URI("/api/notification-templates/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PUT  /notification-templates/:id} : Updates an existing notificationTemplate.
     *
     * @param id the id of the notificationTemplateDTO to save.
     * @param notificationTemplateDTO the notificationTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the notificationTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-templates/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<NotificationTemplateDTO> updateNotificationTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotificationTemplateDTO notificationTemplateDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to update NotificationTemplate : {}, {}", id, notificationTemplateDTO);
            if (notificationTemplateDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, notificationTemplateDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!notificationTemplateRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            NotificationTemplateDTO result = notificationTemplateService.save(notificationTemplateDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationTemplateDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code PATCH  /notification-templates/:id} : Partial updates given fields of an existing notificationTemplate, field will ignore if it is null
     *
     * @param id the id of the notificationTemplateDTO to save.
     * @param notificationTemplateDTO the notificationTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the notificationTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notificationTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-templates/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<NotificationTemplateDTO> partialUpdateNotificationTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotificationTemplateDTO notificationTemplateDTO
    ) throws URISyntaxException, IxssException {
        try {
            log.debug("REST request to partial update NotificationTemplate partially : {}, {}", id, notificationTemplateDTO);
            if (notificationTemplateDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(id, notificationTemplateDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!notificationTemplateRepository.existsById(id)) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            Optional<NotificationTemplateDTO> result = notificationTemplateService.partialUpdate(notificationTemplateDTO);

            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationTemplateDTO.getId().toString())
            );
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /notification-templates} : get all the notificationTemplates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationTemplates in body.
     */
    @GetMapping("/notification-templates")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<NotificationTemplateDTO>> getAllNotificationTemplates(
        NotificationTemplateCriteria criteria,
        Pageable pageable
    ) throws IxssException {
        try {
            log.debug("REST request to get NotificationTemplates by criteria: {}", criteria);
            Page<NotificationTemplateDTO> page = notificationTemplateQueryService.findByCriteria(criteria, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /notification-templates/count} : count all the notificationTemplates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notification-templates/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countNotificationTemplates(NotificationTemplateCriteria criteria) throws IxssException {
        try {
            log.debug("REST request to count NotificationTemplates by criteria: {}", criteria);
            return ResponseEntity.ok().body(notificationTemplateQueryService.countByCriteria(criteria));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /notification-templates/:id} : get the "id" notificationTemplate.
     *
     * @param id the id of the notificationTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-templates/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<NotificationTemplateDTO> getNotificationTemplate(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to get NotificationTemplate : {}", id);
            Optional<NotificationTemplateDTO> notificationTemplateDTO = notificationTemplateService.findOne(id);
            return ResponseUtil.wrapOrNotFound(notificationTemplateDTO);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code DELETE  /notification-templates/:id} : delete the "id" notificationTemplate.
     *
     * @param id the id of the notificationTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-templates/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteNotificationTemplate(@PathVariable("id") Long id) throws IxssException {
        try {
            log.debug("REST request to delete NotificationTemplate : {}", id);
            notificationTemplateService.delete(id);
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


