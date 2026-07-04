package ix.portal.npg.repository;

import ix.portal.npg.domain.BusinessConfigEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BusinessConfigEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessConfigRepository
    extends JpaRepository<BusinessConfigEntity, String>, JpaSpecificationExecutor<BusinessConfigEntity> {}


