package ix.portal.npg.service;

import ix.portal.npg.domain.*;
import ix.portal.npg.domain.AuthorityEntity;
import ix.portal.npg.repository.AuthorityRepository;
import ix.portal.npg.service.criteria.AuthorityCriteria;
import ix.portal.npg.service.dto.AuthorityDTO;
import ix.portal.npg.service.mapper.AuthorityMapper;
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
 * Service for executing complex queries for {@link AuthorityEntity} entities in the database.
 * The main input is a {@link AuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuthorityDTO} or a {@link Page} of {@link AuthorityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuthorityQueryService extends QueryService<AuthorityEntity> {

    private final Logger log = LoggerFactory.getLogger(AuthorityQueryService.class);

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    public AuthorityQueryService(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    /**
     * Return a {@link List} of {@link AuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthorityDTO> findByCriteria(AuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuthorityEntity> specification = createSpecification(criteria);
        return authorityMapper.toDto(authorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findByCriteria(AuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuthorityEntity> specification = createSpecification(criteria);
        return authorityRepository.findAll(specification, page).map(authorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuthorityEntity> specification = createSpecification(criteria);
        return authorityRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<AuthorityDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return authorityRepository
            .findByIdOrDisplayNameContainingIgnoreCaseOrNameContainingIgnoreCaseOrParent_NameContainingIgnoreCaseOrParent_DisplayNameContainingIgnoreCase(
                numberValue,
                text,
                text,
                text,
                text,
                page
            )
            .map(authorityMapper::toDto);
    }

    /**
     * Function to convert {@link AuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuthorityEntity> createSpecification(AuthorityCriteria criteria) {
        Specification<AuthorityEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AuthorityEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AuthorityEntity_.name));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), AuthorityEntity_.displayName));
            }
            if (criteria.getParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParentId(),
                            root -> root.join(AuthorityEntity_.parent, JoinType.LEFT).get(AuthorityEntity_.id)
                        )
                    );
            }
        }
        return specification;
    }
}


