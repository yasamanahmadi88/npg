package ix.portal.npg.service.impl;

import ix.portal.npg.domain.UndifinedStatusEntity;
import ix.portal.npg.repository.UndifinedStatusRepository;
import ix.portal.npg.service.UndifinedStatusService;
import ix.portal.npg.service.dto.UndifinedStatusDTO;
import ix.portal.npg.service.mapper.UndifinedStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UndifinedStatusEntity}.
 */
@Service
@Transactional
public class UndifinedStatusServiceImpl implements UndifinedStatusService {

    private final Logger log = LoggerFactory.getLogger(UndifinedStatusServiceImpl.class);

    private final UndifinedStatusRepository undifinedStatusRepository;

    private final UndifinedStatusMapper undifinedStatusMapper;

    public UndifinedStatusServiceImpl(UndifinedStatusRepository undifinedStatusRepository, UndifinedStatusMapper undifinedStatusMapper) {
        this.undifinedStatusRepository = undifinedStatusRepository;
        this.undifinedStatusMapper = undifinedStatusMapper;
    }

    @Override
    public UndifinedStatusDTO save(UndifinedStatusDTO undifinedStatusDTO) {
        log.debug("Request to save UndifinedStatus : {}", undifinedStatusDTO);
        UndifinedStatusEntity undifinedStatusEntity = undifinedStatusMapper.toEntity(undifinedStatusDTO);
        undifinedStatusEntity = undifinedStatusRepository.save(undifinedStatusEntity);
        return undifinedStatusMapper.toDto(undifinedStatusEntity);
    }

    @Override
    public Optional<UndifinedStatusDTO> partialUpdate(UndifinedStatusDTO undifinedStatusDTO) {
        log.debug("Request to partially update UndifinedStatus : {}", undifinedStatusDTO);

        return undifinedStatusRepository
            .findById(undifinedStatusDTO.getId())
            .map(
                existingUndifinedStatus -> {
                    undifinedStatusMapper.partialUpdate(existingUndifinedStatus, undifinedStatusDTO);

                    return existingUndifinedStatus;
                }
            )
            .map(undifinedStatusRepository::save)
            .map(undifinedStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UndifinedStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UndifinedStatuses");
        return undifinedStatusRepository.findAll(pageable).map(undifinedStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UndifinedStatusDTO> findOne(Long id) {
        log.debug("Request to get UndifinedStatus : {}", id);
        return undifinedStatusRepository.findById(id).map(undifinedStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UndifinedStatus : {}", id);
        undifinedStatusRepository.deleteById(id);
    }
}


