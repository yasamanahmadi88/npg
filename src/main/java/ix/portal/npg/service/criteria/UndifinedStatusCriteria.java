package ix.portal.npg.service.criteria;

import ix.portal.npg.Filter.LocalDateTimeFilter;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ix.portal.npg.domain.UndifinedStatusEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.UndifinedStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /undifined-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UndifinedStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter porStatus;

    private StringFilter porTechStatus;

    private StringFilter porErrCode;

    private StringFilter porRspCode;

    private StringFilter porRequestId;

    private LocalDateTimeFilter insertDate;

    public UndifinedStatusCriteria() {}

    public UndifinedStatusCriteria(UndifinedStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.porStatus = other.porStatus == null ? null : other.porStatus.copy();
        this.porTechStatus = other.porTechStatus == null ? null : other.porTechStatus.copy();
        this.porErrCode = other.porErrCode == null ? null : other.porErrCode.copy();
        this.porRspCode = other.porRspCode == null ? null : other.porRspCode.copy();
        this.porRequestId = other.porRequestId == null ? null : other.porRequestId.copy();
        this.insertDate = other.insertDate == null ? null : other.insertDate.copy();
    }

    @Override
    public UndifinedStatusCriteria copy() {
        return new UndifinedStatusCriteria(this);
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

    public LocalDateTimeFilter getInsertDate() {
        return insertDate;
    }

    public LocalDateTimeFilter insertDate() {
        if (insertDate == null) {
            insertDate = new LocalDateTimeFilter();
        }
        return insertDate;
    }

    public void setInsertDate(LocalDateTimeFilter insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UndifinedStatusCriteria that = (UndifinedStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(porStatus, that.porStatus) &&
            Objects.equals(porTechStatus, that.porTechStatus) &&
            Objects.equals(porErrCode, that.porErrCode) &&
            Objects.equals(porRspCode, that.porRspCode) &&
            Objects.equals(porRequestId, that.porRequestId) &&
            Objects.equals(insertDate, that.insertDate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, porStatus, porTechStatus, porErrCode, porRspCode, porRequestId, insertDate);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UndifinedStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (porStatus != null ? "porStatus=" + porStatus + ", " : "") +
            (porTechStatus != null ? "porTechStatus=" + porTechStatus + ", " : "") +
            (porErrCode != null ? "porErrCode=" + porErrCode + ", " : "") +
            (porRspCode != null ? "porRspCode=" + porRspCode + ", " : "") +
            (porRequestId != null ? "porRequestId=" + porRequestId + ", " : "") +
            (insertDate != null ? "insertDate=" + insertDate + ", " : "") +
            "}";
    }
}


