package ix.portal.npg.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.validation.constraints.*;

/**
 * A DTO for the {@link ix.portal.npg.domain.FileReportGenerationLogEntity} entity.
 */
public class FileReportGenerationLogDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String reportName;

    @NotNull
    private LocalDateTime reportDate;

    @NotNull
    @Size(max = 255)
    private String fileName;

    @NotNull
    private Long rowNumber;

    @NotNull
    @Size(max = 16)
    private String porNumber;

    @NotNull
    @Size(max = 4000)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getPorNumber() {
        return porNumber;
    }

    public void setPorNumber(String porNumber) {
        this.porNumber = porNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileReportGenerationLogDTO)) {
            return false;
        }

        FileReportGenerationLogDTO fileReportGenerationLogDTO = (FileReportGenerationLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileReportGenerationLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileReportGenerationLogDTO{" +
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


