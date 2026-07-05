package ix.portal.npg.service.impl;

import ix.portal.npg.domain.NotificationTemplateEntity;
import ix.portal.npg.repository.NotificationTemplateRepository;
import ix.portal.npg.service.NotificationTemplateService;
import ix.portal.npg.service.dto.NotificationTemplateDTO;
import ix.portal.npg.service.mapper.NotificationTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationTemplateEntity}.
 */
@Service
@Transactional
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplateServiceImpl.class);

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final NotificationTemplateMapper notificationTemplateMapper;

    public NotificationTemplateServiceImpl(
        NotificationTemplateRepository notificationTemplateRepository,
        NotificationTemplateMapper notificationTemplateMapper
    ) {
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.notificationTemplateMapper = notificationTemplateMapper;
    }

    @Override
    public NotificationTemplateDTO save(NotificationTemplateDTO notificationTemplateDTO) {
        log.debug("Request to save NotificationTemplate : {}", notificationTemplateDTO);
        NotificationTemplateEntity notificationTemplateEntity = notificationTemplateMapper.toEntity(notificationTemplateDTO);
        notificationTemplateEntity = notificationTemplateRepository.save(notificationTemplateEntity);
        return notificationTemplateMapper.toDto(notificationTemplateEntity);
    }

    @Override
    public Optional<NotificationTemplateDTO> partialUpdate(NotificationTemplateDTO notificationTemplateDTO) {
        log.debug("Request to partially update NotificationTemplate : {}", notificationTemplateDTO);

        return notificationTemplateRepository
            .findById(notificationTemplateDTO.getId())
            .map(
                existingNotificationTemplate -> {
                    notificationTemplateMapper.partialUpdate(existingNotificationTemplate, notificationTemplateDTO);

                    return existingNotificationTemplate;
                }
            )
            .map(notificationTemplateRepository::save)
            .map(notificationTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationTemplates");
        return notificationTemplateRepository.findAll(pageable).map(notificationTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationTemplateDTO> findOne(Long id) {
        log.debug("Request to get NotificationTemplate : {}", id);
        return notificationTemplateRepository.findById(id).map(notificationTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotificationTemplate : {}", id);
        notificationTemplateRepository.deleteById(id);
    }
}


