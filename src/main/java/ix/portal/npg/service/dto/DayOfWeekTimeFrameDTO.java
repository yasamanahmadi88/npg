package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ix.portal.npg.domain.DayOfWeekTimeFrameEntity} entity.
 */
public class DayOfWeekTimeFrameDTO implements Serializable {

    private Long id;

    private Integer day;

    private Long begin;

    private Long end;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayOfWeekTimeFrameDTO)) {
            return false;
        }

        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = (DayOfWeekTimeFrameDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dayOfWeekTimeFrameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayOfWeekTimeFrameDTO{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", begin=" + getBegin() +
            ", end=" + getEnd() +
            "}";
    }
}


