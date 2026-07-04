package ix.portal.npg.service.criteria;

import ix.portal.npg.domain.enumeration.Verb;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ix.portal.npg.domain.ResourceAuthorityEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.ResourceAuthorityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resource-authorities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourceAuthorityCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Verb
     */
    public static class VerbFilter extends Filter<Verb> {

        public VerbFilter() {}

        public VerbFilter(VerbFilter filter) {
            super(filter);
        }

        @Override
        public VerbFilter copy() {
            return new VerbFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private VerbFilter verb;

    private LongFilter authorityId;

    private LongFilter resourceId;

    public ResourceAuthorityCriteria() {}

    public ResourceAuthorityCriteria(ResourceAuthorityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.verb = other.verb == null ? null : other.verb.copy();
        this.authorityId = other.authorityId == null ? null : other.authorityId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
    }

    @Override
    public ResourceAuthorityCriteria copy() {
        return new ResourceAuthorityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public VerbFilter getVerb() {
        return verb;
    }

    public VerbFilter verb() {
        if (verb == null) {
            verb = new VerbFilter();
        }
        return verb;
    }

    public void setVerb(VerbFilter verb) {
        this.verb = verb;
    }

    public LongFilter getAuthorityId() {
        return authorityId;
    }

    public LongFilter authorityId() {
        if (authorityId == null) {
            authorityId = new LongFilter();
        }
        return authorityId;
    }

    public void setAuthorityId(LongFilter authorityId) {
        this.authorityId = authorityId;
    }

    public LongFilter getResourceId() {
        return resourceId;
    }

    public LongFilter resourceId() {
        if (resourceId == null) {
            resourceId = new LongFilter();
        }
        return resourceId;
    }

    public void setResourceId(LongFilter resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResourceAuthorityCriteria that = (ResourceAuthorityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(verb, that.verb) &&
            Objects.equals(authorityId, that.authorityId) &&
            Objects.equals(resourceId, that.resourceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, verb, authorityId, resourceId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceAuthorityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (verb != null ? "verb=" + verb + ", " : "") +
            (authorityId != null ? "authorityId=" + authorityId + ", " : "") +
            (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            "}";
    }
}


