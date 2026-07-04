package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.NotificationTemplateEntity} entity.
 */
public class NotificationTemplateDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String templateCode;

    @Size(max = 6)
    private String language;

    @Size(max = 1000)
    private String content;

    @Size(max = 12)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationTemplateDTO)) {
            return false;
        }

        NotificationTemplateDTO notificationTemplateDTO = (NotificationTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplateDTO{" +
            "id=" + getId() +
            ", templateCode='" + getTemplateCode() + "'" +
            ", language='" + getLanguage() + "'" +
            ", content='" + getContent() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}


