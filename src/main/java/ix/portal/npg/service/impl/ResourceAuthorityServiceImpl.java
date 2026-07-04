package ix.portal.npg.service.impl;

import ix.portal.npg.domain.ResourceAuthorityEntity;
import ix.portal.npg.repository.ResourceAuthorityRepository;
import ix.portal.npg.service.AuthorityService;
import ix.portal.npg.service.ResourceAuthorityService;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import ix.portal.npg.service.mapper.ResourceAuthorityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResourceAuthorityEntity}.
 */
@Service
@Transactional
public class ResourceAuthorityServiceImpl implements ResourceAuthorityService {

    private final Logger log = LoggerFactory.getLogger(ResourceAuthorityServiceImpl.class);

    private final ResourceAuthorityRepository resourceAuthorityRepository;

    private final ResourceAuthorityMapper resourceAuthorityMapper;
    private final AuthorityService authorityService;

    public ResourceAuthorityServiceImpl(
        ResourceAuthorityRepository resourceAuthorityRepository,
        ResourceAuthorityMapper resourceAuthorityMapper,
        AuthorityService authorityService
    ) {
        this.resourceAuthorityRepository = resourceAuthorityRepository;
        this.resourceAuthorityMapper = resourceAuthorityMapper;
        this.authorityService = authorityService;
    }

    @Override
    public ResourceAuthorityDTO save(ResourceAuthorityDTO resourceAuthorityDTO) {
        log.debug("Request to save ResourceAuthority : {}", resourceAuthorityDTO);
        ResourceAuthorityEntity resourceAuthorityEntity = resourceAuthorityMapper.toEntity(resourceAuthorityDTO);
        resourceAuthorityEntity = resourceAuthorityRepository.save(resourceAuthorityEntity);
        return resourceAuthorityMapper.toDto(resourceAuthorityEntity);
    }

    @Override
    public Optional<ResourceAuthorityDTO> partialUpdate(ResourceAuthorityDTO resourceAuthorityDTO) {
        log.debug("Request to partially update ResourceAuthority : {}", resourceAuthorityDTO);

        return resourceAuthorityRepository
            .findById(resourceAuthorityDTO.getId())
            .map(
                existingResourceAuthority -> {
                    resourceAuthorityMapper.partialUpdate(existingResourceAuthority, resourceAuthorityDTO);
                    return existingResourceAuthority;
                }
            )
            .map(resourceAuthorityRepository::save)
            .map(resourceAuthorityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResourceAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceAuthorities");
        return resourceAuthorityRepository.findAll(pageable).map(resourceAuthorityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceAuthorityDTO> findOne(Long id) {
        log.debug("Request to get ResourceAuthority : {}", id);
        Optional<ResourceAuthorityDTO> temp = resourceAuthorityRepository.findById(id).map(resourceAuthorityMapper::toDto);
        temp.ifPresent(x -> authorityService.findOne(x.getAuthorityId()).ifPresent(x::setAuthority));
        return temp;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceAuthority : {}", id);
        resourceAuthorityRepository.deleteById(id);
    }
}


