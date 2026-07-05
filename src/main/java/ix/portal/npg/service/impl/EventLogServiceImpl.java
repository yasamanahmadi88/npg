package ix.portal.npg.service.impl;

import ix.portal.npg.domain.EventLogEntity;
import ix.portal.npg.repository.EventLogRepository;
import ix.portal.npg.service.EventLogService;
import ix.portal.npg.service.dto.EventLogDTO;
import ix.portal.npg.service.mapper.EventLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventLogEntity}.
 */
@Service
@Transactional
public class EventLogServiceImpl implements EventLogService {

    private final Logger log = LoggerFactory.getLogger(EventLogServiceImpl.class);

    private final EventLogRepository eventLogRepository;

    private final EventLogMapper eventLogMapper;

    public EventLogServiceImpl(EventLogRepository eventLogRepository, EventLogMapper eventLogMapper) {
        this.eventLogRepository = eventLogRepository;
        this.eventLogMapper = eventLogMapper;
    }

    @Override
    public EventLogDTO save(EventLogDTO eventLogDTO) {
        log.debug("Request to save EventLog : {}", eventLogDTO);
        EventLogEntity eventLogEntity = eventLogMapper.toEntity(eventLogDTO);
        eventLogEntity = eventLogRepository.save(eventLogEntity);
        return eventLogMapper.toDto(eventLogEntity);
    }

    @Override
    public Optional<EventLogDTO> partialUpdate(EventLogDTO eventLogDTO) {
        log.debug("Request to partially update EventLog : {}", eventLogDTO);

        return eventLogRepository
            .findById(eventLogDTO.getId())
            .map(
                existingEventLog -> {
                    eventLogMapper.partialUpdate(existingEventLog, eventLogDTO);

                    return existingEventLog;
                }
            )
            .map(eventLogRepository::save)
            .map(eventLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventLogs");
        return eventLogRepository.findAll(pageable).map(eventLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventLogDTO> findOne(Long id) {
        log.debug("Request to get EventLog : {}", id);
        return eventLogRepository.findById(id).map(eventLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventLog : {}", id);
        eventLogRepository.deleteById(id);
    }
}


