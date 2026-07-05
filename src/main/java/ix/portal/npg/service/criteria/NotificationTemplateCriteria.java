package ix.portal.npg.service.criteria;

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
 * Criteria class for the {@link ix.portal.npg.domain.NotificationTemplateEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.NotificationTemplateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notification-templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NotificationTemplateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter templateCode;

    private StringFilter language;

    private StringFilter content;

    private StringFilter type;

    public NotificationTemplateCriteria() {}

    public NotificationTemplateCriteria(NotificationTemplateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.templateCode = other.templateCode == null ? null : other.templateCode.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.type = other.type == null ? null : other.type.copy();
    }

    @Override
    public NotificationTemplateCriteria copy() {
        return new NotificationTemplateCriteria(this);
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

    public StringFilter getTemplateCode() {
        return templateCode;
    }

    public StringFilter templateCode() {
        if (templateCode == null) {
            templateCode = new StringFilter();
        }
        return templateCode;
    }

    public void setTemplateCode(StringFilter templateCode) {
        this.templateCode = templateCode;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public StringFilter language() {
        if (language == null) {
            language = new StringFilter();
        }
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public StringFilter getContent() {
        return content;
    }

    public StringFilter content() {
        if (content == null) {
            content = new StringFilter();
        }
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificationTemplateCriteria that = (NotificationTemplateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(templateCode, that.templateCode) &&
            Objects.equals(language, that.language) &&
            Objects.equals(content, that.content) &&
            Objects.equals(type, that.type)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateCode, language, content, type);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (templateCode != null ? "templateCode=" + templateCode + ", " : "") +
            (language != null ? "language=" + language + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            "}";
    }
}


