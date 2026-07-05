package ix.portal.npg.service.dto;

import ix.portal.npg.domain.enumeration.Verb;
import java.io.Serializable;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.ResourceAuthorityEntity} entity.
 */
public class ResourceAuthorityDTO implements Serializable {

    private Long id;

    @NotNull
    private Verb verb;

    @NotNull
    private Long authorityId;

    private Long resourceId;

    private String resourceDisplayName;
    private String resourceName;

    private ResourceDTO resource;
    private AuthorityDTO authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Verb getVerb() {
        return verb;
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceDisplayName() {
        return resourceDisplayName;
    }

    public void setResourceDisplayName(String resourceDisplayName) {
        this.resourceDisplayName = resourceDisplayName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public AuthorityDTO getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityDTO authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceAuthorityDTO)) {
            return false;
        }

        ResourceAuthorityDTO resourceAuthorityDTO = (ResourceAuthorityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resourceAuthorityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceAuthorityDTO{" +
            "id=" + getId() +
            ", verb='" + getVerb() + "'" +
            ", authorityId=" + getAuthorityId() +
            ", resource=" + getResource() +
            ", resourceId=" + getResourceId() +
            ", resourceDisplayName='" + getResourceDisplayName() + "'" +
            "}";
    }
}


