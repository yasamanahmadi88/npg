package ix.portal.npg.service.mapper;

import ix.portal.npg.domain.*;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileReportGenerationLogEntity} and its DTO {@link FileReportGenerationLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileReportGenerationLogMapper extends EntityMapper<FileReportGenerationLogDTO, FileReportGenerationLogEntity> {}


