package ix.portal.npg.domain.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneralReport {

    private String name;
    private String name2;
    private long value;
    private List<GeneralReport> series;

    public GeneralReport() {}

    public GeneralReport(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public GeneralReport(LocalDateTime date, long value) {
        this.name = date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        this.value = value;
    }

    public GeneralReport(LocalDateTime date, String name2, long value) {
        this.name = date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        System.out.println("date.getDayOfMonth() = " + date.isAfter(LocalDateTime.now()));
        System.out.println("name = " + name);
        this.value = value;
        this.name2 = name2;
    }

    public String getName() {
        return name;
    }

    public GeneralReport setName(String name) {
        this.name = name;
        return this;
    }

    public long getValue() {
        return value;
    }

    public GeneralReport setValue(long value) {
        this.value = value;
        return this;
    }

    public List<GeneralReport> getSeries() {
        return series;
    }

    public GeneralReport setSeries(List<GeneralReport> series) {
        this.series = series;
        return this;
    }

    public String getName2() {
        return name2;
    }

    public GeneralReport setName2(String name2) {
        this.name2 = name2;
        return this;
    }
}


