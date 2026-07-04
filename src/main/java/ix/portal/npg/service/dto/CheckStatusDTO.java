package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.CheckStatusEntity} entity.
 */
public class CheckStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String porStatus;

    @Size(max = 200)
    private String porTechStatus;

    @Size(max = 100)
    private String porErrCode;

    @Size(max = 100)
    private String porRspCode;

    @NotNull
    @Size(max = 4000)
    private String statusMessageFa;

    @Size(max = 4000)
    private String statusMessageEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPorStatus() {
        return porStatus;
    }

    public void setPorStatus(String porStatus) {
        this.porStatus = porStatus;
    }

    public String getPorTechStatus() {
        return porTechStatus;
    }

    public void setPorTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
    }

    public String getPorErrCode() {
        return porErrCode;
    }

    public void setPorErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
    }

    public String getPorRspCode() {
        return porRspCode;
    }

    public void setPorRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
    }

    public String getStatusMessageFa() {
        return statusMessageFa;
    }

    public void setStatusMessageFa(String statusMessageFa) {
        this.statusMessageFa = statusMessageFa;
    }

    public String getStatusMessageEn() {
        return statusMessageEn;
    }

    public void setStatusMessageEn(String statusMessageEn) {
        this.statusMessageEn = statusMessageEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckStatusDTO)) {
            return false;
        }

        CheckStatusDTO checkStatusDTO = (CheckStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckStatusDTO{" +
            "id=" + getId() +
            ", porStatus='" + getPorStatus() + "'" +
            ", porTechStatus='" + getPorTechStatus() + "'" +
            ", porErrCode='" + getPorErrCode() + "'" +
            ", porRspCode='" + getPorRspCode() + "'" +
            ", statusMessageFa='" + getStatusMessageFa() + "'" +
            ", statusMessageEn='" + getStatusMessageEn() + "'" +
            "}";
    }
}


