package ix.portal.npg.service.impl;

import ix.portal.npg.domain.AuthorityEntity;
import ix.portal.npg.repository.AuthorityRepository;
import ix.portal.npg.service.AuthorityService;
import ix.portal.npg.service.dto.AuthorityDTO;
import ix.portal.npg.service.mapper.AuthorityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuthorityEntity}.
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    private final Logger log = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public AuthorityDTO save(AuthorityDTO authorityDTO) {
        log.debug("Request to save Authority : {}", authorityDTO);
        AuthorityEntity authorityEntity = authorityMapper.toEntity(authorityDTO);
        authorityEntity = authorityRepository.save(authorityEntity);
        return authorityMapper.toDto(authorityEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Authorities");
        return authorityRepository.findAll(pageable).map(authorityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorityDTO> findOne(Long id) {
        log.debug("Request to get Authority : {}", id);
        return authorityRepository.findById(id).map(authorityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Authority : {}", id);
        authorityRepository.deleteById(id);
    }
}


