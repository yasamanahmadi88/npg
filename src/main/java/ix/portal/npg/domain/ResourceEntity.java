package ix.portal.npg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ix.portal.npg.domain.enumeration.ResourceType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResourceEntity.
 */
@Entity
@Table(name = "jhi_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RESRC_SEQ_GENERATOR", sequenceName = "RESRC_SEQ", allocationSize = 0)
    @GeneratedValue(generator = "RESRC_SEQ_GENERATOR")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NotNull
    @Size(max = 300)
    @Column(name = "display_name", length = 300, nullable = false)
    private String displayName;

    @NotNull
    @Size(max = 1000)
    @Column(name = "api_uri", length = 1000, nullable = false)
    private String apiUri;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resource" }, allowSetters = true)
    private Set<ResourceAuthorityEntity> resourceAuthorities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResourceEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ResourceEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ResourceEntity displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getApiUri() {
        return this.apiUri;
    }

    public ResourceEntity apiUri(String apiUri) {
        this.apiUri = apiUri;
        return this;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public ResourceEntity resourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Set<ResourceAuthorityEntity> getResourceAuthorities() {
        return this.resourceAuthorities;
    }

    public ResourceEntity resourceAuthorities(Set<ResourceAuthorityEntity> resourceAuthorities) {
        this.setResourceAuthorities(resourceAuthorities);
        return this;
    }

    public ResourceEntity addResourceAuthorities(ResourceAuthorityEntity resourceAuthority) {
        this.resourceAuthorities.add(resourceAuthority);
        resourceAuthority.setResource(this);
        return this;
    }

    public ResourceEntity removeResourceAuthorities(ResourceAuthorityEntity resourceAuthority) {
        this.resourceAuthorities.remove(resourceAuthority);
        resourceAuthority.setResource(null);
        return this;
    }

    public void setResourceAuthorities(Set<ResourceAuthorityEntity> resourceAuthorities) {
        if (this.resourceAuthorities != null) {
            this.resourceAuthorities.forEach(i -> i.setResource(null));
        }
        if (resourceAuthorities != null) {
            resourceAuthorities.forEach(i -> i.setResource(this));
        }
        this.resourceAuthorities = resourceAuthorities;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceEntity)) {
            return false;
        }
        return id != null && id.equals(((ResourceEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", apiUri='" + getApiUri() + "'" +
            ", resourceType='" + getResourceType() + "'" +
            "}";
    }
}


