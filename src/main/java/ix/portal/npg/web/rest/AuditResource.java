package ix.portal.npg.web.rest;

import ix.portal.npg.exception.IxssException;
import ix.portal.npg.service.AuditEventService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for getting the {@link AuditEvent}s.
 */
@RestController
@RequestMapping("/management/audits")
public class AuditResource extends BaseController {

    private final AuditEventService auditEventService;

    public AuditResource(AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
    }

    /**
     * {@code GET /audits} : get a page of {@link AuditEvent}s.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link AuditEvent}s in body.
     */
    @GetMapping
    public ResponseEntity<List<AuditEvent>> getAll(Pageable pageable) throws IxssException {
        try {
            Page<AuditEvent> page = auditEventService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /audits} : get a page of {@link AuditEvent} between the {@code fromDate} and {@code toDate}.
     *
     * @param fromDate the start of the time period of {@link AuditEvent} to get.
     * @param toDate the end of the time period of {@link AuditEvent} to get.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of {@link AuditEvent} in body.
     */
    @GetMapping(params = { "fromDate", "toDate" })
    public ResponseEntity<List<AuditEvent>> getByDates(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        Pageable pageable
    ) throws IxssException {
        try {
            Instant from = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant to = toDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();

            Page<AuditEvent> page = auditEventService.findByDates(from, to, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /audits/:id} : get an {@link AuditEvent} by id.
     *
     * @param id the id of the entity to get.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the {@link AuditEvent} in body, or status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id:.+}")
    public ResponseEntity<AuditEvent> get(@PathVariable("id") Long id) throws IxssException {
        try {
            return ResponseUtil.wrapOrNotFound(auditEventService.find(id));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/count-by-eventType/{eventType}")
    public ResponseEntity<Integer> get(@PathVariable String eventType) throws IxssException {
        try {
            return ResponseEntity.ok(auditEventService.findByEventType(eventType));
        } catch (Exception e) {
            handleException(e);
            return ResponseEntity.noContent().build();
        }
    }
}


