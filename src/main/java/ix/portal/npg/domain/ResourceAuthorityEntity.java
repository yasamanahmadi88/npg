package ix.portal.npg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ix.portal.npg.domain.enumeration.Verb;
import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResourceAuthorityEntity.
 */
@Entity
@Table(name = "JHI_RESOURCE_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceAuthorityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RES_AUTH_SEQ_GENERATOR", sequenceName = "RES_AUTH_SEQ", allocationSize = 0)
    @GeneratedValue(generator = "RES_AUTH_SEQ_GENERATOR")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "verb", nullable = false)
    private Verb verb;

    @NotNull
    @Column(name = "authority_id", nullable = false)
    private Long authorityId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "resourceAuthorities" }, allowSetters = true)
    private ResourceEntity resource;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResourceAuthorityEntity id(Long id) {
        this.id = id;
        return this;
    }

    public Verb getVerb() {
        return this.verb;
    }

    public ResourceAuthorityEntity verb(Verb verb) {
        this.verb = verb;
        return this;
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
    }

    public Long getAuthorityId() {
        return this.authorityId;
    }

    public ResourceAuthorityEntity authorityId(Long authorityId) {
        this.authorityId = authorityId;
        return this;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public ResourceEntity getResource() {
        return this.resource;
    }

    public ResourceAuthorityEntity resource(ResourceEntity resource) {
        this.setResource(resource);
        return this;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceAuthorityEntity)) {
            return false;
        }
        return id != null && id.equals(((ResourceAuthorityEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceAuthorityEntity{" +
            "id=" + getId() +
            ", verb='" + getVerb() + "'" +
            ", authorityId=" + getAuthorityId() +
            "}";
    }
}


