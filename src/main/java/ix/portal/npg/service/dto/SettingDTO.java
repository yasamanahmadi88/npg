package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.SettingEntity} entity.
 */
public class SettingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 70)
    private String key;

    @NotNull
    @Size(max = 200)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        if (!(o instanceof SettingDTO)) {
            return false;
        }

        SettingDTO settingDTO = (SettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, settingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SettingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}


