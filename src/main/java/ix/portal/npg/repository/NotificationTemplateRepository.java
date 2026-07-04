package ix.portal.npg.repository;

import ix.portal.npg.domain.NotificationTemplateEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NotificationTemplateEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTemplateRepository
    extends JpaRepository<NotificationTemplateEntity, Long>, JpaSpecificationExecutor<NotificationTemplateEntity> {}


