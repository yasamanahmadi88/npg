package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.PortabilityEntity} entity.
 */
public class PortabilityDTO implements Serializable {

    private Long id;

    @Size(max = 32)
    private String porRequestId;

    @Size(max = 16)
    private String porNumber;

    private Integer porLegalTerm;

    @Size(max = 50)
    private String porOpr;

    @Size(max = 16)
    private String porAccType;

    @Size(max = 128)
    private String porIdNumber;

    @Size(max = 16)
    private String porContactNumber;

    @Size(max = 50)
    private String porStatus;

    private LocalDateTime porPortedDate;

    @Size(max = 50)
    private String porRouting;

    @Size(max = 32)
    private String porType;

    @Size(max = 50)
    private String porOpOrg;

    @Size(max = 20)
    private String porRspCode;

    @Size(max = 256)
    private String porRspNote;

    @Size(max = 256)
    private String porCancelNote;

    @Size(max = 32)
    private String porMnpid;

    private LocalDateTime portationDate;

    @Size(max = 32)
    private String portaCode;

    @Size(max = 50)
    private String mvno;

    @Size(max = 100)
    private String context;

    @Size(max = 20)
    private String porErrCode;

    @Size(max = 256)
    private String porErrMessage;

    @Size(max = 50)
    private String porOpd;

    @Size(max = 20)
    private String porNumType;

    @Size(max = 200)
    private String porNote;

    private LocalDateTime porDeadline;

    private Long porResponseTimestamp;

    private Integer porEligible;

    private Integer porBillingOk;

    @Size(max = 100)
    private String intermediaryActionState;

    private LocalDateTime porCrDate;

    private LocalDateTime porUpdDate;

    @Size(max = 100)
    private String porTechStatus;

    private LocalDateTime porTechDeadline;

    private Integer needManualRetry;

    private Long refPorId;

    @NotNull
    private Integer retryCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPorRequestId() {
        return porRequestId;
    }

    public void setPorRequestId(String porRequestId) {
        this.porRequestId = porRequestId;
    }

    public String getPorNumber() {
        return porNumber;
    }

    public void setPorNumber(String porNumber) {
        this.porNumber = porNumber;
    }

    public Integer getPorLegalTerm() {
        return porLegalTerm;
    }

    public void setPorLegalTerm(Integer porLegalTerm) {
        this.porLegalTerm = porLegalTerm;
    }

    public String getPorOpr() {
        return porOpr;
    }

    public void setPorOpr(String porOpr) {
        this.porOpr = porOpr;
    }

    public String getPorAccType() {
        return porAccType;
    }

    public void setPorAccType(String porAccType) {
        this.porAccType = porAccType;
    }

    public String getPorIdNumber() {
        return porIdNumber;
    }

    public void setPorIdNumber(String porIdNumber) {
        this.porIdNumber = porIdNumber;
    }

    public String getPorContactNumber() {
        return porContactNumber;
    }

    public void setPorContactNumber(String porContactNumber) {
        this.porContactNumber = porContactNumber;
    }

    public String getPorStatus() {
        return porStatus;
    }

    public void setPorStatus(String porStatus) {
        this.porStatus = porStatus;
    }

    public LocalDateTime getPorPortedDate() {
        return porPortedDate;
    }

    public void setPorPortedDate(LocalDateTime porPortedDate) {
        this.porPortedDate = porPortedDate;
    }

    public String getPorRouting() {
        return porRouting;
    }

    public void setPorRouting(String porRouting) {
        this.porRouting = porRouting;
    }

    public String getPorType() {
        return porType;
    }

    public void setPorType(String porType) {
        this.porType = porType;
    }

    public String getPorOpOrg() {
        return porOpOrg;
    }

    public void setPorOpOrg(String porOpOrg) {
        this.porOpOrg = porOpOrg;
    }

    public String getPorRspCode() {
        return porRspCode;
    }

    public void setPorRspCode(String porRspCode) {
        this.porRspCode = porRspCode;
    }

    public String getPorRspNote() {
        return porRspNote;
    }

    public void setPorRspNote(String porRspNote) {
        this.porRspNote = porRspNote;
    }

    public String getPorCancelNote() {
        return porCancelNote;
    }

    public void setPorCancelNote(String porCancelNote) {
        this.porCancelNote = porCancelNote;
    }

    public String getPorMnpid() {
        return porMnpid;
    }

    public void setPorMnpid(String porMnpid) {
        this.porMnpid = porMnpid;
    }

    public LocalDateTime getPortationDate() {
        return portationDate;
    }

    public void setPortationDate(LocalDateTime portationDate) {
        this.portationDate = portationDate;
    }

    public String getPortaCode() {
        return portaCode;
    }

    public void setPortaCode(String portaCode) {
        this.portaCode = portaCode;
    }

    public String getMvno() {
        return mvno;
    }

    public void setMvno(String mvno) {
        this.mvno = mvno;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPorErrCode() {
        return porErrCode;
    }

    public void setPorErrCode(String porErrCode) {
        this.porErrCode = porErrCode;
    }

    public String getPorErrMessage() {
        return porErrMessage;
    }

    public void setPorErrMessage(String porErrMessage) {
        this.porErrMessage = porErrMessage;
    }

    public String getPorOpd() {
        return porOpd;
    }

    public void setPorOpd(String porOpd) {
        this.porOpd = porOpd;
    }

    public String getPorNumType() {
        return porNumType;
    }

    public void setPorNumType(String porNumType) {
        this.porNumType = porNumType;
    }

    public String getPorNote() {
        return porNote;
    }

    public void setPorNote(String porNote) {
        this.porNote = porNote;
    }

    public LocalDateTime getPorDeadline() {
        return porDeadline;
    }

    public void setPorDeadline(LocalDateTime porDeadline) {
        this.porDeadline = porDeadline;
    }

    public Long getPorResponseTimestamp() {
        return porResponseTimestamp;
    }

    public void setPorResponseTimestamp(Long porResponseTimestamp) {
        this.porResponseTimestamp = porResponseTimestamp;
    }

    public Integer getPorEligible() {
        return porEligible;
    }

    public void setPorEligible(Integer porEligible) {
        this.porEligible = porEligible;
    }

    public Integer getPorBillingOk() {
        return porBillingOk;
    }

    public void setPorBillingOk(Integer porBillingOk) {
        this.porBillingOk = porBillingOk;
    }

    public String getIntermediaryActionState() {
        return intermediaryActionState;
    }

    public void setIntermediaryActionState(String intermediaryActionState) {
        this.intermediaryActionState = intermediaryActionState;
    }

    public LocalDateTime getPorCrDate() {
        return porCrDate;
    }

    public void setPorCrDate(LocalDateTime porCrDate) {
        this.porCrDate = porCrDate;
    }

    public LocalDateTime getPorUpdDate() {
        return porUpdDate;
    }

    public void setPorUpdDate(LocalDateTime porUpdDate) {
        this.porUpdDate = porUpdDate;
    }

    public String getPorTechStatus() {
        return porTechStatus;
    }

    public void setPorTechStatus(String porTechStatus) {
        this.porTechStatus = porTechStatus;
    }

    public LocalDateTime getPorTechDeadline() {
        return porTechDeadline;
    }

    public void setPorTechDeadline(LocalDateTime porTechDeadline) {
        this.porTechDeadline = porTechDeadline;
    }

    public Integer getNeedManualRetry() {
        return needManualRetry;
    }

    public void setNeedManualRetry(Integer needManualRetry) {
        this.needManualRetry = needManualRetry;
    }

    public Long getRefPorId() {
        return refPorId;
    }

    public void setRefPorId(Long refPorId) {
        this.refPorId = refPorId;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortabilityDTO)) {
            return false;
        }

        PortabilityDTO portabilityDTO = (PortabilityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, portabilityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortabilityDTO{" +
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


