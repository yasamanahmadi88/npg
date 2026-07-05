package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.ResourceAuthorityEntity;
import ix.portal.npg.repository.AuthorityRepository;
import ix.portal.npg.repository.ResourceAuthorityRepository;
import ix.portal.npg.service.criteria.ResourceAuthorityCriteria;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import ix.portal.npg.service.mapper.ResourceAuthorityMapper;
import java.util.ArrayList;
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
 * Service for executing complex queries for {@link ResourceAuthorityEntity} entities in the database.
 * The main input is a {@link ResourceAuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResourceAuthorityDTO} or a {@link Page} of {@link ResourceAuthorityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourceAuthorityQueryService extends QueryService<ResourceAuthorityEntity> {

    private final Logger log = LoggerFactory.getLogger(ResourceAuthorityQueryService.class);

    private final ResourceAuthorityRepository resourceAuthorityRepository;

    private final ResourceAuthorityMapper resourceAuthorityMapper;
    private final AuthorityRepository authorityRepository;
    private final AuthorityService authorityService;

    public ResourceAuthorityQueryService(
        ResourceAuthorityRepository resourceAuthorityRepository,
        ResourceAuthorityMapper resourceAuthorityMapper,
        AuthorityRepository authorityRepository,
        AuthorityService authorityService
    ) {
        this.resourceAuthorityRepository = resourceAuthorityRepository;
        this.resourceAuthorityMapper = resourceAuthorityMapper;
        this.authorityRepository = authorityRepository;
        this.authorityService = authorityService;
    }

    /**
     * Return a {@link List} of {@link ResourceAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResourceAuthorityDTO> findByCriteria(ResourceAuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResourceAuthorityEntity> specification = createSpecification(criteria);
        return resourceAuthorityMapper.toDto(resourceAuthorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResourceAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceAuthorityDTO> findByCriteria(ResourceAuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResourceAuthorityEntity> specification = createSpecification(criteria);
        Page<ResourceAuthorityDTO> temp = resourceAuthorityRepository.findAll(specification, page).map(resourceAuthorityMapper::toDto);
        temp.forEach(
            x -> {
                x.setAuthority(authorityService.findOne(x.getAuthorityId()).get());
            }
        );
        return temp;
    }

    @Transactional(readOnly = true)
    public Page<ResourceAuthorityDTO> findByAuthorities(List<String> authorities, Pageable page) {
        log.debug("find by criteria : {}, page: {}", authorities, page);
        List<Long> authIds = new ArrayList<>();
        List<AuthorityEntity> authorityList = authorityRepository.findByNameIn(authorities);
        authorityList.forEach(
            authority -> {
                authIds.add(authority.getId());
            }
        );
        Page<ResourceAuthorityDTO> temp = resourceAuthorityRepository
            .findByAuthorityIdIn(authIds, page)
            .map(resourceAuthorityMapper::toDto);
        temp.forEach(
            x -> {
                x.setAuthority(authorityService.findOne(x.getAuthorityId()).get());
            }
        );
        return temp;
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourceAuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResourceAuthorityEntity> specification = createSpecification(criteria);
        return resourceAuthorityRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<ResourceAuthorityDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        Page<ResourceAuthorityDTO> temp = resourceAuthorityRepository
            .findByIdOrResource_DisplayNameContainingIgnoreCaseOrResource_NameContainingIgnoreCase(numberValue, text, text, page)
            .map(resourceAuthorityMapper::toDto);
        temp.forEach(
            x -> {
                x.setAuthority(authorityService.findOne(x.getAuthorityId()).get());
            }
        );
        return temp;
    }

    /**
     * Function to convert {@link ResourceAuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResourceAuthorityEntity> createSpecification(ResourceAuthorityCriteria criteria) {
        Specification<ResourceAuthorityEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResourceAuthorityEntity_.id));
            }
            if (criteria.getVerb() != null) {
                specification = specification.and(buildSpecification(criteria.getVerb(), ResourceAuthorityEntity_.verb));
            }
            if (criteria.getAuthorityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuthorityId(), ResourceAuthorityEntity_.authorityId));
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceId(),
                            root -> root.join(ResourceAuthorityEntity_.resource, JoinType.LEFT).get(ResourceEntity_.id)
                        )
                    );
            }
        }
        return specification;
    }
}


