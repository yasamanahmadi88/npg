package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.EventLogEntity;
import ix.portal.npg.repository.EventLogRepository;
import ix.portal.npg.service.criteria.EventLogCriteria;
import ix.portal.npg.service.dto.EventLogDTO;
import ix.portal.npg.service.mapper.EventLogMapper;
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
 * Service for executing complex queries for {@link EventLogEntity} entities in the database.
 * The main input is a {@link EventLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventLogDTO} or a {@link Page} of {@link EventLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventLogQueryService extends QueryService<EventLogEntity> {

    private final Logger log = LoggerFactory.getLogger(EventLogQueryService.class);

    private final EventLogRepository eventLogRepository;

    private final EventLogMapper eventLogMapper;

    public EventLogQueryService(EventLogRepository eventLogRepository, EventLogMapper eventLogMapper) {
        this.eventLogRepository = eventLogRepository;
        this.eventLogMapper = eventLogMapper;
    }

    /**
     * Return a {@link List} of {@link EventLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventLogDTO> findByCriteria(EventLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventLogEntity> specification = createSpecification(criteria);
        return eventLogMapper.toDto(eventLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventLogDTO> findByCriteria(EventLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventLogEntity> specification = createSpecification(criteria);
        return eventLogRepository.findAll(specification, page).map(eventLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventLogEntity> specification = createSpecification(criteria);
        return eventLogRepository.count(specification);
    }

    /**
     * Function to convert {@link EventLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventLogEntity> createSpecification(EventLogCriteria criteria) {
        Specification<EventLogEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventLogEntity_.id));
            }
            if (criteria.getConversationId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConversationId(), EventLogEntity_.conversationId));
            }
            if (criteria.getSender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSender(), EventLogEntity_.sender));
            }
            if (criteria.getReceiver() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiver(), EventLogEntity_.receiver));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), EventLogEntity_.message));
            }
            if (criteria.getRequestBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestBody(), EventLogEntity_.requestBody));
            }
            if (criteria.getResponseBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseBody(), EventLogEntity_.responseBody));
            }
            if (criteria.getFlg0Ordinary1Exception() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFlg0Ordinary1Exception(), EventLogEntity_.flg0Ordinary1Exception)
                    );
            }
            if (criteria.getEventSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEventSource(), EventLogEntity_.eventSource));
            }
            if (criteria.getExceptionBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExceptionBody(), EventLogEntity_.exceptionBody));
            }
            if (criteria.getHttpStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHttpStatus(), EventLogEntity_.httpStatus));
            }
            if (criteria.getInsertTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInsertTimestamp(), EventLogEntity_.insertTimestamp));
            }
        }
        return specification;
    }
}


