package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.domain.PortabilityEntityReport;
import ix.portal.npg.service.dto.PortabilityDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link PortabilityEntity} and its DTO {@link PortabilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PortabilityReportMapper extends EntityMapper<PortabilityDTO, PortabilityEntityReport> {}


