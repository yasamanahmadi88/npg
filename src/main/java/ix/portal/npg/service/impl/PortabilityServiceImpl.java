package ix.portal.npg.service.impl;

import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.repository.PortabilityRepository;
import ix.portal.npg.service.PortabilityService;
import ix.portal.npg.service.dto.PortabilityDTO;
import ix.portal.npg.service.mapper.PortabilityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PortabilityEntity}.
 */
@Service
@Transactional
public class PortabilityServiceImpl implements PortabilityService {

    private final Logger log = LoggerFactory.getLogger(PortabilityServiceImpl.class);

    private final PortabilityRepository portabilityRepository;

    private final PortabilityMapper portabilityMapper;

    public PortabilityServiceImpl(PortabilityRepository portabilityRepository, PortabilityMapper portabilityMapper) {
        this.portabilityRepository = portabilityRepository;
        this.portabilityMapper = portabilityMapper;
    }

    @Override
    public PortabilityDTO save(PortabilityDTO portabilityDTO) {
        log.debug("Request to save Portability : {}", portabilityDTO);
        PortabilityEntity portabilityEntity = portabilityMapper.toEntity(portabilityDTO);
        portabilityEntity = portabilityRepository.save(portabilityEntity);
        return portabilityMapper.toDto(portabilityEntity);
    }

    @Override
    public Optional<PortabilityDTO> partialUpdate(PortabilityDTO portabilityDTO) {
        log.debug("Request to partially update Portability : {}", portabilityDTO);

        return portabilityRepository
            .findById(portabilityDTO.getId())
            .map(
                existingPortability -> {
                    portabilityMapper.partialUpdate(existingPortability, portabilityDTO);

                    return existingPortability;
                }
            )
            .map(portabilityRepository::save)
            .map(portabilityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PortabilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Portabilities");
        return portabilityRepository.findAll(pageable).map(portabilityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PortabilityDTO> findOne(Long id) {
        log.debug("Request to get Portability : {}", id);
        return portabilityRepository.findById(id).map(portabilityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Portability : {}", id);
        portabilityRepository.deleteById(id);
    }
}


