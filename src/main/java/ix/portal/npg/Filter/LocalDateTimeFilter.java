package ix.portal.npg.Filter;

import java.time.LocalDateTime;
//import io.github.jhipster.service.filter.RangeFilter;

import tech.jhipster.service.filter.RangeFilter;

public class LocalDateTimeFilter extends RangeFilter<LocalDateTime> {

    public LocalDateTimeFilter() {}

    public LocalDateTimeFilter(LocalDateTimeFilter filter) {
        super(filter);
    }

    public LocalDateTimeFilter setEquals(LocalDateTime filter) {
        super.setEquals(filter);
        return this;
    }

    @Override
    public LocalDateTimeFilter copy() {
        return new LocalDateTimeFilter(this);
    }
}


