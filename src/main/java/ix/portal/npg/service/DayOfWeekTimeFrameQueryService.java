package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.DayOfWeekTimeFrameEntity;
import ix.portal.npg.repository.DayOfWeekTimeFrameRepository;
import ix.portal.npg.service.criteria.DayOfWeekTimeFrameCriteria;
import ix.portal.npg.service.dto.DayOfWeekTimeFrameDTO;
import ix.portal.npg.service.mapper.DayOfWeekTimeFrameMapper;
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
 * Service for executing complex queries for {@link DayOfWeekTimeFrameEntity} entities in the database.
 * The main input is a {@link DayOfWeekTimeFrameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DayOfWeekTimeFrameDTO} or a {@link Page} of {@link DayOfWeekTimeFrameDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DayOfWeekTimeFrameQueryService extends QueryService<DayOfWeekTimeFrameEntity> {

    private final Logger log = LoggerFactory.getLogger(DayOfWeekTimeFrameQueryService.class);

    private final DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository;

    private final DayOfWeekTimeFrameMapper dayOfWeekTimeFrameMapper;

    public DayOfWeekTimeFrameQueryService(
        DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository,
        DayOfWeekTimeFrameMapper dayOfWeekTimeFrameMapper
    ) {
        this.dayOfWeekTimeFrameRepository = dayOfWeekTimeFrameRepository;
        this.dayOfWeekTimeFrameMapper = dayOfWeekTimeFrameMapper;
    }

    /**
     * Return a {@link List} of {@link DayOfWeekTimeFrameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DayOfWeekTimeFrameDTO> findByCriteria(DayOfWeekTimeFrameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DayOfWeekTimeFrameEntity> specification = createSpecification(criteria);
        return dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DayOfWeekTimeFrameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DayOfWeekTimeFrameDTO> findByCriteria(DayOfWeekTimeFrameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DayOfWeekTimeFrameEntity> specification = createSpecification(criteria);
        return dayOfWeekTimeFrameRepository.findAll(specification, page).map(dayOfWeekTimeFrameMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DayOfWeekTimeFrameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DayOfWeekTimeFrameEntity> specification = createSpecification(criteria);
        return dayOfWeekTimeFrameRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<DayOfWeekTimeFrameDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return dayOfWeekTimeFrameRepository.findById(numberValue, page).map(dayOfWeekTimeFrameMapper::toDto);
    }

    /**
     * Function to convert {@link DayOfWeekTimeFrameCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DayOfWeekTimeFrameEntity> createSpecification(DayOfWeekTimeFrameCriteria criteria) {
        Specification<DayOfWeekTimeFrameEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DayOfWeekTimeFrameEntity_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDay(), DayOfWeekTimeFrameEntity_.day));
            }
            if (criteria.getBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBegin(), DayOfWeekTimeFrameEntity_.begin));
            }
            if (criteria.getEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnd(), DayOfWeekTimeFrameEntity_.end));
            }
        }
        return specification;
    }
}


