package ix.portal.npg.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TimeFrameEntity.
 */
@Entity
@Table(name = "TBL_TIME_FRAME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TimeFrameEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_TIME_FRAME_ID_GENERATOR", sequenceName = "SEQ_TIME_FRAME_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_TIME_FRAME_ID_GENERATOR")
    private Long id;

    @Column(name = "begin")
    private Long begin;

    @Column(name = "end")
    private Long end;

    @Column(name = "off_day_id")
    private Long offDayId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeFrameEntity id(Long id) {
        this.id = id;
        return this;
    }

    public Long getBegin() {
        return this.begin;
    }

    public TimeFrameEntity begin(Long begin) {
        this.begin = begin;
        return this;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return this.end;
    }

    public TimeFrameEntity end(Long end) {
        this.end = end;
        return this;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getOffDayId() {
        return this.offDayId;
    }

    public TimeFrameEntity offDayId(Long offDayId) {
        this.offDayId = offDayId;
        return this;
    }

    public void setOffDayId(Long offDayId) {
        this.offDayId = offDayId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeFrameEntity)) {
            return false;
        }
        return id != null && id.equals(((TimeFrameEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeFrameEntity{" +
            "id=" + getId() +
            ", begin=" + getBegin() +
            ", end=" + getEnd() +
            ", offDayId=" + getOffDayId() +
            "}";
    }
}


