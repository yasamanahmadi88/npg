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
 * Criteria class for the {@link ix.portal.npg.domain.CrmToCrdbResponseMapEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.CrmToCrdbResponseMapResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /crm-to-crdb-response-maps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CrmToCrdbResponseMapCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter description;

    private StringFilter crmInterface;

    private StringFilter rspCode;

    private StringFilter rspNote;

    public CrmToCrdbResponseMapCriteria() {}

    public CrmToCrdbResponseMapCriteria(CrmToCrdbResponseMapCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.crmInterface = other.crmInterface == null ? null : other.crmInterface.copy();
        this.rspCode = other.rspCode == null ? null : other.rspCode.copy();
        this.rspNote = other.rspNote == null ? null : other.rspNote.copy();
    }

    @Override
    public CrmToCrdbResponseMapCriteria copy() {
        return new CrmToCrdbResponseMapCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getCrmInterface() {
        return crmInterface;
    }

    public StringFilter crmInterface() {
        if (crmInterface == null) {
            crmInterface = new StringFilter();
        }
        return crmInterface;
    }

    public void setCrmInterface(StringFilter crmInterface) {
        this.crmInterface = crmInterface;
    }

    public StringFilter getRspCode() {
        return rspCode;
    }

    public StringFilter rspCode() {
        if (rspCode == null) {
            rspCode = new StringFilter();
        }
        return rspCode;
    }

    public void setRspCode(StringFilter rspCode) {
        this.rspCode = rspCode;
    }

    public StringFilter getRspNote() {
        return rspNote;
    }

    public StringFilter rspNote() {
        if (rspNote == null) {
            rspNote = new StringFilter();
        }
        return rspNote;
    }

    public void setRspNote(StringFilter rspNote) {
        this.rspNote = rspNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CrmToCrdbResponseMapCriteria that = (CrmToCrdbResponseMapCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(crmInterface, that.crmInterface) &&
            Objects.equals(rspCode, that.rspCode) &&
            Objects.equals(rspNote, that.rspNote)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, crmInterface, rspCode, rspNote);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrmToCrdbResponseMapCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (crmInterface != null ? "crmInterface=" + crmInterface + ", " : "") +
            (rspCode != null ? "rspCode=" + rspCode + ", " : "") +
            (rspNote != null ? "rspNote=" + rspNote + ", " : "") +
            "}";
    }
}


