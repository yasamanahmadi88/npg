package ix.portal.npg.service.impl;

import ix.portal.npg.domain.PortabilityLogEntity;
import ix.portal.npg.repository.PortabilityLogRepository;
import ix.portal.npg.service.PortabilityLogService;
import ix.portal.npg.service.dto.PortabilityLogDTO;
import ix.portal.npg.service.mapper.PortabilityLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PortabilityLogEntity}.
 */
@Service
@Transactional
public class PortabilityLogServiceImpl implements PortabilityLogService {

    private final Logger log = LoggerFactory.getLogger(PortabilityLogServiceImpl.class);

    private final PortabilityLogRepository portabilityLogRepository;

    private final PortabilityLogMapper portabilityLogMapper;

    public PortabilityLogServiceImpl(PortabilityLogRepository portabilityLogRepository, PortabilityLogMapper portabilityLogMapper) {
        this.portabilityLogRepository = portabilityLogRepository;
        this.portabilityLogMapper = portabilityLogMapper;
    }

    @Override
    public PortabilityLogDTO save(PortabilityLogDTO portabilityLogDTO) {
        log.debug("Request to save PortabilityLog : {}", portabilityLogDTO);
        PortabilityLogEntity portabilityLogEntity = portabilityLogMapper.toEntity(portabilityLogDTO);
        portabilityLogEntity = portabilityLogRepository.save(portabilityLogEntity);
        return portabilityLogMapper.toDto(portabilityLogEntity);
    }

    @Override
    public Optional<PortabilityLogDTO> partialUpdate(PortabilityLogDTO portabilityLogDTO) {
        log.debug("Request to partially update PortabilityLog : {}", portabilityLogDTO);

        return portabilityLogRepository
            .findById(portabilityLogDTO.getId())
            .map(
                existingPortabilityLog -> {
                    portabilityLogMapper.partialUpdate(existingPortabilityLog, portabilityLogDTO);

                    return existingPortabilityLog;
                }
            )
            .map(portabilityLogRepository::save)
            .map(portabilityLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PortabilityLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PortabilityLogs");
        return portabilityLogRepository.findAll(pageable).map(portabilityLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PortabilityLogDTO> findOne(Long id) {
        log.debug("Request to get PortabilityLog : {}", id);
        return portabilityLogRepository.findById(id).map(portabilityLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PortabilityLog : {}", id);
        portabilityLogRepository.deleteById(id);
    }
}


