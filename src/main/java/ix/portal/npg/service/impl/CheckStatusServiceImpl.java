package ix.portal.npg.service.impl;

import ix.portal.npg.domain.CheckStatusEntity;
import ix.portal.npg.repository.CheckStatusRepository;
import ix.portal.npg.service.CheckStatusService;
import ix.portal.npg.service.dto.CheckStatusDTO;
import ix.portal.npg.service.mapper.CheckStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckStatusEntity}.
 */
@Service
@Transactional
public class CheckStatusServiceImpl implements CheckStatusService {

    private final Logger log = LoggerFactory.getLogger(CheckStatusServiceImpl.class);

    private final CheckStatusRepository checkStatusRepository;

    private final CheckStatusMapper checkStatusMapper;

    public CheckStatusServiceImpl(CheckStatusRepository checkStatusRepository, CheckStatusMapper checkStatusMapper) {
        this.checkStatusRepository = checkStatusRepository;
        this.checkStatusMapper = checkStatusMapper;
    }

    @Override
    public CheckStatusDTO save(CheckStatusDTO checkStatusDTO) {
        log.debug("Request to save CheckStatus : {}", checkStatusDTO);
        CheckStatusEntity checkStatusEntity = checkStatusMapper.toEntity(checkStatusDTO);
        checkStatusEntity = checkStatusRepository.save(checkStatusEntity);
        return checkStatusMapper.toDto(checkStatusEntity);
    }

    @Override
    public Optional<CheckStatusDTO> partialUpdate(CheckStatusDTO checkStatusDTO) {
        log.debug("Request to partially update CheckStatus : {}", checkStatusDTO);

        return checkStatusRepository
            .findById(checkStatusDTO.getId())
            .map(
                existingCheckStatus -> {
                    checkStatusMapper.partialUpdate(existingCheckStatus, checkStatusDTO);

                    return existingCheckStatus;
                }
            )
            .map(checkStatusRepository::save)
            .map(checkStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckStatuses");
        return checkStatusRepository.findAll(pageable).map(checkStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckStatusDTO> findOne(Long id) {
        log.debug("Request to get CheckStatus : {}", id);
        return checkStatusRepository.findById(id).map(checkStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckStatus : {}", id);
        checkStatusRepository.deleteById(id);
    }
}


