package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ix.portal.npg.domain.TimeFrameEntity} entity.
 */
public class TimeFrameDTO implements Serializable {

    private Long id;

    private Long begin;

    private Long end;

    private Long offDayId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOffDayId() {
        return offDayId;
    }

    public void setOffDayId(Long offDayId) {
        this.offDayId = offDayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeFrameDTO)) {
            return false;
        }

        TimeFrameDTO timeFrameDTO = (TimeFrameDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timeFrameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeFrameDTO{" +
            "id=" + getId() +
            ", begin=" + getBegin() +
            ", end=" + getEnd() +
            ", offDayId=" + getOffDayId() +
            "}";
    }
}


