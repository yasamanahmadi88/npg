package ix.portal.npg.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FileReportGenerationLogEntity.
 */
@Entity
@Table(name = "TBL_FILE_REPORT_GENERATION_LOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileReportGenerationLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_FILE_REPORT_GENERATION_LOG_ID_GENERATOR", sequenceName = "SEQ_FILE_REPORT_GENERATION_LOG_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILE_REPORT_GENERATION_LOG_ID_GENERATOR")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "REPORT_NAME", length = 255, nullable = false)
    private String reportName;

    @NotNull
    @Column(name = "REPORT_DATE", nullable = false)
    private LocalDateTime reportDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "FILE_NAME", length = 255, nullable = false)
    private String fileName;

    @NotNull
    // ROW_NUMBER is an Oracle analytic keyword; keep explicit uppercase physical name like sibling TBL_* entities.
    @Column(name = "ROW_NUMBER", nullable = false)
    private Long rowNumber;

    @NotNull
    @Size(max = 16)
    @Column(name = "POR_NUMBER", length = 16, nullable = false)
    private String porNumber;

    @NotNull
    @Size(max = 4000)
    @Column(name = "CONTENT", length = 4000, nullable = false)
    private String content;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileReportGenerationLogEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getReportName() {
        return this.reportName;
    }

    public FileReportGenerationLogEntity reportName(String reportName) {
        this.reportName = reportName;
        return this;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public LocalDateTime getReportDate() {
        return this.reportDate;
    }

    public FileReportGenerationLogEntity reportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public String getFileName() {
        return this.fileName;
    }

    public FileReportGenerationLogEntity fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getRowNumber() {
        return this.rowNumber;
    }

    public FileReportGenerationLogEntity rowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getPorNumber() {
        return this.porNumber;
    }

    public FileReportGenerationLogEntity porNumber(String porNumber) {
        this.porNumber = porNumber;
        return this;
    }

    public void setPorNumber(String porNumber) {
        this.porNumber = porNumber;
    }

    public String getContent() {
        return this.content;
    }

    public FileReportGenerationLogEntity content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileReportGenerationLogEntity)) {
            return false;
        }
        return id != null && id.equals(((FileReportGenerationLogEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileReportGenerationLogEntity{" +
            "id=" + getId() +
            ", reportName='" + getReportName() + "'" +
            ", reportDate=" + getReportDate() +
            ", fileName='" + getFileName() + "'" +
            ", rowNumber=" + getRowNumber() +
            ", porNumber='" + getPorNumber() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}


