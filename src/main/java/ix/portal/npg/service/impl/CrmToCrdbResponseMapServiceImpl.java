package ix.portal.npg.service.impl;

import ix.portal.npg.domain.CrmToCrdbResponseMapEntity;
import ix.portal.npg.ix.IxClient;
import ix.portal.npg.repository.CrmToCrdbResponseMapRepository;
import ix.portal.npg.service.CrmToCrdbResponseMapService;
import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
import ix.portal.npg.service.mapper.CrmToCrdbResponseMapMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CrmToCrdbResponseMapEntity}.
 */
@Service
@Transactional
public class CrmToCrdbResponseMapServiceImpl implements CrmToCrdbResponseMapService {

    private final Logger log = LoggerFactory.getLogger(CrmToCrdbResponseMapServiceImpl.class);

    private final CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository;
    private final IxClient ixClient;
    private final CrmToCrdbResponseMapMapper crmToCrdbResponseMapMapper;

    public CrmToCrdbResponseMapServiceImpl(
        CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository,
        IxClient ixClient,
        CrmToCrdbResponseMapMapper crmToCrdbResponseMapMapper
    ) {
        this.crmToCrdbResponseMapRepository = crmToCrdbResponseMapRepository;
        this.ixClient = ixClient;
        this.crmToCrdbResponseMapMapper = crmToCrdbResponseMapMapper;
    }

    @Override
    public CrmToCrdbResponseMapDTO save(CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO) {
        log.debug("Request to save CrmToCrdbResponseMap : {}", crmToCrdbResponseMapDTO);
        CrmToCrdbResponseMapEntity crmToCrdbResponseMapEntity = crmToCrdbResponseMapMapper.toEntity(crmToCrdbResponseMapDTO);
        crmToCrdbResponseMapEntity = crmToCrdbResponseMapRepository.save(crmToCrdbResponseMapEntity);
        try {
            ixClient.sendReloadCacheMessage("responseMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);
    }

    @Override
    public Optional<CrmToCrdbResponseMapDTO> partialUpdate(CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO) {
        log.debug("Request to partially update CrmToCrdbResponseMap : {}", crmToCrdbResponseMapDTO);

        Optional<CrmToCrdbResponseMapDTO> temp = crmToCrdbResponseMapRepository
            .findById(crmToCrdbResponseMapDTO.getId())
            .map(
                existingCrmToCrdbResponseMap -> {
                    crmToCrdbResponseMapMapper.partialUpdate(existingCrmToCrdbResponseMap, crmToCrdbResponseMapDTO);

                    return existingCrmToCrdbResponseMap;
                }
            )
            .map(crmToCrdbResponseMapRepository::save)
            .map(crmToCrdbResponseMapMapper::toDto);
        try {
            ixClient.sendReloadCacheMessage("responseMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CrmToCrdbResponseMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CrmToCrdbResponseMaps");
        return crmToCrdbResponseMapRepository.findAll(pageable).map(crmToCrdbResponseMapMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CrmToCrdbResponseMapDTO> findOne(Long id) {
        log.debug("Request to get CrmToCrdbResponseMap : {}", id);
        return crmToCrdbResponseMapRepository.findById(id).map(crmToCrdbResponseMapMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CrmToCrdbResponseMap : {}", id);
        crmToCrdbResponseMapRepository.deleteById(id);
    }
}


