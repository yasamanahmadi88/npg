package ix.portal.npg.service.impl;

import ix.portal.npg.domain.FileReportGenerationLogEntity;
import ix.portal.npg.repository.FileReportGenerationLogRepository;
import ix.portal.npg.service.FileReportGenerationLogService;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import ix.portal.npg.service.mapper.FileReportGenerationLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileReportGenerationLogEntity}.
 */
@Service
@Transactional
public class FileReportGenerationLogServiceImpl implements FileReportGenerationLogService {

    private final Logger log = LoggerFactory.getLogger(FileReportGenerationLogServiceImpl.class);

    private final FileReportGenerationLogRepository fileReportGenerationLogRepository;

    private final FileReportGenerationLogMapper fileReportGenerationLogMapper;

    public FileReportGenerationLogServiceImpl(
        FileReportGenerationLogRepository fileReportGenerationLogRepository,
        FileReportGenerationLogMapper fileReportGenerationLogMapper
    ) {
        this.fileReportGenerationLogRepository = fileReportGenerationLogRepository;
        this.fileReportGenerationLogMapper = fileReportGenerationLogMapper;
    }

    @Override
    public FileReportGenerationLogDTO save(FileReportGenerationLogDTO fileReportGenerationLogDTO) {
        log.debug("Request to save FileReportGenerationLog : {}", fileReportGenerationLogDTO);
        FileReportGenerationLogEntity fileReportGenerationLogEntity = fileReportGenerationLogMapper.toEntity(fileReportGenerationLogDTO);
        fileReportGenerationLogEntity = fileReportGenerationLogRepository.save(fileReportGenerationLogEntity);
        return fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
    }

    @Override
    public Optional<FileReportGenerationLogDTO> partialUpdate(FileReportGenerationLogDTO fileReportGenerationLogDTO) {
        log.debug("Request to partially update FileReportGenerationLog : {}", fileReportGenerationLogDTO);

        return fileReportGenerationLogRepository
            .findById(fileReportGenerationLogDTO.getId())
            .map(
                existingFileReportGenerationLog -> {
                    fileReportGenerationLogMapper.partialUpdate(existingFileReportGenerationLog, fileReportGenerationLogDTO);

                    return existingFileReportGenerationLog;
                }
            )
            .map(fileReportGenerationLogRepository::save)
            .map(fileReportGenerationLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileReportGenerationLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileReportGenerationLogs");
        return fileReportGenerationLogRepository.findAll(pageable).map(fileReportGenerationLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileReportGenerationLogDTO> findOne(Long id) {
        log.debug("Request to get FileReportGenerationLog : {}", id);
        return fileReportGenerationLogRepository.findById(id).map(fileReportGenerationLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileReportGenerationLog : {}", id);
        fileReportGenerationLogRepository.deleteById(id);
    }
}


