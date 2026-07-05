package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.CrmToCrdbResponseMapEntity} entity.
 */
public class CrmToCrdbResponseMapDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 200)
    private String description;

    @NotNull
    @Size(max = 200)
    private String crmInterface;

    @NotNull
    @Size(max = 20)
    private String rspCode;

    @NotNull
    @Size(max = 200)
    private String rspNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCrmInterface() {
        return crmInterface;
    }

    public void setCrmInterface(String crmInterface) {
        this.crmInterface = crmInterface;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspNote() {
        return rspNote;
    }

    public void setRspNote(String rspNote) {
        this.rspNote = rspNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrmToCrdbResponseMapDTO)) {
            return false;
        }

        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = (CrmToCrdbResponseMapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, crmToCrdbResponseMapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrmToCrdbResponseMapDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", crmInterface='" + getCrmInterface() + "'" +
            ", rspCode='" + getRspCode() + "'" +
            ", rspNote='" + getRspNote() + "'" +
            "}";
    }
}


