package ix.portal.npg.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DayOfWeekTimeFrameEntity.
 */
@Entity
@Table(name = "TBL_DAY_OF_WEEK_TIME_FRAME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DayOfWeekTimeFrameEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_DAY_OF_WEEK_TF_ID_GENERATOR", sequenceName = "SEQ_DAY_OF_WEEK_TF_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_DAY_OF_WEEK_TF_ID_GENERATOR")
    private Long id;

    @Column(name = "day")
    private Integer day;

    @Column(name = "begin")
    private Long begin;

    @Column(name = "end")
    private Long end;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeekTimeFrameEntity id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getDay() {
        return this.day;
    }

    public DayOfWeekTimeFrameEntity day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getBegin() {
        return this.begin;
    }

    public DayOfWeekTimeFrameEntity begin(Long begin) {
        this.begin = begin;
        return this;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return this.end;
    }

    public DayOfWeekTimeFrameEntity end(Long end) {
        this.end = end;
        return this;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayOfWeekTimeFrameEntity)) {
            return false;
        }
        return id != null && id.equals(((DayOfWeekTimeFrameEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayOfWeekTimeFrameEntity{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", begin=" + getBegin() +
            ", end=" + getEnd() +
            "}";
    }
}


