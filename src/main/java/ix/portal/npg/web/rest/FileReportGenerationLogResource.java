package ix.portal.npg.web.rest;

import ix.portal.npg.repository.FileReportGenerationLogRepository;
import ix.portal.npg.service.FileReportGenerationLogQueryService;
import ix.portal.npg.service.FileReportGenerationLogService;
import ix.portal.npg.service.criteria.FileReportGenerationLogCriteria;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
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
 * REST controller for managing {@link ix.portal.npg.domain.FileReportGenerationLogEntity}.
 */
@RestController
@RequestMapping("/api")
public class FileReportGenerationLogResource {

    private final Logger log = LoggerFactory.getLogger(FileReportGenerationLogResource.class);

    private static final String ENTITY_NAME = "fileReportGenerationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileReportGenerationLogService fileReportGenerationLogService;

    private final FileReportGenerationLogRepository fileReportGenerationLogRepository;

    private final FileReportGenerationLogQueryService fileReportGenerationLogQueryService;

    public FileReportGenerationLogResource(
        FileReportGenerationLogService fileReportGenerationLogService,
        FileReportGenerationLogRepository fileReportGenerationLogRepository,
        FileReportGenerationLogQueryService fileReportGenerationLogQueryService
    ) {
        this.fileReportGenerationLogService = fileReportGenerationLogService;
        this.fileReportGenerationLogRepository = fileReportGenerationLogRepository;
        this.fileReportGenerationLogQueryService = fileReportGenerationLogQueryService;
    }

    /**
     * {@code POST  /file-report-generation-logs} : Create a new fileReportGenerationLog.
     *
     * @param fileReportGenerationLogDTO the fileReportGenerationLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileReportGenerationLogDTO, or with status {@code 400 (Bad Request)} if the fileReportGenerationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-report-generation-logs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<FileReportGenerationLogDTO> createFileReportGenerationLog(
        @Valid @RequestBody FileReportGenerationLogDTO fileReportGenerationLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FileReportGenerationLog : {}", fileReportGenerationLogDTO);
        if (fileReportGenerationLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileReportGenerationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileReportGenerationLogDTO result = fileReportGenerationLogService.save(fileReportGenerationLogDTO);
        return ResponseEntity
            .created(new URI("/api/file-report-generation-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-report-generation-logs/:id} : Updates an existing fileReportGenerationLog.
     *
     * @param id the id of the fileReportGenerationLogDTO to save.
     * @param fileReportGenerationLogDTO the fileReportGenerationLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileReportGenerationLogDTO,
     * or with status {@code 400 (Bad Request)} if the fileReportGenerationLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileReportGenerationLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-report-generation-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<FileReportGenerationLogDTO> updateFileReportGenerationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FileReportGenerationLogDTO fileReportGenerationLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileReportGenerationLog : {}, {}", id, fileReportGenerationLogDTO);
        if (fileReportGenerationLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileReportGenerationLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileReportGenerationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileReportGenerationLogDTO result = fileReportGenerationLogService.save(fileReportGenerationLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileReportGenerationLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-report-generation-logs/:id} : Partial updates given fields of an existing fileReportGenerationLog, field will ignore if it is null
     *
     * @param id the id of the fileReportGenerationLogDTO to save.
     * @param fileReportGenerationLogDTO the fileReportGenerationLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileReportGenerationLogDTO,
     * or with status {@code 400 (Bad Request)} if the fileReportGenerationLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileReportGenerationLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileReportGenerationLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-report-generation-logs/{id}", consumes = "application/merge-patch+json")
    @Secured(ENTITY_NAME)
    public ResponseEntity<FileReportGenerationLogDTO> partialUpdateFileReportGenerationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FileReportGenerationLogDTO fileReportGenerationLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileReportGenerationLog partially : {}, {}", id, fileReportGenerationLogDTO);
        if (fileReportGenerationLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileReportGenerationLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileReportGenerationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileReportGenerationLogDTO> result = fileReportGenerationLogService.partialUpdate(fileReportGenerationLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileReportGenerationLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /file-report-generation-logs} : get all the fileReportGenerationLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileReportGenerationLogs in body.
     */
    @GetMapping("/file-report-generation-logs")
    @Secured(ENTITY_NAME)
    public ResponseEntity<List<FileReportGenerationLogDTO>> getAllFileReportGenerationLogs(
        FileReportGenerationLogCriteria criteria,
        Pageable pageable
    ) {
        log.info(
            "REST request to get FileReportGenerationLogs by criteria: {}, pageable: {}",
            criteria,
            pageable
        );
        Page<FileReportGenerationLogDTO> page = fileReportGenerationLogQueryService.findByCriteria(criteria, pageable);
        long tableCount = fileReportGenerationLogRepository.count();

        // Safety net for blank filters / page-0 empty results while the table has rows.
        if (
            page.isEmpty() &&
            tableCount > 0 &&
            pageable.getPageNumber() == 0 &&
            !fileReportGenerationLogQueryService.hasActiveFilter(criteria)
        ) {
            log.warn(
                "FileReportGenerationLog: empty page-0 with inactive/blank criteria while tableCount={}. Retrying unfiltered query.",
                tableCount
            );
            page = fileReportGenerationLogQueryService.findByCriteria(new FileReportGenerationLogCriteria(), pageable);
        }

        log.info(
            "FileReportGenerationLog query result: matchingTotal={}, returnedPageSize={}, unfilteredTableCount={}, sort={}",
            page.getTotalElements(),
            page.getNumberOfElements(),
            tableCount,
            pageable.getSort()
        );
        if (page.isEmpty() && tableCount > 0) {
            log.warn(
                "FileReportGenerationLog table has {} row(s) but this request matched 0. " +
                    "Check request filters/criteria and page index (pageable.page={}). criteria={}",
                tableCount,
                pageable.getPageNumber(),
                criteria
            );
        }
        if (page.isEmpty() && tableCount == 0) {
            log.warn(
                "FileReportGenerationLog mapped table is empty for the connected datasource user/schema. " +
                    "Verify JDBC URL/user and that data exists in TBL_FILE_REPORT_GENERATION_LOG."
            );
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        headers.add("X-Table-Count", String.valueOf(tableCount));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-report-generation-logs/count} : count all the fileReportGenerationLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/file-report-generation-logs/count")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Long> countFileReportGenerationLogs(FileReportGenerationLogCriteria criteria) {
        log.debug("REST request to count FileReportGenerationLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(fileReportGenerationLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /file-report-generation-logs/:id} : get the "id" fileReportGenerationLog.
     *
     * @param id the id of the fileReportGenerationLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileReportGenerationLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-report-generation-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<FileReportGenerationLogDTO> getFileReportGenerationLog(@PathVariable("id") Long id) {
        log.debug("REST request to get FileReportGenerationLog : {}", id);
        Optional<FileReportGenerationLogDTO> fileReportGenerationLogDTO = fileReportGenerationLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileReportGenerationLogDTO);
    }

    /**
     * {@code DELETE  /file-report-generation-logs/:id} : delete the "id" fileReportGenerationLog.
     *
     * @param id the id of the fileReportGenerationLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-report-generation-logs/{id}")
    @Secured(ENTITY_NAME)
    public ResponseEntity<Void> deleteFileReportGenerationLog(@PathVariable("id") Long id) {
        log.debug("REST request to delete FileReportGenerationLog : {}", id);
        fileReportGenerationLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}


