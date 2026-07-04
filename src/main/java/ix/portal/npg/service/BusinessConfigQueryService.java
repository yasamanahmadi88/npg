package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.BusinessConfigEntity;
import ix.portal.npg.repository.BusinessConfigRepository;
import ix.portal.npg.service.criteria.BusinessConfigCriteria;
import ix.portal.npg.service.dto.BusinessConfigDTO;
import ix.portal.npg.service.mapper.BusinessConfigMapper;
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
 * Service for executing complex queries for {@link BusinessConfigEntity} entities in the database.
 * The main input is a {@link BusinessConfigCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessConfigDTO} or a {@link Page} of {@link BusinessConfigDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessConfigQueryService extends QueryService<BusinessConfigEntity> {

    private final Logger log = LoggerFactory.getLogger(BusinessConfigQueryService.class);

    private final BusinessConfigRepository businessConfigRepository;

    private final BusinessConfigMapper businessConfigMapper;

    public BusinessConfigQueryService(BusinessConfigRepository businessConfigRepository, BusinessConfigMapper businessConfigMapper) {
        this.businessConfigRepository = businessConfigRepository;
        this.businessConfigMapper = businessConfigMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessConfigDTO> findByCriteria(BusinessConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessConfigEntity> specification = createSpecification(criteria);
        return businessConfigMapper.toDto(businessConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessConfigDTO> findByCriteria(BusinessConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessConfigEntity> specification = createSpecification(criteria);
        return businessConfigRepository.findAll(specification, page).map(businessConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessConfigEntity> specification = createSpecification(criteria);
        return businessConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessConfigEntity> createSpecification(BusinessConfigCriteria criteria) {
        Specification<BusinessConfigEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), BusinessConfigEntity_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), BusinessConfigEntity_.value));
            }
        }
        return specification;
    }
}


