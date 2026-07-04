package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.ResourceEntity;
import ix.portal.npg.repository.ResourceRepository;
import ix.portal.npg.service.criteria.ResourceCriteria;
import ix.portal.npg.service.dto.ResourceDTO;
import ix.portal.npg.service.mapper.ResourceMapper;
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
 * Service for executing complex queries for {@link ResourceEntity} entities in the database.
 * The main input is a {@link ResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResourceDTO} or a {@link Page} of {@link ResourceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourceQueryService extends QueryService<ResourceEntity> {

    private final Logger log = LoggerFactory.getLogger(ResourceQueryService.class);

    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    public ResourceQueryService(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    /**
     * Return a {@link List} of {@link ResourceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResourceDTO> findByCriteria(ResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResourceEntity> specification = createSpecification(criteria);
        return resourceMapper.toDto(resourceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResourceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceDTO> findByCriteria(ResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResourceEntity> specification = createSpecification(criteria);
        return resourceRepository.findAll(specification, page).map(resourceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResourceEntity> specification = createSpecification(criteria);
        return resourceRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<ResourceDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return resourceRepository
            .findByIdOrNameContainingIgnoreCaseOrDisplayNameContainingIgnoreCaseOrApiUriContainingIgnoreCase(
                numberValue,
                text,
                text,
                text,
                page
            )
            .map(resourceMapper::toDto);
    }

    /**
     * Function to convert {@link ResourceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResourceEntity> createSpecification(ResourceCriteria criteria) {
        Specification<ResourceEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResourceEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ResourceEntity_.name));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), ResourceEntity_.displayName));
            }
            if (criteria.getApiUri() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApiUri(), ResourceEntity_.apiUri));
            }
            if (criteria.getResourceType() != null) {
                specification = specification.and(buildSpecification(criteria.getResourceType(), ResourceEntity_.resourceType));
            }
            if (criteria.getResourceAuthoritiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceAuthoritiesId(),
                            root -> root.join(ResourceEntity_.resourceAuthorities, JoinType.LEFT).get(ResourceAuthorityEntity_.id)
                        )
                    );
            }
        }
        return specification;
    }
}


