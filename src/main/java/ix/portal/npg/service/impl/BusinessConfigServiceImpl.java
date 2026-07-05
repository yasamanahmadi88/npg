package ix.portal.npg.service.impl;

import ix.portal.npg.domain.BusinessConfigEntity;
import ix.portal.npg.ix.IxClient;
import ix.portal.npg.repository.BusinessConfigRepository;
import ix.portal.npg.service.BusinessConfigService;
import ix.portal.npg.service.dto.BusinessConfigDTO;
import ix.portal.npg.service.mapper.BusinessConfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessConfigEntity}.
 */
@Service
@Transactional
public class BusinessConfigServiceImpl implements BusinessConfigService {

    private final Logger log = LoggerFactory.getLogger(BusinessConfigServiceImpl.class);

    private final BusinessConfigRepository businessConfigRepository;
    private final IxClient ixClient;
    private final BusinessConfigMapper businessConfigMapper;

    public BusinessConfigServiceImpl(
        BusinessConfigRepository businessConfigRepository,
        IxClient ixClient,
        BusinessConfigMapper businessConfigMapper
    ) {
        this.businessConfigRepository = businessConfigRepository;
        this.ixClient = ixClient;
        this.businessConfigMapper = businessConfigMapper;
    }

    @Override
    public BusinessConfigDTO save(BusinessConfigDTO businessConfigDTO) {
        log.debug("Request to save BusinessConfig : {}", businessConfigDTO);
        BusinessConfigEntity businessConfigEntity = businessConfigMapper.toEntity(businessConfigDTO);
        businessConfigEntity = businessConfigRepository.save(businessConfigEntity);
        try {
            ixClient.sendReloadCacheMessage("businessConfig");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessConfigMapper.toDto(businessConfigEntity);
    }

    @Override
    public Optional<BusinessConfigDTO> partialUpdate(BusinessConfigDTO businessConfigDTO) {
        log.debug("Request to partially update BusinessConfig : {}", businessConfigDTO);

        Optional<BusinessConfigDTO> temp = businessConfigRepository
            .findById(businessConfigDTO.getId())
            .map(
                existingBusinessConfig -> {
                    businessConfigMapper.partialUpdate(existingBusinessConfig, businessConfigDTO);
                    return existingBusinessConfig;
                }
            )
            .map(businessConfigRepository::save)
            .map(businessConfigMapper::toDto);
        try {
            ixClient.sendReloadCacheMessage("businessConfig");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessConfigs");
        return businessConfigRepository.findAll(pageable).map(businessConfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessConfigDTO> findOne(String id) {
        log.debug("Request to get BusinessConfig : {}", id);
        return businessConfigRepository.findById(id).map(businessConfigMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete BusinessConfig : {}", id);
        businessConfigRepository.deleteById(id);
    }
}


