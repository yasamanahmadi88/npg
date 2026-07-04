package ix.portal.npg.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PortabilityEntity.
 */
@Entity
@Table(name = "TBL_PORTABILITY_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortabilityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POR_ID")
    @SequenceGenerator(name = "SEQ_NPG_PORT_ID_GENERATOR", sequenceName = "SEQ_NPG_PORT_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_NPG_PORT_ID_GENERATOR")
    private Long id;

    @Size(max = 32)
    @Column(name = "por_request_id", length = 32)
    private String porRequestId;

    @Size(max = 16)
    @Column(name = "por_number", length = 16)
    private String porNumber;

    @Column(name = "por_legal_term")
    private Integer porLegalTerm;

    @Size(max = 50)
    @Column(name = "por_opr", length = 50)
    private String porOpr;

    @Size(max = 16)
    @Column(name = "por_acc_type", length = 16)
    private String porAccType;

    @Size(max = 128)
    @Column(name = "por_id_number", length = 128)
    private String porIdNumber;

    @Size(max = 16)
    @Column(name = "por_contact_number", length = 16)
    private String porContactNumber;

    @Size(max = 50)
    @Column(name = "por_status", length = 50)
    private String porStatus;

    @Column(name = "por_ported_date")
    private LocalDateTime porPortedDate;

    @Size(max = 50)
    @Column(name = "por_routing", length = 50)
    private String porRouting;

    @Size(max = 32)
    @Column(name = "por_type", length = 32)
    private String porType;

    @Size(max = 50)
    @Column(name = "por_op_org", length = 50)
    private String porOpOrg;

    @Size(max = 20)
    @Column(name = "por_rsp_code", length = 20)
    private String porRspCode;

    @Size(max = 256)
    @Column(name = "por_rsp_note", length = 256)
    private String porRspNote;

    @Size(max = 256)
    @Column(name = "por_cancel_note", length = 256)
    private String porCancelNote;

    @Size(max = 32)
    @Column(name = "por_mnpid", length = 32)
    private String porMnpid;

    @Column(name = "portation_date")
    private LocalDateTime portationDate;

    @Size(max = 32)
    @Column(name = "porta_code", length = 32)
    private String portaCode;

    @Size(max = 50)
    @Column(name = "mvno", length = 50)
    private String mvno;

    @Size(max = 100)
    @Column(name = "context", length = 100)
    private String context;

    @Size(max = 20)
    @Column(name = "por_err_code", length = 20)
    private String porErrCode;

    @Size(max = 256)
    @Column(name = "por_err_message", length = 256)
    private String porErrMessage;

    @Size(max = 50)
    @Column(name = "por_opd", length = 50)
    private String porOpd;

    @Size(max = 20)
    @Column(name = "por_num_type", length = 20)
    private String porNumType;

    @Size(max = 200)
    @Column(name = "por_note", length = 200)
    private String porNote;

    @Column(name = "por_deadline")
    private LocalDateTime porDeadline;

    @Column(name = "por_response_timestamp")
    private Long porResponseTimestamp;

    @Column(name = "por_eligible")
    private Integer porEligible;

    @Column(name = "por_billing_ok")
    private Integer porBillingOk;

    @Size(max = 100)
    @Column(name = "intermediary_action_state", length = 100)
    private String intermediaryActionState;

    @Column(name = "por_cr_date")
    private LocalDateTime porCrDate;

    @Column(name = "por_upd_date")
    private LocalDateTime porUpdDate;

    @Size(max = 100)
    @Column(name = "por_tech_status", length = 100)
    private String porTechStatus;

    @Column(name = "por_tech_deadline")
    private LocalDateTime porTechDeadline;

    @Column(name = "need_manual_retry")
    private Integer needManualRetry;

    @Column(name = "ref_por_id")
    private Long refPorId;

    @NotNull
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PortabilityEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getPorRequestId() {
        return this.porRequestId;
    }

    public PortabilityEntity porRequestId(String porRequestId) {
        this.porRequestId = porRequestId;
        return this;
    }

    public void setPorRequestId(String porRequestId) {
        this.porRequestId = porRequestId;
    }

    public String getPorNumber() {
        return this.porNumber;
    }

    public PortabilityEntity porNumber(String porNumber) {
        this.porNumber = porNumber;
        return this;
    }

    public void setPorNumber(String porNumber) {
        this.porNumber = porNumber;
    }

    public Integer getPorLegalTerm() {
        return this.porLegalTerm;
    }

    public PortabilityEntity porLegalTerm(Integer porLegalTerm) {
        this.porLegalTerm = porLegalTerm;
        return this;
    }

    public void setPorLegalTerm(Integer porLegalTerm) {
        this.porLegalTerm = porLegalTerm;
    }

    public String getPorOpr() {
        return this.porOpr;
    }

    public PortabilityEntity porOpr(String porOpr) {
        this.porOpr = porOpr;
        return this;
    }

    public void setPorOpr(String porOpr) {
        this.porOpr = porOpr;
    }

    public String getPorAccType() {
        return this.porAccType;
    }

    public PortabilityEntity porAccType(String porAccType) {
        this.porAccType = porAccType;
        return this;
    }

    public void setPorAccType(String porAccType) {
        this.porAccType = porAccType;
    }

    public String getPorIdNumber() {
        return this.porIdNumber;
    }

    public PortabilityEntity porIdNumber(String porIdNumber) {
        this.porIdNumber = porIdNumber;
        return this;
    }

    public void setPorIdNumber(String porIdNumber) {
        this.porIdNumber = porIdNumber;
    }

    public String getPorContactNumber() {
        return this.porContactNumber;
    }

    public PortabilityEntity porContactNumber(String porContactNumber) {
        this.porContactNumber = porContactNumber;
        return this;
    }

    public void setPorContactNumber(String porContactNumber) {
        this.porContactNumber = porContactNumber;
    }

    public String getPorStatus() {
        return this.porStatus;
    }

    public PortabilityEntity porStatus(String porStatus) {
        this.porStatus = porStatus;
        return this;
    }

    public void setPorStatus(String porStatus) {
        this.porStatus = porStatus;
    }

    public LocalDateTime getPorPortedDate() {
        return this.porPortedDate;
    }

    public PortabilityEntity porPortedDate(LocalDateTime porPortedDate) {
        this.porPortedDate = porPortedDate;
        return this;
    }

    public void setPorPortedDate(LocalDateTime porPortedDate) {
        this.porPortedDate = porPortedDate;
    }

    public String getPorRouting() {
        return this.porRouting;
    }

    public PortabilityEntity porRouting(String porRouting) {
        this.porRouting = porRouting;
        return this;
    }

    public void setPorRouting(String porRouting) {
        this.porRouting = porRouting;
    }

    public String getPorType() {
        return this.porType;
    }

    public PortabilityEntity porType(String porType) {
        this.porType = porType;
        return this;
    }

    public void setPorType(String porType) {
        this.porType = porType;
    }

    public String getPorOpOrg() {
        return this.porOpOrg;
    }

    public PortabilityEntity porOpOrg(String porOpOrg) {
        this.porOpOrg = porOpOrg;
        return this;
    }

    public void setPorOpOrg(String porOpOrg) {
        this.porOpOrg = porOpOrg;
    }

    public String getPorRspCode() {
        return this.porRspCode;
    }

    public PortabilityEntity porRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
        return this;
    }

    public void setPorRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
    }

    public String getPorRspNote() {
        return this.porRspNote;
    }

    public PortabilityEntity porRspNote(String porRspNote) {
        this.porRspNote = porRspNote;
        return this;
    }

    public void setPorRspNote(String porRspNote) {
        this.porRspNote = porRspNote;
    }

    public String getPorCancelNote() {
        return this.porCancelNote;
    }

    public PortabilityEntity porCancelNote(String porCancelNote) {
        this.porCancelNote = porCancelNote;
        return this;
    }

    public void setPorCancelNote(String porCancelNote) {
        this.porCancelNote = porCancelNote;
    }

    public String getPorMnpid() {
        return this.porMnpid;
    }

    public PortabilityEntity porMnpid(String porMnpid) {
        this.porMnpid = porMnpid;
        return this;
    }

    public void setPorMnpid(String porMnpid) {
        this.porMnpid = porMnpid;
    }

    public LocalDateTime getPortationDate() {
        return this.portationDate;
    }

    public PortabilityEntity portationDate(LocalDateTime portationDate) {
        this.portationDate = portationDate;
        return this;
    }

    public void setPortationDate(LocalDateTime portationDate) {
        this.portationDate = portationDate;
    }

    public String getPortaCode() {
        return this.portaCode;
    }

    public PortabilityEntity portaCode(String portaCode) {
        this.portaCode = portaCode;
        return this;
    }

    public void setPortaCode(String portaCode) {
        this.portaCode = portaCode;
    }

    public String getMvno() {
        return this.mvno;
    }

    public PortabilityEntity mvno(String mvno) {
        this.mvno = mvno;
        return this;
    }

    public void setMvno(String mvno) {
        this.mvno = mvno;
    }

    public String getContext() {
        return this.context;
    }

    public PortabilityEntity context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPorErrCode() {
        return this.porErrCode;
    }

    public PortabilityEntity porErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
        return this;
    }

    public void setPorErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
    }

    public String getPorErrMessage() {
        return this.porErrMessage;
    }

    public PortabilityEntity porErrMessage(String porErrMessage) {
        this.porErrMessage = porErrMessage;
        return this;
    }

    public void setPorErrMessage(String porErrMessage) {
        this.porErrMessage = porErrMessage;
    }

    public String getPorOpd() {
        return this.porOpd;
    }

    public PortabilityEntity porOpd(String porOpd) {
        this.porOpd = porOpd;
        return this;
    }

    public void setPorOpd(String porOpd) {
        this.porOpd = porOpd;
    }

    public String getPorNumType() {
        return this.porNumType;
    }

    public PortabilityEntity porNumType(String porNumType) {
        this.porNumType = porNumType;
        return this;
    }

    public void setPorNumType(String porNumType) {
        this.porNumType = porNumType;
    }

    public String getPorNote() {
        return this.porNote;
    }

    public PortabilityEntity porNote(String porNote) {
        this.porNote = porNote;
        return this;
    }

    public void setPorNote(String porNote) {
        this.porNote = porNote;
    }

    public LocalDateTime getPorDeadline() {
        return this.porDeadline;
    }

    public PortabilityEntity porDeadline(LocalDateTime porDeadline) {
        this.porDeadline = porDeadline;
        return this;
    }

    public void setPorDeadline(LocalDateTime porDeadline) {
        this.porDeadline = porDeadline;
    }

    public Long getPorResponseTimestamp() {
        return this.porResponseTimestamp;
    }

    public PortabilityEntity porResponseTimestamp(Long porResponseTimestamp) {
        this.porResponseTimestamp = porResponseTimestamp;
        return this;
    }

    public void setPorResponseTimestamp(Long porResponseTimestamp) {
        this.porResponseTimestamp = porResponseTimestamp;
    }

    public Integer getPorEligible() {
        return this.porEligible;
    }

    public PortabilityEntity porEligible(Integer porEligible) {
        this.porEligible = porEligible;
        return this;
    }

    public void setPorEligible(Integer porEligible) {
        this.porEligible = porEligible;
    }

    public Integer getPorBillingOk() {
        return this.porBillingOk;
    }

    public PortabilityEntity porBillingOk(Integer porBillingOk) {
        this.porBillingOk = porBillingOk;
        return this;
    }

    public void setPorBillingOk(Integer porBillingOk) {
        this.porBillingOk = porBillingOk;
    }

    public String getIntermediaryActionState() {
        return this.intermediaryActionState;
    }

    public PortabilityEntity intermediaryActionState(String intermediaryActionState) {
        this.intermediaryActionState = intermediaryActionState;
        return this;
    }

    public void setIntermediaryActionState(String intermediaryActionState) {
        this.intermediaryActionState = intermediaryActionState;
    }

    public LocalDateTime getPorCrDate() {
        return this.porCrDate;
    }

    public PortabilityEntity porCrDate(LocalDateTime porCrDate) {
        this.porCrDate = porCrDate;
        return this;
    }

    public void setPorCrDate(LocalDateTime porCrDate) {
        this.porCrDate = porCrDate;
    }

    public LocalDateTime getPorUpdDate() {
        return this.porUpdDate;
    }

    public PortabilityEntity porUpdDate(LocalDateTime porUpdDate) {
        this.porUpdDate = porUpdDate;
        return this;
    }

    public void setPorUpdDate(LocalDateTime porUpdDate) {
        this.porUpdDate = porUpdDate;
    }

    public String getPorTechStatus() {
        return this.porTechStatus;
    }

    public PortabilityEntity porTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
        return this;
    }

    public void setPorTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
    }

    public LocalDateTime getPorTechDeadline() {
        return this.porTechDeadline;
    }

    public PortabilityEntity porTechDeadline(LocalDateTime porTechDeadline) {
        this.porTechDeadline = porTechDeadline;
        return this;
    }

    public void setPorTechDeadline(LocalDateTime porTechDeadline) {
        this.porTechDeadline = porTechDeadline;
    }

    public Integer getNeedManualRetry() {
        return this.needManualRetry;
    }

    public PortabilityEntity needManualRetry(Integer needManualRetry) {
        this.needManualRetry = needManualRetry;
        return this;
    }

    public void setNeedManualRetry(Integer needManualRetry) {
        this.needManualRetry = needManualRetry;
    }

    public Long getRefPorId() {
        return this.refPorId;
    }

    public PortabilityEntity refPorId(Long refPorId) {
        this.refPorId = refPorId;
        return this;
    }

    public void setRefPorId(Long refPorId) {
        this.refPorId = refPorId;
    }

    public Integer getRetryCount() {
        return this.retryCount;
    }

    public PortabilityEntity retryCount(Integer retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortabilityEntity)) {
            return false;
        }
        return id != null && id.equals(((PortabilityEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortabilityEntity{" +
            "id=" + getId() +
            ", porRequestId='" + getPorRequestId() + "'" +
            ", porNumber='" + getPorNumber() + "'" +
            ", porLegalTerm=" + getPorLegalTerm() +
            ", porOpr='" + getPorOpr() + "'" +
            ", porAccType='" + getPorAccType() + "'" +
            ", porIdNumber='" + getPorIdNumber() + "'" +
            ", porContactNumber='" + getPorContactNumber() + "'" +
            ", porStatus='" + getPorStatus() + "'" +
            ", porPortedDate='" + getPorPortedDate() + "'" +
            ", porRouting='" + getPorRouting() + "'" +
            ", porType='" + getPorType() + "'" +
            ", porOpOrg='" + getPorOpOrg() + "'" +
            ", porRspCode='" + getPorRspCode() + "'" +
            ", porRspNote='" + getPorRspNote() + "'" +
            ", porCancelNote='" + getPorCancelNote() + "'" +
            ", porMnpid='" + getPorMnpid() + "'" +
            ", portationDate='" + getPortationDate() + "'" +
            ", portaCode='" + getPortaCode() + "'" +
            ", mvno='" + getMvno() + "'" +
            ", context='" + getContext() + "'" +
            ", porErrCode='" + getPorErrCode() + "'" +
            ", porErrMessage='" + getPorErrMessage() + "'" +
            ", porOpd='" + getPorOpd() + "'" +
            ", porNumType='" + getPorNumType() + "'" +
            ", porNote='" + getPorNote() + "'" +
            ", porDeadline='" + getPorDeadline() + "'" +
            ", porResponseTimestamp=" + getPorResponseTimestamp() +
            ", porEligible=" + getPorEligible() +
            ", porBillingOk=" + getPorBillingOk() +
            ", intermediaryActionState='" + getIntermediaryActionState() + "'" +
            ", porCrDate='" + getPorCrDate() + "'" +
            ", porUpdDate='" + getPorUpdDate() + "'" +
            ", porTechStatus='" + getPorTechStatus() + "'" +
            ", porTechDeadline='" + getPorTechDeadline() + "'" +
            ", needManualRetry=" + getNeedManualRetry() +
            ", refPorId=" + getRefPorId() +
            ", retryCount=" + getRetryCount() +
            "}";
    }
}


