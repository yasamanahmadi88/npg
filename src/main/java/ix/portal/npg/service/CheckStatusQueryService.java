package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.CheckStatusEntity;
import ix.portal.npg.repository.CheckStatusRepository;
import ix.portal.npg.service.criteria.CheckStatusCriteria;
import ix.portal.npg.service.dto.CheckStatusDTO;
import ix.portal.npg.service.mapper.CheckStatusMapper;
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
 * Service for executing complex queries for {@link CheckStatusEntity} entities in the database.
 * The main input is a {@link CheckStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckStatusDTO} or a {@link Page} of {@link CheckStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckStatusQueryService extends QueryService<CheckStatusEntity> {

    private final Logger log = LoggerFactory.getLogger(CheckStatusQueryService.class);

    private final CheckStatusRepository checkStatusRepository;

    private final CheckStatusMapper checkStatusMapper;

    public CheckStatusQueryService(CheckStatusRepository checkStatusRepository, CheckStatusMapper checkStatusMapper) {
        this.checkStatusRepository = checkStatusRepository;
        this.checkStatusMapper = checkStatusMapper;
    }

    /**
     * Return a {@link List} of {@link CheckStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckStatusDTO> findByCriteria(CheckStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckStatusEntity> specification = createSpecification(criteria);
        return checkStatusMapper.toDto(checkStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckStatusDTO> findByCriteria(CheckStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckStatusEntity> specification = createSpecification(criteria);
        return checkStatusRepository.findAll(specification, page).map(checkStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckStatusEntity> specification = createSpecification(criteria);
        return checkStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckStatusEntity> createSpecification(CheckStatusCriteria criteria) {
        Specification<CheckStatusEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckStatusEntity_.id));
            }
            if (criteria.getPorStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorStatus(), CheckStatusEntity_.porStatus));
            }
            if (criteria.getPorTechStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorTechStatus(), CheckStatusEntity_.porTechStatus));
            }
            if (criteria.getPorErrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorErrCode(), CheckStatusEntity_.porErrCode));
            }
            if (criteria.getPorRspCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspCode(), CheckStatusEntity_.porRspCode));
            }
            if (criteria.getStatusMessageFa() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getStatusMessageFa(), CheckStatusEntity_.statusMessageFa));
            }
            if (criteria.getStatusMessageEn() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getStatusMessageEn(), CheckStatusEntity_.statusMessageEn));
            }
        }
        return specification;
    }
}


