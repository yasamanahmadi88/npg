package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.FileReportGenerationLogEntity;
import ix.portal.npg.repository.FileReportGenerationLogRepository;
import ix.portal.npg.service.criteria.FileReportGenerationLogCriteria;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import ix.portal.npg.service.mapper.FileReportGenerationLogMapper;
import java.util.List;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FileReportGenerationLogEntity} entities in the database.
 * The main input is a {@link FileReportGenerationLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FileReportGenerationLogDTO} or a {@link Page} of {@link FileReportGenerationLogDTO} which fulfills the criteria.
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

    /**
     * Return a {@link List} of {@link FileReportGenerationLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FileReportGenerationLogDTO> findByCriteria(FileReportGenerationLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FileReportGenerationLogEntity> specification = createSpecification(criteria);
        return fileReportGenerationLogMapper.toDto(fileReportGenerationLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FileReportGenerationLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FileReportGenerationLogDTO> findByCriteria(FileReportGenerationLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FileReportGenerationLogEntity> specification = createSpecification(criteria);
        return fileReportGenerationLogRepository.findAll(specification, page).map(fileReportGenerationLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileReportGenerationLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FileReportGenerationLogEntity> specification = createSpecification(criteria);
        return fileReportGenerationLogRepository.count(specification);
    }

    /**
     * Function to convert {@link FileReportGenerationLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FileReportGenerationLogEntity> createSpecification(FileReportGenerationLogCriteria criteria) {
        Specification<FileReportGenerationLogEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FileReportGenerationLogEntity_.id));
            }
            if (criteria.getReportName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReportName(), FileReportGenerationLogEntity_.reportName));
            }
            if (criteria.getReportDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getReportDate(), FileReportGenerationLogEntity_.reportDate));
            }
            if (criteria.getFileName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFileName(), FileReportGenerationLogEntity_.fileName));
            }
            if (criteria.getRowNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRowNumber(), FileReportGenerationLogEntity_.rowNumber));
            }
            if (criteria.getPorNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorNumber(), FileReportGenerationLogEntity_.porNumber));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), FileReportGenerationLogEntity_.content));
            }
        }
        return specification;
    }
}


