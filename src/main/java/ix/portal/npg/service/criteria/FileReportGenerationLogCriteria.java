package ix.portal.npg.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import ix.portal.npg.Filter.LocalDateTimeFilter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ix.portal.npg.domain.FileReportGenerationLogEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.FileReportGenerationLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /file-report-generation-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FileReportGenerationLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reportName;

    private LocalDateTimeFilter reportDate;

    private StringFilter fileName;

    private LongFilter rowNumber;

    private StringFilter porNumber;

    private StringFilter content;

    public FileReportGenerationLogCriteria() {}

    public FileReportGenerationLogCriteria(FileReportGenerationLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reportName = other.reportName == null ? null : other.reportName.copy();
        this.reportDate = other.reportDate == null ? null : other.reportDate.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.rowNumber = other.rowNumber == null ? null : other.rowNumber.copy();
        this.porNumber = other.porNumber == null ? null : other.porNumber.copy();
        this.content = other.content == null ? null : other.content.copy();
    }

    @Override
    public FileReportGenerationLogCriteria copy() {
        return new FileReportGenerationLogCriteria(this);
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

    public StringFilter getReportName() {
        return reportName;
    }

    public StringFilter reportName() {
        if (reportName == null) {
            reportName = new StringFilter();
        }
        return reportName;
    }

    public void setReportName(StringFilter reportName) {
        this.reportName = reportName;
    }

    public LocalDateTimeFilter getReportDate() {
        return reportDate;
    }

    public LocalDateTimeFilter reportDate() {
        if (reportDate == null) {
            reportDate = new LocalDateTimeFilter();
        }
        return reportDate;
    }

    public void setReportDate(LocalDateTimeFilter reportDate) {
        this.reportDate = reportDate;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public LongFilter getRowNumber() {
        return rowNumber;
    }

    public LongFilter rowNumber() {
        if (rowNumber == null) {
            rowNumber = new LongFilter();
        }
        return rowNumber;
    }

    public void setRowNumber(LongFilter rowNumber) {
        this.rowNumber = rowNumber;
    }

    public StringFilter getPorNumber() {
        return porNumber;
    }

    public StringFilter porNumber() {
        if (porNumber == null) {
            porNumber = new StringFilter();
        }
        return porNumber;
    }

    public void setPorNumber(StringFilter porNumber) {
        this.porNumber = porNumber;
    }

    public StringFilter getContent() {
        return content;
    }

    public StringFilter content() {
        if (content == null) {
            content = new StringFilter();
        }
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileReportGenerationLogCriteria that = (FileReportGenerationLogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(reportName, that.reportName) &&
            Objects.equals(reportDate, that.reportDate) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(rowNumber, that.rowNumber) &&
            Objects.equals(porNumber, that.porNumber) &&
            Objects.equals(content, that.content)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportName, reportDate, fileName, rowNumber, porNumber, content);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileReportGenerationLogCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (reportName != null ? "reportName=" + reportName + ", " : "") +
            (reportDate != null ? "reportDate=" + reportDate + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (rowNumber != null ? "rowNumber=" + rowNumber + ", " : "") +
            (porNumber != null ? "porNumber=" + porNumber + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            "}";
    }
}


