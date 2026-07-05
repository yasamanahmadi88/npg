package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OffDayMapperTest {

    private OffDayMapper offDayMapper;

    @BeforeEach
    public void setUp() {
        offDayMapper = new OffDayMapperImpl();
    }
}
