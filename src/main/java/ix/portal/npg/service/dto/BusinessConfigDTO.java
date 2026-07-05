package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.BusinessConfigEntity} entity.
 */
public class BusinessConfigDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 512)
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessConfigDTO)) {
            return false;
        }

        BusinessConfigDTO businessConfigDTO = (BusinessConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, businessConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessConfigDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}


