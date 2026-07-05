package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.SettingEntity;
import ix.portal.npg.repository.SettingRepository;
import ix.portal.npg.service.criteria.SettingCriteria;
import ix.portal.npg.service.dto.SettingDTO;
import ix.portal.npg.service.mapper.SettingMapper;
import java.util.List;
import java.util.Optional;
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
 * Service for executing complex queries for {@link SettingEntity} entities in the database.
 * The main input is a {@link SettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SettingDTO} or a {@link Page} of {@link SettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SettingQueryService extends QueryService<SettingEntity> {

    private final Logger log = LoggerFactory.getLogger(SettingQueryService.class);

    private final SettingRepository settingRepository;

    private final SettingMapper settingMapper;

    public SettingQueryService(SettingRepository settingRepository, SettingMapper settingMapper) {
        this.settingRepository = settingRepository;
        this.settingMapper = settingMapper;
    }

    /**
     * Return a {@link List} of {@link SettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SettingDTO> findByCriteria(SettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SettingEntity> specification = createSpecification(criteria);
        return settingMapper.toDto(settingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SettingDTO> findByCriteria(SettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SettingEntity> specification = createSpecification(criteria);
        return settingRepository.findAll(specification, page).map(settingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SettingDTO> findByKey(String key) {
        log.debug("find by key : {},", key);
        return settingRepository.findByKey(key).map(settingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SettingEntity> specification = createSpecification(criteria);
        return settingRepository.count(specification);
    }

    /**
     * Function to convert {@link SettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SettingEntity> createSpecification(SettingCriteria criteria) {
        Specification<SettingEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SettingEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SettingEntity_.name));
            }
            if (criteria.getKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKey(), SettingEntity_.key));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), SettingEntity_.value));
            }
        }
        return specification;
    }
}


