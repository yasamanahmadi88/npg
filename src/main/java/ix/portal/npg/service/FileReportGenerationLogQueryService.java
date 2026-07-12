package ix.portal.npg.service;

import ix.portal.npg.domain.FileReportGenerationLogEntity;
import ix.portal.npg.domain.FileReportGenerationLogEntity_;
import ix.portal.npg.repository.FileReportGenerationLogRepository;
import ix.portal.npg.service.criteria.FileReportGenerationLogCriteria;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import ix.portal.npg.service.mapper.FileReportGenerationLogMapper;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.RangeFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service for executing complex queries for {@link FileReportGenerationLogEntity} entities in the database.
 */
@Service
@Transactional(readOnly = true)
public class FileReportGenerationLogQueryService extends QueryService<FileReportGenerationLogEntity> {

    private final Logger log = LoggerFactory.getLogger(FileReportGenerationLogQueryService.class);

    private final FileReportGenerationLogRepository fileReportGenerationLogRepository;

    private final FileReportGenerationLogMapper fileReportGenerationLogMapper;

    public FileReportGenerationLogQueryService(
        FileReportGenerationLogRepository fileReportGenerationLogRepository,
        FileReportGenerationLogMapper fileReportGenerationLogMapper
    ) {
        this.fileReportGenerationLogRepository = fileReportGenerationLogRepository;
        this.fileReportGenerationLogMapper = fileReportGenerationLogMapper;
    }

    @Transactional(readOnly = true)
    public List<FileReportGenerationLogDTO> findByCriteria(FileReportGenerationLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        sanitizeCriteria(criteria);
        if (!hasActiveFilter(criteria)) {
            return fileReportGenerationLogMapper.toDto(fileReportGenerationLogRepository.findAll());
        }
        final Specification<FileReportGenerationLogEntity> specification = createSpecification(criteria);
        return fileReportGenerationLogMapper.toDto(fileReportGenerationLogRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<FileReportGenerationLogDTO> findByCriteria(FileReportGenerationLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        sanitizeCriteria(criteria);

        // Empty/blank filters (e.g. reportName.equals=) must not become WHERE col = ''.
        // When no real filter remains, query the table directly — same as pre-filter behavior.
        if (!hasActiveFilter(criteria)) {
            log.info("FileReportGenerationLog: no active filters — using findAll(pageable). pageable={}", page);
            return fileReportGenerationLogRepository.findAll(page).map(fileReportGenerationLogMapper::toDto);
        }

        final Specification<FileReportGenerationLogEntity> specification = createSpecification(criteria);
        Page<FileReportGenerationLogDTO> result =
            fileReportGenerationLogRepository.findAll(specification, page).map(fileReportGenerationLogMapper::toDto);
        log.info(
            "FileReportGenerationLog: filtered query matchingTotal={}, pageSize={}, criteria={}",
            result.getTotalElements(),
            result.getNumberOfElements(),
            criteria
        );
        return result;
    }

    @Transactional(readOnly = true)
    public long countByCriteria(FileReportGenerationLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        sanitizeCriteria(criteria);
        if (!hasActiveFilter(criteria)) {
            return fileReportGenerationLogRepository.count();
        }
        final Specification<FileReportGenerationLogEntity> specification = createSpecification(criteria);
        return fileReportGenerationLogRepository.count(specification);
    }

    /**
     * Drop blank equals/contains values that Spring may bind from empty query params
     * (e.g. {@code reportName.equals=} or {@code reportName.equals=}). Those produce SQL
     * {@code report_name = ''} and return zero rows even when the table has data.
     */
    void sanitizeCriteria(FileReportGenerationLogCriteria criteria) {
        if (criteria == null) {
            return;
        }
        if (isBlankStringFilter(criteria.getReportName())) {
            criteria.setReportName(null);
        }
        if (isBlankStringFilter(criteria.getFileName())) {
            criteria.setFileName(null);
        }
        if (isBlankStringFilter(criteria.getPorNumber())) {
            criteria.setPorNumber(null);
        }
        if (isBlankStringFilter(criteria.getContent())) {
            criteria.setContent(null);
        }
        if (isInactiveRangeFilter(criteria.getId())) {
            criteria.setId(null);
        }
        if (isInactiveRangeFilter(criteria.getRowNumber())) {
            criteria.setRowNumber(null);
        }
        if (isInactiveRangeFilter(criteria.getReportDate())) {
            criteria.setReportDate(null);
        }
    }

    boolean hasActiveFilter(FileReportGenerationLogCriteria criteria) {
        if (criteria == null) {
            return false;
        }
        return (
            isActiveFilter(criteria.getReportName()) ||
            isActiveFilter(criteria.getFileName()) ||
            isActiveFilter(criteria.getPorNumber()) ||
            isActiveFilter(criteria.getContent()) ||
            isActiveRangeFilter(criteria.getId()) ||
            isActiveRangeFilter(criteria.getRowNumber()) ||
            isActiveRangeFilter(criteria.getReportDate())
        );
    }

    protected Specification<FileReportGenerationLogEntity> createSpecification(FileReportGenerationLogCriteria criteria) {
        Specification<FileReportGenerationLogEntity> specification = Specification.unrestricted();
        if (criteria == null) {
            return specification;
        }
        if (isActiveRangeFilter(criteria.getId())) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), FileReportGenerationLogEntity_.id));
        }
        if (isActiveFilter(criteria.getReportName())) {
            specification =
                specification.and(buildStringSpecification(criteria.getReportName(), FileReportGenerationLogEntity_.reportName));
        }
        if (isActiveRangeFilter(criteria.getReportDate())) {
            specification =
                specification.and(buildRangeSpecification(criteria.getReportDate(), FileReportGenerationLogEntity_.reportDate));
        }
        if (isActiveFilter(criteria.getFileName())) {
            specification = specification.and(buildStringSpecification(criteria.getFileName(), FileReportGenerationLogEntity_.fileName));
        }
        if (isActiveRangeFilter(criteria.getRowNumber())) {
            specification =
                specification.and(buildRangeSpecification(criteria.getRowNumber(), FileReportGenerationLogEntity_.rowNumber));
        }
        if (isActiveFilter(criteria.getPorNumber())) {
            specification = specification.and(buildStringSpecification(criteria.getPorNumber(), FileReportGenerationLogEntity_.porNumber));
        }
        if (isActiveFilter(criteria.getContent())) {
            specification = specification.and(buildStringSpecification(criteria.getContent(), FileReportGenerationLogEntity_.content));
        }
        return specification;
    }

    private boolean isBlankStringFilter(StringFilter filter) {
        return filter != null && !isActiveFilter(filter);
    }

    private boolean isInactiveRangeFilter(RangeFilter<?> filter) {
        return filter != null && !isActiveRangeFilter(filter);
    }

    private boolean isActiveFilter(StringFilter filter) {
        if (filter == null) {
            return false;
        }
        return (
            hasText(filter.getEquals()) ||
            hasText(filter.getNotEquals()) ||
            hasText(filter.getContains()) ||
            hasText(filter.getDoesNotContain()) ||
            isNonEmpty(filter.getIn()) ||
            isNonEmpty(filter.getNotIn()) ||
            filter.getSpecified() != null
        );
    }

    private boolean isActiveFilter(Filter<?> filter) {
        if (filter == null) {
            return false;
        }
        if (filter instanceof StringFilter stringFilter) {
            return isActiveFilter(stringFilter);
        }
        return (
            filter.getEquals() != null ||
            filter.getNotEquals() != null ||
            isNonEmpty(filter.getIn()) ||
            isNonEmpty(filter.getNotIn()) ||
            filter.getSpecified() != null
        );
    }

    private boolean isActiveRangeFilter(RangeFilter<?> filter) {
        if (filter == null) {
            return false;
        }
        return (
            isActiveFilter(filter) ||
            filter.getGreaterThan() != null ||
            filter.getLessThan() != null ||
            filter.getGreaterThanOrEqual() != null ||
            filter.getLessThanOrEqual() != null
        );
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private boolean isNonEmpty(Collection<?> values) {
        return values != null && !values.isEmpty();
    }
}
