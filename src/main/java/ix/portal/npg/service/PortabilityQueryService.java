package ix.portal.npg.service;

import ix.portal.npg.domain.*; // for static metamodels
import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.domain.model.GeneralReport;
import ix.portal.npg.domain.model.SimpleReport;
import ix.portal.npg.repository.PortabilityRepository;
import ix.portal.npg.service.criteria.PortabilityCriteria;
import ix.portal.npg.service.criteria.PortabilityReportCriteria;
import ix.portal.npg.service.dto.PortabilityDTO;
import ix.portal.npg.service.mapper.PortabilityMapper;
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
public class PortabilityQueryService extends QueryService<PortabilityEntity> {

    private final Logger log = LoggerFactory.getLogger(PortabilityQueryService.class);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final PortabilityRepository portabilityRepository;

    private final PortabilityMapper portabilityMapper;

    public PortabilityQueryService(PortabilityRepository portabilityRepository, PortabilityMapper portabilityMapper) {
        this.portabilityRepository = portabilityRepository;
        this.portabilityMapper = portabilityMapper;
    }

    /**
     * Return a {@link List} of {@link PortabilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PortabilityDTO> findByCriteria(PortabilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PortabilityEntity> specification = createSpecification(criteria);
        return portabilityMapper.toDto(portabilityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PortabilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PortabilityDTO> findByCriteria(PortabilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PortabilityEntity> specification = createSpecification(criteria);
        return portabilityRepository.findAll(specification, page).map(portabilityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PortabilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PortabilityEntity> specification = createSpecification(criteria);
        return portabilityRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public Page<PortabilityDTO> searchByText(String text, Pageable page) {
        log.debug("find by text : {}, page: {}", text, page);
        Long numberValue = StringUtils.isNumeric(text) ? Long.parseLong(text) : 0;
        return portabilityRepository
            .findByIdOrPorRequestIdContainingIgnoreCaseOrPorNumberContainingIgnoreCaseOrPorOprContainingIgnoreCaseOrPorAccTypeContainingIgnoreCaseOrPorIdNumberContainingIgnoreCaseOrPorContactNumberContainingIgnoreCaseOrPorStatusContainingIgnoreCaseOrPorRoutingContainingIgnoreCaseOrPorTypeContainingIgnoreCaseOrPorOpOrgContainingIgnoreCaseOrPorRspCodeContainingIgnoreCaseOrPorRspNoteContainingIgnoreCaseOrPorCancelNoteContainingIgnoreCaseOrPorMnpidContainingIgnoreCaseOrPortaCodeContainingIgnoreCaseOrMvnoContainingIgnoreCaseOrContextContainingIgnoreCaseOrPorTechStatusContainingIgnoreCaseOrPorErrCodeContainingIgnoreCaseOrPorErrMessageContainingIgnoreCaseOrPorOpdContainingIgnoreCaseOrPorNoteContainingIgnoreCaseOrIntermediaryActionStateContainingIgnoreCaseOrPorNumTypeContainingIgnoreCase(
                numberValue,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                text,
                page
            )
            .map(portabilityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SimpleReport> findPortTypeReport(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("find by dates start: {}, end:{}", startDate, endDate);
        return portabilityRepository.findPorTypeCount(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<GeneralReport> findPorDateReport(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("find by dates start: {}, end:{}", startDate, endDate);

        List<GeneralReport> reportList = new ArrayList<>();
        List<Object[]> temp = portabilityRepository.findPorDateCount(startDate, endDate);
        /** index[0] tarikh, index[1] name, index[2] value **/
        temp.forEach(
            gr1 -> {
                if (reportList.stream().anyMatch(rp -> rp.getName().equalsIgnoreCase((String) gr1[0]))) {
                    List<GeneralReport> report = reportList
                        .stream()
                        .filter(rp -> rp.getName().equalsIgnoreCase((String) gr1[0]))
                        .collect(Collectors.toList());
                    GeneralReport generalReport = new GeneralReport();
                    generalReport.setName((String) gr1[1]).setValue(((BigDecimal) gr1[2]).longValue());
                    List<GeneralReport> myList2 = report.get(0).getSeries();
                    myList2.add(generalReport);
                } else {
                    GeneralReport generalReport = new GeneralReport();
                    generalReport.setName((String) gr1[1]).setValue(((BigDecimal) gr1[2]).longValue());
                    List<GeneralReport> myList = new ArrayList<>();
                    myList.add(generalReport);
                    reportList.add(new GeneralReport().setName((String) gr1[0]).setSeries(myList));
                }
            }
        );

        return reportList;
    }

    /**
     * Function to convert {@link PortabilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PortabilityEntity> createSpecification(PortabilityCriteria criteria) {
        Specification<PortabilityEntity> specification = Specification.unrestricted();
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PortabilityEntity_.id));
            }
            if (criteria.getPorRequestId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRequestId(), PortabilityEntity_.porRequestId));
            }
            if (criteria.getPorNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNumber(), PortabilityEntity_.porNumber));
            }
            if (criteria.getPorLegalTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorLegalTerm(), PortabilityEntity_.porLegalTerm));
            }
            if (criteria.getPorOpr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpr(), PortabilityEntity_.porOpr));
            }
            if (criteria.getPorAccType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorAccType(), PortabilityEntity_.porAccType));
            }
            if (criteria.getPorIdNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorIdNumber(), PortabilityEntity_.porIdNumber));
            }
            if (criteria.getPorContactNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPorContactNumber(), PortabilityEntity_.porContactNumber));
            }
            if (criteria.getPorStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorStatus(), PortabilityEntity_.porStatus));
            }
            if (criteria.getPorPortedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorPortedDate(), PortabilityEntity_.porPortedDate));
            }
            if (criteria.getPorRouting() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRouting(), PortabilityEntity_.porRouting));
            }
            if (criteria.getPorType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorType(), PortabilityEntity_.porType));
            }
            if (criteria.getPorOpOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpOrg(), PortabilityEntity_.porOpOrg));
            }
            if (criteria.getPorRspCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspCode(), PortabilityEntity_.porRspCode));
            }
            if (criteria.getPorRspNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorRspNote(), PortabilityEntity_.porRspNote));
            }
            if (criteria.getPorCancelNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorCancelNote(), PortabilityEntity_.porCancelNote));
            }
            if (criteria.getPorMnpid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorMnpid(), PortabilityEntity_.porMnpid));
            }
            if (criteria.getPortationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPortationDate(), PortabilityEntity_.portationDate));
            }
            if (criteria.getPortaCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortaCode(), PortabilityEntity_.portaCode));
            }
            if (criteria.getMvno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMvno(), PortabilityEntity_.mvno));
            }
            if (criteria.getContext() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContext(), PortabilityEntity_.context));
            }
            if (criteria.getPorErrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorErrCode(), PortabilityEntity_.porErrCode));
            }
            if (criteria.getPorErrMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorErrMessage(), PortabilityEntity_.porErrMessage));
            }
            if (criteria.getPorOpd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorOpd(), PortabilityEntity_.porOpd));
            }
            if (criteria.getPorNumType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNumType(), PortabilityEntity_.porNumType));
            }
            if (criteria.getPorNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorNote(), PortabilityEntity_.porNote));
            }
            if (criteria.getPorDeadline() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorDeadline(), PortabilityEntity_.porDeadline));
            }
            if (criteria.getPorResponseTimestamp() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorResponseTimestamp(), PortabilityEntity_.porResponseTimestamp));
            }
            if (criteria.getPorEligible() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorEligible(), PortabilityEntity_.porEligible));
            }
            if (criteria.getPorBillingOk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorBillingOk(), PortabilityEntity_.porBillingOk));
            }
            if (criteria.getIntermediaryActionState() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIntermediaryActionState(), PortabilityEntity_.intermediaryActionState)
                    );
            }
            if (criteria.getPorCrDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorCrDate(), PortabilityEntity_.porCrDate));
            }
            if (criteria.getPorUpdDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorUpdDate(), PortabilityEntity_.porUpdDate));
            }
            if (criteria.getPorTechStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPorTechStatus(), PortabilityEntity_.porTechStatus));
            }
            if (criteria.getPorTechDeadline() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPorTechDeadline(), PortabilityEntity_.porTechDeadline));
            }
            if (criteria.getNeedManualRetry() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNeedManualRetry(), PortabilityEntity_.needManualRetry));
            }
            if (criteria.getRefPorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefPorId(), PortabilityEntity_.refPorId));
            }
            if (criteria.getRetryCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetryCount(), PortabilityEntity_.retryCount));
            }
        }
        return specification;
    }
}


