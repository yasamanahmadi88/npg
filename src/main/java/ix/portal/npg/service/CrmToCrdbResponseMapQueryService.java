package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.CrmToCrdbResponseMapEntity;
import ix.portal.npg.repository.CrmToCrdbResponseMapRepository;
import ix.portal.npg.service.criteria.CrmToCrdbResponseMapCriteria;
import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
import ix.portal.npg.service.mapper.CrmToCrdbResponseMapMapper;
import java.util.List;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CrmToCrdbResponseMapEntity} entities in the database.
 * The main input is a {@link CrmToCrdbResponseMapCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CrmToCrdbResponseMapDTO} or a {@link Page} of {@link CrmToCrdbResponseMapDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CrmToCrdbResponseMapQueryService extends QueryService<CrmToCrdbResponseMapEntity> {

    private final Logger log = LoggerFactory.getLogger(CrmToCrdbResponseMapQueryService.class);

    private final CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository;

    private final CrmToCrdbResponseMapMapper crmToCrdbResponseMapMapper;

    public CrmToCrdbResponseMapQueryService(
        CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository,
        CrmToCrdbResponseMapMapper crmToCrdbResponseMapMapper
    ) {
        this.crmToCrdbResponseMapRepository = crmToCrdbResponseMapRepository;
        this.crmToCrdbResponseMapMapper = crmToCrdbResponseMapMapper;
    }

    /**
     * Return a {@link List} of {@link CrmToCrdbResponseMapDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CrmToCrdbResponseMapDTO> findByCriteria(CrmToCrdbResponseMapCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CrmToCrdbResponseMapEntity> specification = createSpecification(criteria);
        return crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CrmToCrdbResponseMapDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CrmToCrdbResponseMapDTO> findByCriteria(CrmToCrdbResponseMapCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CrmToCrdbResponseMapEntity> specification = createSpecification(criteria);
        return crmToCrdbResponseMapRepository.findAll(specification, page).map(crmToCrdbResponseMapMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CrmToCrdbResponseMapCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CrmToCrdbResponseMapEntity> specification = createSpecification(criteria);
        return crmToCrdbResponseMapRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<CrmToCrdbResponseMapDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return crmToCrdbResponseMapRepository
            .findByIdOrCodeContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCrmInterfaceContainingIgnoreCaseOrRspCodeContainingIgnoreCaseOrRspNoteContainingIgnoreCase(
                numberValue,
                text,
                text,
                text,
                text,
                text,
                page
            )
            .map(crmToCrdbResponseMapMapper::toDto);
    }

    /**
     * Function to convert {@link CrmToCrdbResponseMapCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CrmToCrdbResponseMapEntity> createSpecification(CrmToCrdbResponseMapCriteria criteria) {
        Specification<CrmToCrdbResponseMapEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CrmToCrdbResponseMapEntity_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CrmToCrdbResponseMapEntity_.code));
            }
            if (criteria.getDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescription(), CrmToCrdbResponseMapEntity_.description));
            }
            if (criteria.getCrmInterface() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCrmInterface(), CrmToCrdbResponseMapEntity_.crmInterface));
            }
            if (criteria.getRspCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRspCode(), CrmToCrdbResponseMapEntity_.rspCode));
            }
            if (criteria.getRspNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRspNote(), CrmToCrdbResponseMapEntity_.rspNote));
            }
        }
        return specification;
    }
}


