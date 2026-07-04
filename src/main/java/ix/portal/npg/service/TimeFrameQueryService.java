package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.TimeFrameEntity;
import ix.portal.npg.repository.TimeFrameRepository;
import ix.portal.npg.service.criteria.TimeFrameCriteria;
import ix.portal.npg.service.dto.TimeFrameDTO;
import ix.portal.npg.service.mapper.TimeFrameMapper;
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
 * Service for executing complex queries for {@link TimeFrameEntity} entities in the database.
 * The main input is a {@link TimeFrameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TimeFrameDTO} or a {@link Page} of {@link TimeFrameDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TimeFrameQueryService extends QueryService<TimeFrameEntity> {

    private final Logger log = LoggerFactory.getLogger(TimeFrameQueryService.class);

    private final TimeFrameRepository timeFrameRepository;

    private final TimeFrameMapper timeFrameMapper;

    public TimeFrameQueryService(TimeFrameRepository timeFrameRepository, TimeFrameMapper timeFrameMapper) {
        this.timeFrameRepository = timeFrameRepository;
        this.timeFrameMapper = timeFrameMapper;
    }

    /**
     * Return a {@link List} of {@link TimeFrameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TimeFrameDTO> findByCriteria(TimeFrameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TimeFrameEntity> specification = createSpecification(criteria);
        return timeFrameMapper.toDto(timeFrameRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TimeFrameDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeFrameDTO> findByCriteria(TimeFrameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TimeFrameEntity> specification = createSpecification(criteria);
        return timeFrameRepository.findAll(specification, page).map(timeFrameMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TimeFrameCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TimeFrameEntity> specification = createSpecification(criteria);
        return timeFrameRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<TimeFrameDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return timeFrameRepository.findById(numberValue, page).map(timeFrameMapper::toDto);
    }

    /**
     * Function to convert {@link TimeFrameCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TimeFrameEntity> createSpecification(TimeFrameCriteria criteria) {
        Specification<TimeFrameEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TimeFrameEntity_.id));
            }
            if (criteria.getBegin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBegin(), TimeFrameEntity_.begin));
            }
            if (criteria.getEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnd(), TimeFrameEntity_.end));
            }
            if (criteria.getOffDayId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOffDayId(), TimeFrameEntity_.offDayId));
            }
        }
        return specification;
    }
}


