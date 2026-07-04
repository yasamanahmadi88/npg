package ix.portal.npg.service.impl;

import ix.portal.npg.domain.TimeFrameEntity;
import ix.portal.npg.ix.IxClient;
import ix.portal.npg.repository.TimeFrameRepository;
import ix.portal.npg.service.TimeFrameService;
import ix.portal.npg.service.dto.TimeFrameDTO;
import ix.portal.npg.service.mapper.TimeFrameMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TimeFrameEntity}.
 */
@Service
@Transactional
public class TimeFrameServiceImpl implements TimeFrameService {

    private final Logger log = LoggerFactory.getLogger(TimeFrameServiceImpl.class);

    private final TimeFrameRepository timeFrameRepository;
    private final IxClient ixClient;
    private final TimeFrameMapper timeFrameMapper;

    public TimeFrameServiceImpl(TimeFrameRepository timeFrameRepository, IxClient ixClient, TimeFrameMapper timeFrameMapper) {
        this.timeFrameRepository = timeFrameRepository;
        this.ixClient = ixClient;
        this.timeFrameMapper = timeFrameMapper;
    }

    @Override
    public TimeFrameDTO save(TimeFrameDTO timeFrameDTO) {
        log.debug("Request to save TimeFrame : {}", timeFrameDTO);
        TimeFrameEntity timeFrameEntity = timeFrameMapper.toEntity(timeFrameDTO);
        timeFrameEntity = timeFrameRepository.save(timeFrameEntity);
        try {
            ixClient.sendReloadCacheMessage("offDay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeFrameMapper.toDto(timeFrameEntity);
    }

    @Override
    public Optional<TimeFrameDTO> partialUpdate(TimeFrameDTO timeFrameDTO) {
        log.debug("Request to partially update TimeFrame : {}", timeFrameDTO);

        Optional<TimeFrameDTO> temp = timeFrameRepository
            .findById(timeFrameDTO.getId())
            .map(
                existingTimeFrame -> {
                    timeFrameMapper.partialUpdate(existingTimeFrame, timeFrameDTO);
                    return existingTimeFrame;
                }
            )
            .map(timeFrameRepository::save)
            .map(timeFrameMapper::toDto);
        try {
            ixClient.sendReloadCacheMessage("offDay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeFrameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeFrames");
        return timeFrameRepository.findAll(pageable).map(timeFrameMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeFrameDTO> findOne(Long id) {
        log.debug("Request to get TimeFrame : {}", id);
        return timeFrameRepository.findById(id).map(timeFrameMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeFrame : {}", id);
        timeFrameRepository.deleteById(id);
    }
}


