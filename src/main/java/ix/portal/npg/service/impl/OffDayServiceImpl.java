package ix.portal.npg.service.impl;

import ix.portal.npg.domain.OffDayEntity;
import ix.portal.npg.ix.IxClient;
import ix.portal.npg.repository.OffDayRepository;
import ix.portal.npg.service.OffDayService;
import ix.portal.npg.service.dto.OffDayDTO;
import ix.portal.npg.service.mapper.OffDayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OffDayEntity}.
 */
@Service
@Transactional
public class OffDayServiceImpl implements OffDayService {

    private final Logger log = LoggerFactory.getLogger(OffDayServiceImpl.class);

    private final OffDayRepository offDayRepository;
    private final IxClient ixClient;
    private final OffDayMapper offDayMapper;

    public OffDayServiceImpl(OffDayRepository offDayRepository, IxClient ixClient, OffDayMapper offDayMapper) {
        this.offDayRepository = offDayRepository;
        this.ixClient = ixClient;
        this.offDayMapper = offDayMapper;
    }

    @Override
    public OffDayDTO save(OffDayDTO offDayDTO) {
        log.debug("Request to save OffDay : {}", offDayDTO);
        OffDayEntity offDayEntity = offDayMapper.toEntity(offDayDTO);
        offDayEntity = offDayRepository.save(offDayEntity);
        try {
            ixClient.sendReloadCacheMessage("offDay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offDayMapper.toDto(offDayEntity);
    }

    @Override
    public Optional<OffDayDTO> partialUpdate(OffDayDTO offDayDTO) {
        log.debug("Request to partially update OffDay : {}", offDayDTO);

        Optional<OffDayDTO> temp = offDayRepository
            .findById(offDayDTO.getId())
            .map(
                existingOffDay -> {
                    offDayMapper.partialUpdate(existingOffDay, offDayDTO);

                    return existingOffDay;
                }
            )
            .map(offDayRepository::save)
            .map(offDayMapper::toDto);
        try {
            ixClient.sendReloadCacheMessage("offDay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OffDayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OffDays");
        return offDayRepository.findAll(pageable).map(offDayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OffDayDTO> findOne(Long id) {
        log.debug("Request to get OffDay : {}", id);
        return offDayRepository.findById(id).map(offDayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OffDay : {}", id);
        offDayRepository.deleteById(id);
    }
}


