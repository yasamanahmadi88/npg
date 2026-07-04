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
 * Criteria class for the {@link ix.portal.npg.domain.DayOfWeekTimeFrameEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.DayOfWeekTimeFrameResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /day-of-week-time-frames?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DayOfWeekTimeFrameCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter day;

    private LongFilter begin;

    private LongFilter end;

    public DayOfWeekTimeFrameCriteria() {}

    public DayOfWeekTimeFrameCriteria(DayOfWeekTimeFrameCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.begin = other.begin == null ? null : other.begin.copy();
        this.end = other.end == null ? null : other.end.copy();
    }

    @Override
    public DayOfWeekTimeFrameCriteria copy() {
        return new DayOfWeekTimeFrameCriteria(this);
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

    public IntegerFilter getDay() {
        return day;
    }

    public IntegerFilter day() {
        if (day == null) {
            day = new IntegerFilter();
        }
        return day;
    }

    public void setDay(IntegerFilter day) {
        this.day = day;
    }

    public LongFilter getBegin() {
        return begin;
    }

    public LongFilter begin() {
        if (begin == null) {
            begin = new LongFilter();
        }
        return begin;
    }

    public void setBegin(LongFilter begin) {
        this.begin = begin;
    }

    public LongFilter getEnd() {
        return end;
    }

    public LongFilter end() {
        if (end == null) {
            end = new LongFilter();
        }
        return end;
    }

    public void setEnd(LongFilter end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DayOfWeekTimeFrameCriteria that = (DayOfWeekTimeFrameCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(begin, that.begin) &&
            Objects.equals(end, that.end)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, begin, end);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayOfWeekTimeFrameCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (begin != null ? "begin=" + begin + ", " : "") +
            (end != null ? "end=" + end + ", " : "") +
            "}";
    }
}


