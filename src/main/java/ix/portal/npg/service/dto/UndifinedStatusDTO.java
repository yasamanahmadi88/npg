package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.UndifinedStatusEntity} entity.
 */
public class UndifinedStatusDTO implements Serializable {

    private Long id;

    @Size(max = 4000)
    private String porStatus;

    @Size(max = 4000)
    private String porTechStatus;

    @Size(max = 4000)
    private String porErrCode;

    @Size(max = 4000)
    private String porRspCode;

    @Size(max = 4000)
    private String porRequestId;

    @NotNull
    private LocalDateTime insertDate;

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

    public String getPorRequestId() {
        return porRequestId;
    }

    public void setPorRequestId(String porRequestId) {
        this.porRequestId = porRequestId;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UndifinedStatusDTO)) {
            return false;
        }

        UndifinedStatusDTO undifinedStatusDTO = (UndifinedStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, undifinedStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UndifinedStatusDTO{" +
            "id=" + getId() +
            ", porStatus='" + getPorStatus() + "'" +
            ", porTechStatus='" + getPorTechStatus() + "'" +
            ", porErrCode='" + getPorErrCode() + "'" +
            ", porRspCode='" + getPorRspCode() + "'" +
            ", porRequestId='" + getPorRequestId() + "'" +
            ", insertDate='" + getInsertDate() + "'" +
            "}";
    }
}


