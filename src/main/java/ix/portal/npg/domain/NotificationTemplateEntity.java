package ix.portal.npg.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NotificationTemplateEntity.
 */
@Entity
@Table(name = "TBL_NOTIFICATION_TEMPLATE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotificationTemplateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_NOTIFICATION_TEMPLATE_ID_GENERATOR", sequenceName = "SEQ_NOTIFICATION_TEMPLATE_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_NOTIFICATION_TEMPLATE_ID_GENERATOR")
    private Long id;

    @Size(max = 100)
    @Column(name = "template_code", length = 100)
    private String templateCode;

    @Size(max = 6)
    @Column(name = "language", length = 6)
    private String language;

    @Size(max = 4000)
    @Column(name = "content", length = 4000)
    private String content;

    @Size(max = 12)
    @Column(name = "type", length = 12)
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationTemplateEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public NotificationTemplateEntity templateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getLanguage() {
        return this.language;
    }

    public NotificationTemplateEntity language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return this.content;
    }

    public NotificationTemplateEntity content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return this.type;
    }

    public NotificationTemplateEntity type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationTemplateEntity)) {
            return false;
        }
        return id != null && id.equals(((NotificationTemplateEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplateEntity{" +
            "id=" + getId() +
            ", templateCode='" + getTemplateCode() + "'" +
            ", language='" + getLanguage() + "'" +
            ", content='" + getContent() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}


