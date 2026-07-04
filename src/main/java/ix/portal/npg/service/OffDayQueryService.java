package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.OffDayEntity;
import ix.portal.npg.repository.OffDayRepository;
import ix.portal.npg.service.criteria.OffDayCriteria;
import ix.portal.npg.service.dto.OffDayDTO;
import ix.portal.npg.service.mapper.OffDayMapper;
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
 * Service for executing complex queries for {@link OffDayEntity} entities in the database.
 * The main input is a {@link OffDayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OffDayDTO} or a {@link Page} of {@link OffDayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OffDayQueryService extends QueryService<OffDayEntity> {

    private final Logger log = LoggerFactory.getLogger(OffDayQueryService.class);

    private final OffDayRepository offDayRepository;

    private final OffDayMapper offDayMapper;

    public OffDayQueryService(OffDayRepository offDayRepository, OffDayMapper offDayMapper) {
        this.offDayRepository = offDayRepository;
        this.offDayMapper = offDayMapper;
    }

    /**
     * Return a {@link List} of {@link OffDayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OffDayDTO> findByCriteria(OffDayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OffDayEntity> specification = createSpecification(criteria);
        return offDayMapper.toDto(offDayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OffDayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OffDayDTO> findByCriteria(OffDayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OffDayEntity> specification = createSpecification(criteria);
        return offDayRepository.findAll(specification, page).map(offDayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OffDayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OffDayEntity> specification = createSpecification(criteria);
        return offDayRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<OffDayDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return offDayRepository.findById(numberValue, page).map(offDayMapper::toDto);
    }

    /**
     * Function to convert {@link OffDayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OffDayEntity> createSpecification(OffDayCriteria criteria) {
        Specification<OffDayEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OffDayEntity_.id));
            }
            if (criteria.getOffDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOffDate(), OffDayEntity_.offDate));
            }
            if (criteria.getFullOff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFullOff(), OffDayEntity_.fullOff));
            }
        }
        return specification;
    }
}


