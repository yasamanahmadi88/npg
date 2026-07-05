package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeFrameMapperTest {

    private TimeFrameMapper timeFrameMapper;

    @BeforeEach
    public void setUp() {
        timeFrameMapper = new TimeFrameMapperImpl();
    }
}
