package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.PortabilityLogEntity;
import ix.portal.npg.repository.PortabilityLogRepository;
import ix.portal.npg.service.criteria.PortabilityLogCriteria;
import ix.portal.npg.service.dto.PortabilityLogDTO;
import ix.portal.npg.service.mapper.PortabilityLogMapper;
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
 * Service for executing complex queries for {@link PortabilityLogEntity} entities in the database.
 * The main input is a {@link PortabilityLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PortabilityLogDTO} or a {@link Page} of {@link PortabilityLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PortabilityLogQueryService extends QueryService<PortabilityLogEntity> {

    private final Logger log = LoggerFactory.getLogger(PortabilityLogQueryService.class);

    private final PortabilityLogRepository portabilityLogRepository;

    private final PortabilityLogMapper portabilityLogMapper;

    public PortabilityLogQueryService(PortabilityLogRepository portabilityLogRepository, PortabilityLogMapper portabilityLogMapper) {
        this.portabilityLogRepository = portabilityLogRepository;
        this.portabilityLogMapper = portabilityLogMapper;
    }

    /**
     * Return a {@link List} of {@link PortabilityLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PortabilityLogDTO> findByCriteria(PortabilityLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PortabilityLogEntity> specification = createSpecification(criteria);
        return portabilityLogMapper.toDto(portabilityLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PortabilityLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PortabilityLogDTO> findByCriteria(PortabilityLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PortabilityLogEntity> specification = createSpecification(criteria);
        return portabilityLogRepository.findAll(specification, page).map(portabilityLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PortabilityLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PortabilityLogEntity> specification = createSpecification(criteria);
        return portabilityLogRepository.count(specification);
    }

    /**
     * Function to convert {@link PortabilityLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PortabilityLogEntity> createSpecification(PortabilityLogCriteria criteria) {
        Specification<PortabilityLogEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PortabilityLogEntity_.id));
            }
            if (criteria.getPorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorId(), PortabilityLogEntity_.porId));
            }
            if (criteria.getPorRequestId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRequestId(), PortabilityLogEntity_.porRequestId));
            }
            if (criteria.getPorNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNumber(), PortabilityLogEntity_.porNumber));
            }
            if (criteria.getPorLegalTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorLegalTerm(), PortabilityLogEntity_.porLegalTerm));
            }
            if (criteria.getPorOpr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpr(), PortabilityLogEntity_.porOpr));
            }
            if (criteria.getPorAccType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorAccType(), PortabilityLogEntity_.porAccType));
            }
            if (criteria.getPorIdNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorIdNumber(), PortabilityLogEntity_.porIdNumber));
            }
            if (criteria.getPorContactNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorContactNumber(), PortabilityLogEntity_.porContactNumber));
            }
            if (criteria.getPorStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorStatus(), PortabilityLogEntity_.porStatus));
            }
            if (criteria.getPorPortedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorPortedDate(), PortabilityLogEntity_.porPortedDate));
            }
            if (criteria.getPorRouting() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRouting(), PortabilityLogEntity_.porRouting));
            }
            if (criteria.getPorType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorType(), PortabilityLogEntity_.porType));
            }
            if (criteria.getPorOpOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpOrg(), PortabilityLogEntity_.porOpOrg));
            }
            if (criteria.getPorRspCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspCode(), PortabilityLogEntity_.porRspCode));
            }
            if (criteria.getPorRspNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspNote(), PortabilityLogEntity_.porRspNote));
            }
            if (criteria.getPorCancelNote() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorCancelNote(), PortabilityLogEntity_.porCancelNote));
            }
            if (criteria.getPorMnpid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorMnpid(), PortabilityLogEntity_.porMnpid));
            }
            if (criteria.getPortationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPortationDate(), PortabilityLogEntity_.portationDate));
            }
            if (criteria.getPortaCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortaCode(), PortabilityLogEntity_.portaCode));
            }
            if (criteria.getMvno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMvno(), PortabilityLogEntity_.mvno));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), PortabilityLogEntity_.context));
            }
            if (criteria.getPorErrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorErrCode(), PortabilityLogEntity_.porErrCode));
            }
            if (criteria.getPorErrMessage() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorErrMessage(), PortabilityLogEntity_.porErrMessage));
            }
            if (criteria.getPorOpd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpd(), PortabilityLogEntity_.porOpd));
            }
            if (criteria.getPorNumType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNumType(), PortabilityLogEntity_.porNumType));
            }
            if (criteria.getPorNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNote(), PortabilityLogEntity_.porNote));
            }
            if (criteria.getPorDeadline() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorDeadline(), PortabilityLogEntity_.porDeadline));
            }
            if (criteria.getPorResponseTimestamp() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPorResponseTimestamp(), PortabilityLogEntity_.porResponseTimestamp)
                    );
            }
            if (criteria.getPorEligible() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorEligible(), PortabilityLogEntity_.porEligible));
            }
            if (criteria.getPorBillingOk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorBillingOk(), PortabilityLogEntity_.porBillingOk));
            }
            if (criteria.getIntermediaryActionState() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIntermediaryActionState(), PortabilityLogEntity_.intermediaryActionState)
                    );
            }
            if (criteria.getPorCrDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorCrDate(), PortabilityLogEntity_.porCrDate));
            }
            if (criteria.getPorUpdDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorUpdDate(), PortabilityLogEntity_.porUpdDate));
            }
            if (criteria.getPorTechStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorTechStatus(), PortabilityLogEntity_.porTechStatus));
            }
            if (criteria.getPorTechDeadline() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorTechDeadline(), PortabilityLogEntity_.porTechDeadline));
            }
            if (criteria.getRefPorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefPorId(), PortabilityLogEntity_.refPorId));
            }
            if (criteria.getNeedManualRetry() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNeedManualRetry(), PortabilityLogEntity_.needManualRetry));
            }
            if (criteria.getRetryCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetryCount(), PortabilityLogEntity_.retryCount));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), PortabilityLogEntity_.action));
            }
            if (criteria.getRequest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequest(), PortabilityLogEntity_.request));
            }
            if (criteria.getInsertTimestamp() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInsertTimestamp(), PortabilityLogEntity_.insertTimestamp));
            }
        }
        return specification;
    }
}


