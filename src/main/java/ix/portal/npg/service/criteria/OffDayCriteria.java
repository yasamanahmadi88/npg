package ix.portal.npg.service.criteria;

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
 * Criteria class for the {@link ix.portal.npg.domain.OffDayEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.OffDayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /off-days?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OffDayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter offDate;

    private IntegerFilter fullOff;

    public OffDayCriteria() {}

    public OffDayCriteria(OffDayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.offDate = other.offDate == null ? null : other.offDate.copy();
        this.fullOff = other.fullOff == null ? null : other.fullOff.copy();
    }

    @Override
    public OffDayCriteria copy() {
        return new OffDayCriteria(this);
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

    public InstantFilter getOffDate() {
        return offDate;
    }

    public InstantFilter offDate() {
        if (offDate == null) {
            offDate = new InstantFilter();
        }
        return offDate;
    }

    public void setOffDate(InstantFilter offDate) {
        this.offDate = offDate;
    }

    public IntegerFilter getFullOff() {
        return fullOff;
    }

    public IntegerFilter fullOff() {
        if (fullOff == null) {
            fullOff = new IntegerFilter();
        }
        return fullOff;
    }

    public void setFullOff(IntegerFilter fullOff) {
        this.fullOff = fullOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OffDayCriteria that = (OffDayCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(offDate, that.offDate) && Objects.equals(fullOff, that.fullOff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, offDate, fullOff);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffDayCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (offDate != null ? "offDate=" + offDate + ", " : "") +
            (fullOff != null ? "fullOff=" + fullOff + ", " : "") +
            "}";
    }
}


