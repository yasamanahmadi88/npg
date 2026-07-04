package ix.portal.npg.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SimpleReport {

    private String name;
    private long value;

    public SimpleReport() {}

    public SimpleReport(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public SimpleReport(LocalDateTime date, long value) {
        this.name = date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        this.value = value;
    }

    public SimpleReport(LocalDateTime date, String name2, long value) {
        this.name = date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public SimpleReport setName(String name) {
        this.name = name;
        return this;
    }

    public long getValue() {
        return value;
    }

    public SimpleReport setValue(long value) {
        this.value = value;
        return this;
    }
}


