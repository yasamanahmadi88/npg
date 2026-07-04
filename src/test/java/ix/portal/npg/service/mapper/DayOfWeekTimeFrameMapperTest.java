package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayOfWeekTimeFrameMapperTest {

    private DayOfWeekTimeFrameMapper dayOfWeekTimeFrameMapper;

    @BeforeEach
    public void setUp() {
        dayOfWeekTimeFrameMapper = new DayOfWeekTimeFrameMapperImpl();
    }
}
