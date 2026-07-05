package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.NotificationTemplateEntity;
import ix.portal.npg.repository.NotificationTemplateRepository;
import ix.portal.npg.service.criteria.NotificationTemplateCriteria;
import ix.portal.npg.service.dto.NotificationTemplateDTO;
import ix.portal.npg.service.mapper.NotificationTemplateMapper;
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
 * Service for executing complex queries for {@link NotificationTemplateEntity} entities in the database.
 * The main input is a {@link NotificationTemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationTemplateDTO} or a {@link Page} of {@link NotificationTemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationTemplateQueryService extends QueryService<NotificationTemplateEntity> {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplateQueryService.class);

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final NotificationTemplateMapper notificationTemplateMapper;

    public NotificationTemplateQueryService(
        NotificationTemplateRepository notificationTemplateRepository,
        NotificationTemplateMapper notificationTemplateMapper
    ) {
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.notificationTemplateMapper = notificationTemplateMapper;
    }

    /**
     * Return a {@link List} of {@link NotificationTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationTemplateDTO> findByCriteria(NotificationTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationTemplateEntity> specification = createSpecification(criteria);
        return notificationTemplateMapper.toDto(notificationTemplateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificationTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationTemplateDTO> findByCriteria(NotificationTemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationTemplateEntity> specification = createSpecification(criteria);
        return notificationTemplateRepository.findAll(specification, page).map(notificationTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationTemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationTemplateEntity> specification = createSpecification(criteria);
        return notificationTemplateRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationTemplateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationTemplateEntity> createSpecification(NotificationTemplateCriteria criteria) {
        Specification<NotificationTemplateEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationTemplateEntity_.id));
            }
            if (criteria.getTemplateCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTemplateCode(), NotificationTemplateEntity_.templateCode));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), NotificationTemplateEntity_.language));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), NotificationTemplateEntity_.content));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), NotificationTemplateEntity_.type));
            }
        }
        return specification;
    }
}


