package ix.portal.npg.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckStatusEntity.
 */
@Entity
@Table(name = "TBL_CHECK_STATUS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CheckStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_CHK_STATS_ID_GENERATOR", sequenceName = "SEQ_CHK_STATS_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_CHK_STATS_ID_GENERATOR")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "por_status", length = 200, nullable = false)
    private String porStatus;

    @Size(max = 200)
    @Column(name = "por_tech_status", length = 200)
    private String porTechStatus;

    @Size(max = 100)
    @Column(name = "por_err_code", length = 100)
    private String porErrCode;

    @Size(max = 100)
    @Column(name = "por_rsp_code", length = 100)
    private String porRspCode;

    @NotNull
    @Size(max = 4000)
    @Column(name = "status_message_fa", length = 4000, nullable = false)
    private String statusMessageFa;

    @Size(max = 4000)
    @Column(name = "status_message_en", length = 4000)
    private String statusMessageEn;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckStatusEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getPorStatus() {
        return this.porStatus;
    }

    public CheckStatusEntity porStatus(String porStatus) {
        this.porStatus = porStatus;
        return this;
    }

    public void setPorStatus(String porStatus) {
        this.porStatus = porStatus;
    }

    public String getPorTechStatus() {
        return this.porTechStatus;
    }

    public CheckStatusEntity porTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
        return this;
    }

    public void setPorTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
    }

    public String getPorErrCode() {
        return this.porErrCode;
    }

    public CheckStatusEntity porErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
        return this;
    }

    public void setPorErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
    }

    public String getPorRspCode() {
        return this.porRspCode;
    }

    public CheckStatusEntity porRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
        return this;
    }

    public void setPorRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
    }

    public String getStatusMessageFa() {
        return this.statusMessageFa;
    }

    public CheckStatusEntity statusMessageFa(String statusMessageFa) {
        this.statusMessageFa = statusMessageFa;
        return this;
    }

    public void setStatusMessageFa(String statusMessageFa) {
        this.statusMessageFa = statusMessageFa;
    }

    public String getStatusMessageEn() {
        return this.statusMessageEn;
    }

    public CheckStatusEntity statusMessageEn(String statusMessageEn) {
        this.statusMessageEn = statusMessageEn;
        return this;
    }

    public void setStatusMessageEn(String statusMessageEn) {
        this.statusMessageEn = statusMessageEn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckStatusEntity)) {
            return false;
        }
        return id != null && id.equals(((CheckStatusEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckStatusEntity{" +
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


