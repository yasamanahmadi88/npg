package ix.portal.npg.repository;

import ix.portal.npg.domain.AuthorityEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA repository for the {@link AuthorityEntity} entity.
 */
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long>, JpaSpecificationExecutor<AuthorityEntity> {
    Optional<AuthorityEntity> findByName(String string);

    List<AuthorityEntity> findByNameIn(List<String> string);

    Page<AuthorityEntity> findByIdOrDisplayNameContainingIgnoreCaseOrNameContainingIgnoreCaseOrParent_NameContainingIgnoreCaseOrParent_DisplayNameContainingIgnoreCase(
        Long id,
        String p1,
        String p2,
        String p3,
        String p4,
        Pageable pageable
    );
}


