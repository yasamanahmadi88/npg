package ix.portal.npg.repository;

import ix.portal.npg.domain.SettingEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SettingEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingRepository extends JpaRepository<SettingEntity, Long>, JpaSpecificationExecutor<SettingEntity> {
    Optional<SettingEntity> findByKey(String key);
}


