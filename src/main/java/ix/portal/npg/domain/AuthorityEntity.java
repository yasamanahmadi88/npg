package ix.portal.npg.domain;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "AUTR_SEQ_GENERATOR", sequenceName = "AUTR_SEQ", allocationSize = 0)
    @GeneratedValue(generator = "AUTR_SEQ_GENERATOR")
    private Long id;

    @NotNull
    @Column(length = 50)
    private String name;

    @Column(name = "display_Name", length = 500)
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "ID")
    private AuthorityEntity parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorityEntity name(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public AuthorityEntity displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public AuthorityEntity getParent() {
        return parent;
    }

    public void setParent(AuthorityEntity parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorityEntity)) {
            return false;
        }
        return Objects.equals(name, ((AuthorityEntity) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Authority{" +
            "name='" + name + '\'' +
            "}";
    }
}


