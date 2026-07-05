package ix.portal.npg.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UndifinedStatusEntity.
 */
@Entity
@Table(name = "TBL_UNDEFINED_STATUS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UndifinedStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 4000)
    @Column(name = "por_status", length = 4000)
    private String porStatus;

    @Size(max = 4000)
    @Column(name = "por_tech_status", length = 4000)
    private String porTechStatus;

    @Size(max = 4000)
    @Column(name = "por_err_code", length = 4000)
    private String porErrCode;

    @Size(max = 4000)
    @Column(name = "por_rsp_code", length = 4000)
    private String porRspCode;

    @Size(max = 4000)
    @Column(name = "por_request_id", length = 4000)
    private String porRequestId;

    @NotNull
    @Column(name = "insert_date", nullable = false)
    private LocalDateTime insertDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UndifinedStatusEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getPorStatus() {
        return this.porStatus;
    }

    public UndifinedStatusEntity porStatus(String porStatus) {
        this.porStatus = porStatus;
        return this;
    }

    public void setPorStatus(String porStatus) {
        this.porStatus = porStatus;
    }

    public String getPorTechStatus() {
        return this.porTechStatus;
    }

    public UndifinedStatusEntity porTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
        return this;
    }

    public void setPorTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
    }

    public String getPorErrCode() {
        return this.porErrCode;
    }

    public UndifinedStatusEntity porErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
        return this;
    }

    public void setPorErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
    }

    public String getPorRspCode() {
        return this.porRspCode;
    }

    public UndifinedStatusEntity porRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
        return this;
    }

    public void setPorRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
    }

    public String getPorRequestId() {
        return this.porRequestId;
    }

    public UndifinedStatusEntity porRequestId(String porRequestId) {
        this.porRequestId = porRequestId;
        return this;
    }

    public void setPorRequestId(String porRequestId) {
        this.porRequestId = porRequestId;
    }

    public LocalDateTime getInsertDate() {
        return this.insertDate;
    }

    public UndifinedStatusEntity insertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UndifinedStatusEntity)) {
            return false;
        }
        return id != null && id.equals(((UndifinedStatusEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UndifinedStatusEntity{" +
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


