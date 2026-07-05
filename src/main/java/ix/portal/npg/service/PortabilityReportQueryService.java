package ix.portal.npg.service;

import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.domain.PortabilityEntityReport;
import ix.portal.npg.domain.PortabilityEntityReport_;
import ix.portal.npg.domain.PortabilityEntity_;
import ix.portal.npg.domain.model.GeneralReport;
import ix.portal.npg.domain.model.SimpleReport;
import ix.portal.npg.repository.PortabilityRepository;
import ix.portal.npg.repository.PortabilityRepositoryReport;
import ix.portal.npg.service.criteria.PortabilityCriteria;
import ix.portal.npg.service.criteria.PortabilityReportCriteria;
import ix.portal.npg.service.dto.PortabilityDTO;
import ix.portal.npg.service.mapper.PortabilityMapper;
import ix.portal.npg.service.mapper.PortabilityReportMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
 * Service for executing complex queries for {@link PortabilityEntity} entities in the database.
 * The main input is a {@link PortabilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PortabilityDTO} or a {@link Page} of {@link PortabilityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PortabilityReportQueryService extends QueryService<PortabilityEntityReport> {

    private final Logger log = LoggerFactory.getLogger(PortabilityReportQueryService.class);
    private final PortabilityRepositoryReport portabilityRepositoryReport;

    private final PortabilityReportMapper portabilityReportMapper;

    public PortabilityReportQueryService(
        PortabilityRepositoryReport portabilityRepositoryReport,
        PortabilityReportMapper portabilityReportMapper
    ) {
        this.portabilityRepositoryReport = portabilityRepositoryReport;
        this.portabilityReportMapper = portabilityReportMapper;
    }

    @Transactional(readOnly = true)
    public Page<PortabilityDTO> findByCriteria(PortabilityReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PortabilityEntityReport> specification = createSpecification(criteria);
        return portabilityRepositoryReport.findAll(specification, page).map(portabilityReportMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(PortabilityReportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PortabilityEntityReport> specification = createSpecification(criteria);
        return portabilityRepositoryReport.count(specification);
    }

    protected Specification<PortabilityEntityReport> createSpecification(PortabilityReportCriteria criteria) {
        Specification<PortabilityEntityReport> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PortabilityEntityReport_.id));
            }
            if (criteria.getPorRequestId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorRequestId(), PortabilityEntityReport_.porRequestId));
            }
            if (criteria.getPorNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNumber(), PortabilityEntityReport_.porNumber));
            }
            if (criteria.getPorLegalTerm() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorLegalTerm(), PortabilityEntityReport_.porLegalTerm));
            }
            if (criteria.getPorOpr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpr(), PortabilityEntityReport_.porOpr));
            }
            if (criteria.getPorAccType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorAccType(), PortabilityEntityReport_.porAccType));
            }
            if (criteria.getPorIdNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorIdNumber(), PortabilityEntityReport_.porIdNumber));
            }
            if (criteria.getPorContactNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorContactNumber(), PortabilityEntityReport_.porContactNumber));
            }
            if (criteria.getPorStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorStatus(), PortabilityEntityReport_.porStatus));
            }
            if (criteria.getPorPortedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorPortedDate(), PortabilityEntityReport_.porPortedDate));
            }
            if (criteria.getPorRouting() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRouting(), PortabilityEntityReport_.porRouting));
            }
            if (criteria.getPorType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorType(), PortabilityEntityReport_.porType));
            }
            if (criteria.getPorOpOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpOrg(), PortabilityEntityReport_.porOpOrg));
            }
            if (criteria.getPorRspCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspCode(), PortabilityEntityReport_.porRspCode));
            }
            if (criteria.getPorRspNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspNote(), PortabilityEntityReport_.porRspNote));
            }
            if (criteria.getPorCancelNote() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorCancelNote(), PortabilityEntityReport_.porCancelNote));
            }
            if (criteria.getPorMnpid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorMnpid(), PortabilityEntityReport_.porMnpid));
            }
            if (criteria.getPortationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPortationDate(), PortabilityEntityReport_.portationDate));
            }
            if (criteria.getPortaCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortaCode(), PortabilityEntityReport_.portaCode));
            }
            if (criteria.getMvno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMvno(), PortabilityEntityReport_.mvno));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), PortabilityEntityReport_.context));
            }
            if (criteria.getPorErrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorErrCode(), PortabilityEntityReport_.porErrCode));
            }
            if (criteria.getPorErrMessage() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorErrMessage(), PortabilityEntityReport_.porErrMessage));
            }
            if (criteria.getPorOpd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpd(), PortabilityEntityReport_.porOpd));
            }
            if (criteria.getPorNumType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNumType(), PortabilityEntityReport_.porNumType));
            }
            if (criteria.getPorNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNote(), PortabilityEntityReport_.porNote));
            }
            if (criteria.getPorDeadline() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorDeadline(), PortabilityEntityReport_.porDeadline));
            }
            if (criteria.getPorResponseTimestamp() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPorResponseTimestamp(), PortabilityEntityReport_.porResponseTimestamp)
                    );
            }
            if (criteria.getPorEligible() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorEligible(), PortabilityEntityReport_.porEligible));
            }
            if (criteria.getPorBillingOk() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorBillingOk(), PortabilityEntityReport_.porBillingOk));
            }
            if (criteria.getIntermediaryActionState() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIntermediaryActionState(), PortabilityEntityReport_.intermediaryActionState)
                    );
            }
            if (criteria.getPorCrDateSearch() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorCrDateSearch(), PortabilityEntityReport_.porCrDate));
            }
            if (criteria.getPorUpdDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorUpdDate(), PortabilityEntityReport_.porUpdDate));
            }
            if (criteria.getPorTechStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorTechStatus(), PortabilityEntityReport_.porTechStatus));
            }
            if (criteria.getPorTechDeadline() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorTechDeadline(), PortabilityEntityReport_.porTechDeadline));
            }
            if (criteria.getNeedManualRetry() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNeedManualRetry(), PortabilityEntityReport_.needManualRetry));
            }
            if (criteria.getRefPorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefPorId(), PortabilityEntityReport_.refPorId));
            }
            if (criteria.getRetryCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetryCount(), PortabilityEntityReport_.retryCount));
            }
        }
        return specification;
    }
}


