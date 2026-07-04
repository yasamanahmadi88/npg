package ix.portal.npg.service.criteria;

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
 * Criteria class for the {@link ix.portal.npg.domain.CheckStatusEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.CheckStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /check-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter porStatus;

    private StringFilter porTechStatus;

    private StringFilter porErrCode;

    private StringFilter porRspCode;

    private StringFilter statusMessageFa;

    private StringFilter statusMessageEn;

    public CheckStatusCriteria() {}

    public CheckStatusCriteria(CheckStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.porStatus = other.porStatus == null ? null : other.porStatus.copy();
        this.porTechStatus = other.porTechStatus == null ? null : other.porTechStatus.copy();
        this.porErrCode = other.porErrCode == null ? null : other.porErrCode.copy();
        this.porRspCode = other.porRspCode == null ? null : other.porRspCode.copy();
        this.statusMessageFa = other.statusMessageFa == null ? null : other.statusMessageFa.copy();
        this.statusMessageEn = other.statusMessageEn == null ? null : other.statusMessageEn.copy();
    }

    @Override
    public CheckStatusCriteria copy() {
        return new CheckStatusCriteria(this);
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

    public StringFilter getStatusMessageFa() {
        return statusMessageFa;
    }

    public StringFilter statusMessageFa() {
        if (statusMessageFa == null) {
            statusMessageFa = new StringFilter();
        }
        return statusMessageFa;
    }

    public void setStatusMessageFa(StringFilter statusMessageFa) {
        this.statusMessageFa = statusMessageFa;
    }

    public StringFilter getStatusMessageEn() {
        return statusMessageEn;
    }

    public StringFilter statusMessageEn() {
        if (statusMessageEn == null) {
            statusMessageEn = new StringFilter();
        }
        return statusMessageEn;
    }

    public void setStatusMessageEn(StringFilter statusMessageEn) {
        this.statusMessageEn = statusMessageEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckStatusCriteria that = (CheckStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(porStatus, that.porStatus) &&
            Objects.equals(porTechStatus, that.porTechStatus) &&
            Objects.equals(porErrCode, that.porErrCode) &&
            Objects.equals(porRspCode, that.porRspCode) &&
            Objects.equals(statusMessageFa, that.statusMessageFa) &&
            Objects.equals(statusMessageEn, that.statusMessageEn)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, porStatus, porTechStatus, porErrCode, porRspCode, statusMessageFa, statusMessageEn);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (porStatus != null ? "porStatus=" + porStatus + ", " : "") +
            (porTechStatus != null ? "porTechStatus=" + porTechStatus + ", " : "") +
            (porErrCode != null ? "porErrCode=" + porErrCode + ", " : "") +
            (porRspCode != null ? "porRspCode=" + porRspCode + ", " : "") +
            (statusMessageFa != null ? "statusMessageFa=" + statusMessageFa + ", " : "") +
            (statusMessageEn != null ? "statusMessageEn=" + statusMessageEn + ", " : "") +
            "}";
    }
}


