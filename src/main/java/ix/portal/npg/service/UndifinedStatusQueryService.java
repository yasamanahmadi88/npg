package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.UndifinedStatusEntity;
import ix.portal.npg.repository.UndifinedStatusRepository;
import ix.portal.npg.service.criteria.UndifinedStatusCriteria;
import ix.portal.npg.service.dto.UndifinedStatusDTO;
import ix.portal.npg.service.mapper.UndifinedStatusMapper;
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
 * Service for executing complex queries for {@link UndifinedStatusEntity} entities in the database.
 * The main input is a {@link UndifinedStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UndifinedStatusDTO} or a {@link Page} of {@link UndifinedStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UndifinedStatusQueryService extends QueryService<UndifinedStatusEntity> {

    private final Logger log = LoggerFactory.getLogger(UndifinedStatusQueryService.class);

    private final UndifinedStatusRepository undifinedStatusRepository;

    private final UndifinedStatusMapper undifinedStatusMapper;

    public UndifinedStatusQueryService(UndifinedStatusRepository undifinedStatusRepository, UndifinedStatusMapper undifinedStatusMapper) {
        this.undifinedStatusRepository = undifinedStatusRepository;
        this.undifinedStatusMapper = undifinedStatusMapper;
    }

    /**
     * Return a {@link List} of {@link UndifinedStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UndifinedStatusDTO> findByCriteria(UndifinedStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UndifinedStatusEntity> specification = createSpecification(criteria);
        return undifinedStatusMapper.toDto(undifinedStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UndifinedStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UndifinedStatusDTO> findByCriteria(UndifinedStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UndifinedStatusEntity> specification = createSpecification(criteria);
        return undifinedStatusRepository.findAll(specification, page).map(undifinedStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UndifinedStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UndifinedStatusEntity> specification = createSpecification(criteria);
        return undifinedStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link UndifinedStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UndifinedStatusEntity> createSpecification(UndifinedStatusCriteria criteria) {
        Specification<UndifinedStatusEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UndifinedStatusEntity_.id));
            }
            if (criteria.getPorStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorStatus(), UndifinedStatusEntity_.porStatus));
            }
            if (criteria.getPorTechStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorTechStatus(), UndifinedStatusEntity_.porTechStatus));
            }
            if (criteria.getPorErrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorErrCode(), UndifinedStatusEntity_.porErrCode));
            }
            if (criteria.getPorRspCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspCode(), UndifinedStatusEntity_.porRspCode));
            }
            if (criteria.getPorRequestId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorRequestId(), UndifinedStatusEntity_.porRequestId));
            }
            if (criteria.getInsertDate() != null) {
                specification = specification.and(buildSpecification(criteria.getInsertDate(), UndifinedStatusEntity_.insertDate));
            }
        }
        return specification;
    }
}


