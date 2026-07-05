package ix.portal.npg.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessConfigEntity.
 */
@Entity
@Table(name = "TBL_BUSINESS_CONFIG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BusinessConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "NAME", unique = true, nullable = false)
    private String id;

    @NotNull
    @Size(max = 512)
    @Column(name = "value", length = 512, nullable = false)
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BusinessConfigEntity id(String id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public BusinessConfigEntity value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessConfigEntity)) {
            return false;
        }
        return id != null && id.equals(((BusinessConfigEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessConfigEntity{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}


