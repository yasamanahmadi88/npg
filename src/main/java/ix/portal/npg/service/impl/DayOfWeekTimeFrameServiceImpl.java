package ix.portal.npg.service.impl;

import ix.portal.npg.domain.DayOfWeekTimeFrameEntity;
import ix.portal.npg.ix.IxClient;
import ix.portal.npg.repository.DayOfWeekTimeFrameRepository;
import ix.portal.npg.service.DayOfWeekTimeFrameService;
import ix.portal.npg.service.dto.DayOfWeekTimeFrameDTO;
import ix.portal.npg.service.mapper.DayOfWeekTimeFrameMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DayOfWeekTimeFrameEntity}.
 */
@Service
@Transactional
public class DayOfWeekTimeFrameServiceImpl implements DayOfWeekTimeFrameService {

    private final Logger log = LoggerFactory.getLogger(DayOfWeekTimeFrameServiceImpl.class);

    private final DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository;
    private final IxClient ixClient;
    private final DayOfWeekTimeFrameMapper dayOfWeekTimeFrameMapper;

    public DayOfWeekTimeFrameServiceImpl(
        DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository,
        IxClient ixClient,
        DayOfWeekTimeFrameMapper dayOfWeekTimeFrameMapper
    ) {
        this.dayOfWeekTimeFrameRepository = dayOfWeekTimeFrameRepository;
        this.ixClient = ixClient;
        this.dayOfWeekTimeFrameMapper = dayOfWeekTimeFrameMapper;
    }

    @Override
    public DayOfWeekTimeFrameDTO save(DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO) {
        log.debug("Request to save DayOfWeekTimeFrame : {}", dayOfWeekTimeFrameDTO);
        DayOfWeekTimeFrameEntity dayOfWeekTimeFrameEntity = dayOfWeekTimeFrameMapper.toEntity(dayOfWeekTimeFrameDTO);
        dayOfWeekTimeFrameEntity = dayOfWeekTimeFrameRepository.save(dayOfWeekTimeFrameEntity);
        try {
            ixClient.sendReloadCacheMessage("weekWorkingTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);
    }

    @Override
    public Optional<DayOfWeekTimeFrameDTO> partialUpdate(DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO) {
        log.debug("Request to partially update DayOfWeekTimeFrame : {}", dayOfWeekTimeFrameDTO);

        Optional<DayOfWeekTimeFrameDTO> temp = dayOfWeekTimeFrameRepository
            .findById(dayOfWeekTimeFrameDTO.getId())
            .map(
                existingDayOfWeekTimeFrame -> {
                    dayOfWeekTimeFrameMapper.partialUpdate(existingDayOfWeekTimeFrame, dayOfWeekTimeFrameDTO);

                    return existingDayOfWeekTimeFrame;
                }
            )
            .map(dayOfWeekTimeFrameRepository::save)
            .map(dayOfWeekTimeFrameMapper::toDto);
        try {
            ixClient.sendReloadCacheMessage("weekWorkingTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DayOfWeekTimeFrameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DayOfWeekTimeFrames");
        return dayOfWeekTimeFrameRepository.findAll(pageable).map(dayOfWeekTimeFrameMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DayOfWeekTimeFrameDTO> findOne(Long id) {
        log.debug("Request to get DayOfWeekTimeFrame : {}", id);
        return dayOfWeekTimeFrameRepository.findById(id).map(dayOfWeekTimeFrameMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DayOfWeekTimeFrame : {}", id);
        dayOfWeekTimeFrameRepository.deleteById(id);
    }
}


