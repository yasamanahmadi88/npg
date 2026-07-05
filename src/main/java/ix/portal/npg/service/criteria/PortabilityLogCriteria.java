package ix.portal.npg.service.criteria;

import ix.portal.npg.Filter.LocalDateTimeFilter;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ix.portal.npg.domain.PortabilityLogEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.PortabilityLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /portability-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PortabilityLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter porId;

    private StringFilter porRequestId;

    private StringFilter porNumber;

    private IntegerFilter porLegalTerm;

    private StringFilter porOpr;

    private StringFilter porAccType;

    private StringFilter porIdNumber;

    private StringFilter porContactNumber;

    private StringFilter porStatus;

    private LocalDateTimeFilter porPortedDate;

    private StringFilter porRouting;

    private StringFilter porType;

    private StringFilter porOpOrg;

    private StringFilter porRspCode;

    private StringFilter porRspNote;

    private StringFilter porCancelNote;

    private StringFilter porMnpid;

    private LocalDateTimeFilter portationDate;

    private StringFilter portaCode;

    private StringFilter mvno;

    private StringFilter context;

    private StringFilter porErrCode;

    private StringFilter porErrMessage;

    private StringFilter porOpd;

    private StringFilter porNumType;

    private StringFilter porNote;

    private LocalDateTimeFilter porDeadline;

    private LongFilter porResponseTimestamp;

    private IntegerFilter porEligible;

    private IntegerFilter porBillingOk;

    private StringFilter intermediaryActionState;

    private LocalDateTimeFilter porCrDate;

    private LocalDateTimeFilter porUpdDate;

    private StringFilter porTechStatus;

    private LocalDateTimeFilter porTechDeadline;

    private LongFilter refPorId;

    private IntegerFilter needManualRetry;

    private IntegerFilter retryCount;

    private StringFilter action;

    private StringFilter request;

    private LocalDateTimeFilter insertTimestamp;

    public PortabilityLogCriteria() {}

    public PortabilityLogCriteria(PortabilityLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.porId = other.porId == null ? null : other.porId.copy();
        this.porRequestId = other.porRequestId == null ? null : other.porRequestId.copy();
        this.porNumber = other.porNumber == null ? null : other.porNumber.copy();
        this.porLegalTerm = other.porLegalTerm == null ? null : other.porLegalTerm.copy();
        this.porOpr = other.porOpr == null ? null : other.porOpr.copy();
        this.porAccType = other.porAccType == null ? null : other.porAccType.copy();
        this.porIdNumber = other.porIdNumber == null ? null : other.porIdNumber.copy();
        this.porContactNumber = other.porContactNumber == null ? null : other.porContactNumber.copy();
        this.porStatus = other.porStatus == null ? null : other.porStatus.copy();
        this.porPortedDate = other.porPortedDate == null ? null : other.porPortedDate.copy();
        this.porRouting = other.porRouting == null ? null : other.porRouting.copy();
        this.porType = other.porType == null ? null : other.porType.copy();
        this.porOpOrg = other.porOpOrg == null ? null : other.porOpOrg.copy();
        this.porRspCode = other.porRspCode == null ? null : other.porRspCode.copy();
        this.porRspNote = other.porRspNote == null ? null : other.porRspNote.copy();
        this.porCancelNote = other.porCancelNote == null ? null : other.porCancelNote.copy();
        this.porMnpid = other.porMnpid == null ? null : other.porMnpid.copy();
        this.portationDate = other.portationDate == null ? null : other.portationDate.copy();
        this.portaCode = other.portaCode == null ? null : other.portaCode.copy();
        this.mvno = other.mvno == null ? null : other.mvno.copy();
        this.context = other.context == null ? null : other.context.copy();
        this.porErrCode = other.porErrCode == null ? null : other.porErrCode.copy();
        this.porErrMessage = other.porErrMessage == null ? null : other.porErrMessage.copy();
        this.porOpd = other.porOpd == null ? null : other.porOpd.copy();
        this.porNumType = other.porNumType == null ? null : other.porNumType.copy();
        this.porNote = other.porNote == null ? null : other.porNote.copy();
        this.porDeadline = other.porDeadline == null ? null : other.porDeadline.copy();
        this.porResponseTimestamp = other.porResponseTimestamp == null ? null : other.porResponseTimestamp.copy();
        this.porEligible = other.porEligible == null ? null : other.porEligible.copy();
        this.porBillingOk = other.porBillingOk == null ? null : other.porBillingOk.copy();
        this.intermediaryActionState = other.intermediaryActionState == null ? null : other.intermediaryActionState.copy();
        this.porCrDate = other.porCrDate == null ? null : other.porCrDate.copy();
        this.porUpdDate = other.porUpdDate == null ? null : other.porUpdDate.copy();
        this.porTechStatus = other.porTechStatus == null ? null : other.porTechStatus.copy();
        this.porTechDeadline = other.porTechDeadline == null ? null : other.porTechDeadline.copy();
        this.refPorId = other.refPorId == null ? null : other.refPorId.copy();
        this.needManualRetry = other.needManualRetry == null ? null : other.needManualRetry.copy();
        this.retryCount = other.retryCount == null ? null : other.retryCount.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.request = other.request == null ? null : other.request.copy();
        this.insertTimestamp = other.insertTimestamp == null ? null : other.insertTimestamp.copy();
    }

    @Override
    public PortabilityLogCriteria copy() {
        return new PortabilityLogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPorId() {
        return porId;
    }

    public LongFilter porId() {
        if (porId == null) {
            porId = new LongFilter();
        }
        return porId;
    }

    public void setPorId(LongFilter porId) {
        this.porId = porId;
    }

    public StringFilter getPorRequestId() {
        return porRequestId;
    }

    public StringFilter porRequestId() {
        if (porRequestId == null) {
            porRequestId = new StringFilter();
        }
        return porRequestId;
    }

    public void setPorRequestId(StringFilter porRequestId) {
        this.porRequestId = porRequestId;
    }

    public StringFilter getPorNumber() {
        return porNumber;
    }

    public StringFilter porNumber() {
        if (porNumber == null) {
            porNumber = new StringFilter();
        }
        return porNumber;
    }

    public void setPorNumber(StringFilter porNumber) {
        this.porNumber = porNumber;
    }

    public IntegerFilter getPorLegalTerm() {
        return porLegalTerm;
    }

    public IntegerFilter porLegalTerm() {
        if (porLegalTerm == null) {
            porLegalTerm = new IntegerFilter();
        }
        return porLegalTerm;
    }

    public void setPorLegalTerm(IntegerFilter porLegalTerm) {
        this.porLegalTerm = porLegalTerm;
    }

    public StringFilter getPorOpr() {
        return porOpr;
    }

    public StringFilter porOpr() {
        if (porOpr == null) {
            porOpr = new StringFilter();
        }
        return porOpr;
    }

    public void setPorOpr(StringFilter porOpr) {
        this.porOpr = porOpr;
    }

    public StringFilter getPorAccType() {
        return porAccType;
    }

    public StringFilter porAccType() {
        if (porAccType == null) {
            porAccType = new StringFilter();
        }
        return porAccType;
    }

    public void setPorAccType(StringFilter porAccType) {
        this.porAccType = porAccType;
    }

    public StringFilter getPorIdNumber() {
        return porIdNumber;
    }

    public StringFilter porIdNumber() {
        if (porIdNumber == null) {
            porIdNumber = new StringFilter();
        }
        return porIdNumber;
    }

    public void setPorIdNumber(StringFilter porIdNumber) {
        this.porIdNumber = porIdNumber;
    }

    public StringFilter getPorContactNumber() {
        return porContactNumber;
    }

    public StringFilter porContactNumber() {
        if (porContactNumber == null) {
            porContactNumber = new StringFilter();
        }
        return porContactNumber;
    }

    public void setPorContactNumber(StringFilter porContactNumber) {
        this.porContactNumber = porContactNumber;
    }

    public StringFilter getPorStatus() {
        return porStatus;
    }

    public StringFilter porStatus() {
        if (porStatus == null) {
            porStatus = new StringFilter();
        }
        return porStatus;
    }

    public void setPorStatus(StringFilter porStatus) {
        this.porStatus = porStatus;
    }

    public LocalDateTimeFilter getPorPortedDate() {
        return porPortedDate;
    }

    public LocalDateTimeFilter porPortedDate() {
        if (porPortedDate == null) {
            porPortedDate = new LocalDateTimeFilter();
        }
        return porPortedDate;
    }

    public void setPorPortedDate(LocalDateTimeFilter porPortedDate) {
        this.porPortedDate = porPortedDate;
    }

    public StringFilter getPorRouting() {
        return porRouting;
    }

    public StringFilter porRouting() {
        if (porRouting == null) {
            porRouting = new StringFilter();
        }
        return porRouting;
    }

    public void setPorRouting(StringFilter porRouting) {
        this.porRouting = porRouting;
    }

    public StringFilter getPorType() {
        return porType;
    }

    public StringFilter porType() {
        if (porType == null) {
            porType = new StringFilter();
        }
        return porType;
    }

    public void setPorType(StringFilter porType) {
        this.porType = porType;
    }

    public StringFilter getPorOpOrg() {
        return porOpOrg;
    }

    public StringFilter porOpOrg() {
        if (porOpOrg == null) {
            porOpOrg = new StringFilter();
        }
        return porOpOrg;
    }

    public void setPorOpOrg(StringFilter porOpOrg) {
        this.porOpOrg = porOpOrg;
    }

    public StringFilter getPorRspCode() {
        return porRspCode;
    }

    public StringFilter porRspCode() {
        if (porRspCode == null) {
            porRspCode = new StringFilter();
        }
        return porRspCode;
    }

    public void setPorRspCode(StringFilter porRspCode) {
        this.porRspCode = porRspCode;
    }

    public StringFilter getPorRspNote() {
        return porRspNote;
    }

    public StringFilter porRspNote() {
        if (porRspNote == null) {
            porRspNote = new StringFilter();
        }
        return porRspNote;
    }

    public void setPorRspNote(StringFilter porRspNote) {
        this.porRspNote = porRspNote;
    }

    public StringFilter getPorCancelNote() {
        return porCancelNote;
    }

    public StringFilter porCancelNote() {
        if (porCancelNote == null) {
            porCancelNote = new StringFilter();
        }
        return porCancelNote;
    }

    public void setPorCancelNote(StringFilter porCancelNote) {
        this.porCancelNote = porCancelNote;
    }

    public StringFilter getPorMnpid() {
        return porMnpid;
    }

    public StringFilter porMnpid() {
        if (porMnpid == null) {
            porMnpid = new StringFilter();
        }
        return porMnpid;
    }

    public void setPorMnpid(StringFilter porMnpid) {
        this.porMnpid = porMnpid;
    }

    public LocalDateTimeFilter getPortationDate() {
        return portationDate;
    }

    public LocalDateTimeFilter portationDate() {
        if (portationDate == null) {
            portationDate = new LocalDateTimeFilter();
        }
        return portationDate;
    }

    public void setPortationDate(LocalDateTimeFilter portationDate) {
        this.portationDate = portationDate;
    }

    public StringFilter getPortaCode() {
        return portaCode;
    }

    public StringFilter portaCode() {
        if (portaCode == null) {
            portaCode = new StringFilter();
        }
        return portaCode;
    }

    public void setPortaCode(StringFilter portaCode) {
        this.portaCode = portaCode;
    }

    public StringFilter getMvno() {
        return mvno;
    }

    public StringFilter mvno() {
        if (mvno == null) {
            mvno = new StringFilter();
        }
        return mvno;
    }

    public void setMvno(StringFilter mvno) {
        this.mvno = mvno;
    }

    public StringFilter getContext() {
        return context;
    }

    public StringFilter context() {
        if (context == null) {
            context = new StringFilter();
        }
        return context;
    }

    public void setContext(StringFilter context) {
        this.context = context;
    }

    public StringFilter getPorErrCode() {
        return porErrCode;
    }

    public StringFilter porErrCode() {
        if (porErrCode == null) {
            porErrCode = new StringFilter();
        }
        return porErrCode;
    }

    public void setPorErrCode(StringFilter porErrCode) {
        this.porErrCode = porErrCode;
    }

    public StringFilter getPorErrMessage() {
        return porErrMessage;
    }

    public StringFilter porErrMessage() {
        if (porErrMessage == null) {
            porErrMessage = new StringFilter();
        }
        return porErrMessage;
    }

    public void setPorErrMessage(StringFilter porErrMessage) {
        this.porErrMessage = porErrMessage;
    }

    public StringFilter getPorOpd() {
        return porOpd;
    }

    public StringFilter porOpd() {
        if (porOpd == null) {
            porOpd = new StringFilter();
        }
        return porOpd;
    }

    public void setPorOpd(StringFilter porOpd) {
        this.porOpd = porOpd;
    }

    public StringFilter getPorNumType() {
        return porNumType;
    }

    public StringFilter porNumType() {
        if (porNumType == null) {
            porNumType = new StringFilter();
        }
        return porNumType;
    }

    public void setPorNumType(StringFilter porNumType) {
        this.porNumType = porNumType;
    }

    public StringFilter getPorNote() {
        return porNote;
    }

    public StringFilter porNote() {
        if (porNote == null) {
            porNote = new StringFilter();
        }
        return porNote;
    }

    public void setPorNote(StringFilter porNote) {
        this.porNote = porNote;
    }

    public LocalDateTimeFilter getPorDeadline() {
        return porDeadline;
    }

    public LocalDateTimeFilter porDeadline() {
        if (porDeadline == null) {
            porDeadline = new LocalDateTimeFilter();
        }
        return porDeadline;
    }

    public void setPorDeadline(LocalDateTimeFilter porDeadline) {
        this.porDeadline = porDeadline;
    }

    public LongFilter getPorResponseTimestamp() {
        return porResponseTimestamp;
    }

    public LongFilter porResponseTimestamp() {
        if (porResponseTimestamp == null) {
            porResponseTimestamp = new LongFilter();
        }
        return porResponseTimestamp;
    }

    public void setPorResponseTimestamp(LongFilter porResponseTimestamp) {
        this.porResponseTimestamp = porResponseTimestamp;
    }

    public IntegerFilter getPorEligible() {
        return porEligible;
    }

    public IntegerFilter porEligible() {
        if (porEligible == null) {
            porEligible = new IntegerFilter();
        }
        return porEligible;
    }

    public void setPorEligible(IntegerFilter porEligible) {
        this.porEligible = porEligible;
    }

    public IntegerFilter getPorBillingOk() {
        return porBillingOk;
    }

    public IntegerFilter porBillingOk() {
        if (porBillingOk == null) {
            porBillingOk = new IntegerFilter();
        }
        return porBillingOk;
    }

    public void setPorBillingOk(IntegerFilter porBillingOk) {
        this.porBillingOk = porBillingOk;
    }

    public StringFilter getIntermediaryActionState() {
        return intermediaryActionState;
    }

    public StringFilter intermediaryActionState() {
        if (intermediaryActionState == null) {
            intermediaryActionState = new StringFilter();
        }
        return intermediaryActionState;
    }

    public void setIntermediaryActionState(StringFilter intermediaryActionState) {
        this.intermediaryActionState = intermediaryActionState;
    }

    public LocalDateTimeFilter getPorCrDate() {
        return porCrDate;
    }

    public LocalDateTimeFilter porCrDate() {
        if (porCrDate == null) {
            porCrDate = new LocalDateTimeFilter();
        }
        return porCrDate;
    }

    public void setPorCrDate(LocalDateTimeFilter porCrDate) {
        this.porCrDate = porCrDate;
    }

    public LocalDateTimeFilter getPorUpdDate() {
        return porUpdDate;
    }

    public LocalDateTimeFilter porUpdDate() {
        if (porUpdDate == null) {
            porUpdDate = new LocalDateTimeFilter();
        }
        return porUpdDate;
    }

    public void setPorUpdDate(LocalDateTimeFilter porUpdDate) {
        this.porUpdDate = porUpdDate;
    }

    public StringFilter getPorTechStatus() {
        return porTechStatus;
    }

    public StringFilter porTechStatus() {
        if (porTechStatus == null) {
            porTechStatus = new StringFilter();
        }
        return porTechStatus;
    }

    public void setPorTechStatus(StringFilter porTechStatus) {
        this.porTechStatus = porTechStatus;
    }

    public LocalDateTimeFilter getPorTechDeadline() {
        return porTechDeadline;
    }

    public LocalDateTimeFilter porTechDeadline() {
        if (porTechDeadline == null) {
            porTechDeadline = new LocalDateTimeFilter();
        }
        return porTechDeadline;
    }

    public void setPorTechDeadline(LocalDateTimeFilter porTechDeadline) {
        this.porTechDeadline = porTechDeadline;
    }

    public LongFilter getRefPorId() {
        return refPorId;
    }

    public LongFilter refPorId() {
        if (refPorId == null) {
            refPorId = new LongFilter();
        }
        return refPorId;
    }

    public void setRefPorId(LongFilter refPorId) {
        this.refPorId = refPorId;
    }

    public IntegerFilter getNeedManualRetry() {
        return needManualRetry;
    }

    public IntegerFilter needManualRetry() {
        if (needManualRetry == null) {
            needManualRetry = new IntegerFilter();
        }
        return needManualRetry;
    }

    public void setNeedManualRetry(IntegerFilter needManualRetry) {
        this.needManualRetry = needManualRetry;
    }

    public IntegerFilter getRetryCount() {
        return retryCount;
    }

    public IntegerFilter retryCount() {
        if (retryCount == null) {
            retryCount = new IntegerFilter();
        }
        return retryCount;
    }

    public void setRetryCount(IntegerFilter retryCount) {
        this.retryCount = retryCount;
    }

    public StringFilter getAction() {
        return action;
    }

    public StringFilter action() {
        if (action == null) {
            action = new StringFilter();
        }
        return action;
    }

    public void setAction(StringFilter action) {
        this.action = action;
    }

    public StringFilter getRequest() {
        return request;
    }

    public StringFilter request() {
        if (request == null) {
            request = new StringFilter();
        }
        return request;
    }

    public void setRequest(StringFilter request) {
        this.request = request;
    }

    public LocalDateTimeFilter getInsertTimestamp() {
        return insertTimestamp;
    }

    public LocalDateTimeFilter insertTimestamp() {
        if (insertTimestamp == null) {
            insertTimestamp = new LocalDateTimeFilter();
        }
        return insertTimestamp;
    }

    public void setInsertTimestamp(LocalDateTimeFilter insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PortabilityLogCriteria that = (PortabilityLogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(porId, that.porId) &&
            Objects.equals(porRequestId, that.porRequestId) &&
            Objects.equals(porNumber, that.porNumber) &&
            Objects.equals(porLegalTerm, that.porLegalTerm) &&
            Objects.equals(porOpr, that.porOpr) &&
            Objects.equals(porAccType, that.porAccType) &&
            Objects.equals(porIdNumber, that.porIdNumber) &&
            Objects.equals(porContactNumber, that.porContactNumber) &&
            Objects.equals(porStatus, that.porStatus) &&
            Objects.equals(porPortedDate, that.porPortedDate) &&
            Objects.equals(porRouting, that.porRouting) &&
            Objects.equals(porType, that.porType) &&
            Objects.equals(porOpOrg, that.porOpOrg) &&
            Objects.equals(porRspCode, that.porRspCode) &&
            Objects.equals(porRspNote, that.porRspNote) &&
            Objects.equals(porCancelNote, that.porCancelNote) &&
            Objects.equals(porMnpid, that.porMnpid) &&
            Objects.equals(portationDate, that.portationDate) &&
            Objects.equals(portaCode, that.portaCode) &&
            Objects.equals(mvno, that.mvno) &&
            Objects.equals(context, that.context) &&
            Objects.equals(porErrCode, that.porErrCode) &&
            Objects.equals(porErrMessage, that.porErrMessage) &&
            Objects.equals(porOpd, that.porOpd) &&
            Objects.equals(porNumType, that.porNumType) &&
            Objects.equals(porNote, that.porNote) &&
            Objects.equals(porDeadline, that.porDeadline) &&
            Objects.equals(porResponseTimestamp, that.porResponseTimestamp) &&
            Objects.equals(porEligible, that.porEligible) &&
            Objects.equals(porBillingOk, that.porBillingOk) &&
            Objects.equals(intermediaryActionState, that.intermediaryActionState) &&
            Objects.equals(porCrDate, that.porCrDate) &&
            Objects.equals(porUpdDate, that.porUpdDate) &&
            Objects.equals(porTechStatus, that.porTechStatus) &&
            Objects.equals(porTechDeadline, that.porTechDeadline) &&
            Objects.equals(refPorId, that.refPorId) &&
            Objects.equals(needManualRetry, that.needManualRetry) &&
            Objects.equals(retryCount, that.retryCount) &&
            Objects.equals(action, that.action) &&
            Objects.equals(request, that.request) &&
            Objects.equals(insertTimestamp, that.insertTimestamp)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            porId,
            porRequestId,
            porNumber,
            porLegalTerm,
            porOpr,
            porAccType,
            porIdNumber,
            porContactNumber,
            porStatus,
            porPortedDate,
            porRouting,
            porType,
            porOpOrg,
            porRspCode,
            porRspNote,
            porCancelNote,
            porMnpid,
            portationDate,
            portaCode,
            mvno,
            context,
            porErrCode,
            porErrMessage,
            porOpd,
            porNumType,
            porNote,
            porDeadline,
            porResponseTimestamp,
            porEligible,
            porBillingOk,
            intermediaryActionState,
            porCrDate,
            porUpdDate,
            porTechStatus,
            porTechDeadline,
            refPorId,
            needManualRetry,
            retryCount,
            action,
            request,
            insertTimestamp
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortabilityLogCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (porId != null ? "porId=" + porId + ", " : "") +
            (porRequestId != null ? "porRequestId=" + porRequestId + ", " : "") +
            (porNumber != null ? "porNumber=" + porNumber + ", " : "") +
            (porLegalTerm != null ? "porLegalTerm=" + porLegalTerm + ", " : "") +
            (porOpr != null ? "porOpr=" + porOpr + ", " : "") +
            (porAccType != null ? "porAccType=" + porAccType + ", " : "") +
            (porIdNumber != null ? "porIdNumber=" + porIdNumber + ", " : "") +
            (porContactNumber != null ? "porContactNumber=" + porContactNumber + ", " : "") +
            (porStatus != null ? "porStatus=" + porStatus + ", " : "") +
            (porPortedDate != null ? "porPortedDate=" + porPortedDate + ", " : "") +
            (porRouting != null ? "porRouting=" + porRouting + ", " : "") +
            (porType != null ? "porType=" + porType + ", " : "") +
            (porOpOrg != null ? "porOpOrg=" + porOpOrg + ", " : "") +
            (porRspCode != null ? "porRspCode=" + porRspCode + ", " : "") +
            (porRspNote != null ? "porRspNote=" + porRspNote + ", " : "") +
            (porCancelNote != null ? "porCancelNote=" + porCancelNote + ", " : "") +
            (porMnpid != null ? "porMnpid=" + porMnpid + ", " : "") +
            (portationDate != null ? "portationDate=" + portationDate + ", " : "") +
            (portaCode != null ? "portaCode=" + portaCode + ", " : "") +
            (mvno != null ? "mvno=" + mvno + ", " : "") +
            (context != null ? "context=" + context + ", " : "") +
            (porErrCode != null ? "porErrCode=" + porErrCode + ", " : "") +
            (porErrMessage != null ? "porErrMessage=" + porErrMessage + ", " : "") +
            (porOpd != null ? "porOpd=" + porOpd + ", " : "") +
            (porNumType != null ? "porNumType=" + porNumType + ", " : "") +
            (porNote != null ? "porNote=" + porNote + ", " : "") +
            (porDeadline != null ? "porDeadline=" + porDeadline + ", " : "") +
            (porResponseTimestamp != null ? "porResponseTimestamp=" + porResponseTimestamp + ", " : "") +
            (porEligible != null ? "porEligible=" + porEligible + ", " : "") +
            (porBillingOk != null ? "porBillingOk=" + porBillingOk + ", " : "") +
            (intermediaryActionState != null ? "intermediaryActionState=" + intermediaryActionState + ", " : "") +
            (porCrDate != null ? "porCrDate=" + porCrDate + ", " : "") +
            (porUpdDate != null ? "porUpdDate=" + porUpdDate + ", " : "") +
            (porTechStatus != null ? "porTechStatus=" + porTechStatus + ", " : "") +
            (porTechDeadline != null ? "porTechDeadline=" + porTechDeadline + ", " : "") +
            (refPorId != null ? "refPorId=" + refPorId + ", " : "") +
            (needManualRetry != null ? "needManualRetry=" + needManualRetry + ", " : "") +
            (retryCount != null ? "retryCount=" + retryCount + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (request != null ? "request=" + request + ", " : "") +
            (insertTimestamp != null ? "insertTimestamp=" + insertTimestamp + ", " : "") +
            "}";
    }
}


