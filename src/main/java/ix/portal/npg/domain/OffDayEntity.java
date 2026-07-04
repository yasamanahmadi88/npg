package ix.portal.npg.domain;

import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OffDayEntity.
 */
@Entity
@Table(name = "TBL_OFF_DAY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OffDayEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_OFF_DAY_ID_GENERATOR", sequenceName = "SEQ_OFF_DAY_ID", allocationSize = 0)
    @GeneratedValue(generator = "SEQ_OFF_DAY_ID_GENERATOR")
    private Long id;

    @Column(name = "off_date")
    private Instant offDate;

    @Column(name = "full_off")
    private Integer fullOff;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffDayEntity id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getOffDate() {
        return this.offDate;
    }

    public OffDayEntity offDate(Instant offDate) {
        this.offDate = offDate;
        return this;
    }

    public void setOffDate(Instant offDate) {
        this.offDate = offDate;
    }

    public Integer getFullOff() {
        return this.fullOff;
    }

    public OffDayEntity fullOff(Integer fullOff) {
        this.fullOff = fullOff;
        return this;
    }

    public void setFullOff(Integer fullOff) {
        this.fullOff = fullOff;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffDayEntity)) {
            return false;
        }
        return id != null && id.equals(((OffDayEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffDayEntity{" +
            "id=" + getId() +
            ", offDate='" + getOffDate() + "'" +
            ", fullOff=" + getFullOff() +
            "}";
    }
}


