package ix.portal.npg.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CrmToCrdbResponseMapEntity.
 */
@Entity
@Table(name = "TBL_CRM_TO_CRDB_RESPONSE_MAP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CrmToCrdbResponseMapEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_CRM_TO_CRDB_RESPONSE_ID_GENERATOR", sequenceName = "SEQ_CRM_TO_CRDB_RESPONSE_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_CRM_TO_CRDB_RESPONSE_ID_GENERATOR")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 200)
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @NotNull
    @Size(max = 200)
    @Column(name = "crm_interface", length = 200, nullable = false)
    private String crmInterface;

    @NotNull
    @Size(max = 20)
    @Column(name = "rsp_code", length = 20, nullable = false)
    private String rspCode;

    @NotNull
    @Size(max = 200)
    @Column(name = "rsp_note", length = 200, nullable = false)
    private String rspNote;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmToCrdbResponseMapEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public CrmToCrdbResponseMapEntity code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public CrmToCrdbResponseMapEntity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCrmInterface() {
        return this.crmInterface;
    }

    public CrmToCrdbResponseMapEntity crmInterface(String crmInterface) {
        this.crmInterface = crmInterface;
        return this;
    }

    public void setCrmInterface(String crmInterface) {
        this.crmInterface = crmInterface;
    }

    public String getRspCode() {
        return this.rspCode;
    }

    public CrmToCrdbResponseMapEntity rspCode(String rspCode) {
        this.rspCode = rspCode;
        return this;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspNote() {
        return this.rspNote;
    }

    public CrmToCrdbResponseMapEntity rspNote(String rspNote) {
        this.rspNote = rspNote;
        return this;
    }

    public void setRspNote(String rspNote) {
        this.rspNote = rspNote;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrmToCrdbResponseMapEntity)) {
            return false;
        }
        return id != null && id.equals(((CrmToCrdbResponseMapEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrmToCrdbResponseMapEntity{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", crmInterface='" + getCrmInterface() + "'" +
            ", rspCode='" + getRspCode() + "'" +
            ", rspNote='" + getRspNote() + "'" +
            "}";
    }
}


