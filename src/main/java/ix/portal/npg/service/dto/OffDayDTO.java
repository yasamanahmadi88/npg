package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ix.portal.npg.domain.OffDayEntity} entity.
 */
public class OffDayDTO implements Serializable {

    private Long id;

    private Instant offDate;

    private Integer fullOff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOffDate() {
        return offDate;
    }

    public void setOffDate(Instant offDate) {
        this.offDate = offDate;
    }

    public Integer getFullOff() {
        return fullOff;
    }

    public void setFullOff(Integer fullOff) {
        this.fullOff = fullOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffDayDTO)) {
            return false;
        }

        OffDayDTO offDayDTO = (OffDayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offDayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffDayDTO{" +
            "id=" + getId() +
            ", offDate='" + getOffDate() + "'" +
            ", fullOff=" + getFullOff() +
            "}";
    }
}


